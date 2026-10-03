#!/usr/bin/env python3
import argparse
import concurrent.futures
import datetime as dt
import fnmatch
import hashlib
from html.parser import HTMLParser
import json
import os
from pathlib import Path, PurePosixPath
import re
import shutil
import subprocess
import tempfile
import time
import tomllib
import urllib.error
import urllib.parse
import urllib.request


ROOT = Path(__file__).resolve().parent.parent
SOURCES_FILE = ROOT / "packwiz-sources.json"
MINECRAFT_VERSION = "1.7.10"
GITHUB_SOURCES = {"ntnh", "gtnh", "github"}


class HTMLTextExtractor(HTMLParser):
    def __init__(self):
        super().__init__()
        self.parts = []

    def handle_starttag(self, tag, attrs):
        if tag in {"br", "p", "div", "li", "h1", "h2", "h3", "pre"}:
            self.parts.append("\n")
        if tag == "li":
            self.parts.append("- ")

    def handle_endtag(self, tag):
        if tag in {"p", "div", "li", "h1", "h2", "h3", "pre"}:
            self.parts.append("\n")

    def handle_data(self, data):
        self.parts.append(data)

    def text(self):
        value = "".join(self.parts).replace("\r", "")
        value = re.sub(r"[ \t]+\n", "\n", value)
        value = re.sub(r"\n{3,}", "\n\n", value)
        return value.strip()


def parse_time(value):
    if not value:
        return dt.datetime.min.replace(tzinfo=dt.timezone.utc)
    return dt.datetime.fromisoformat(value.replace("Z", "+00:00"))


def toml_string(value):
    return json.dumps(value, ensure_ascii=True)


def github_token(explicit):
    if explicit:
        return explicit
    if os.environ.get("GITHUB_TOKEN"):
        return os.environ["GITHUB_TOKEN"]
    try:
        return subprocess.check_output(["gh", "auth", "token"], text=True, stderr=subprocess.DEVNULL).strip()
    except (FileNotFoundError, subprocess.CalledProcessError):
        return None


class API:
    def __init__(self, token=None):
        self.token = token

    def json(self, url, github=False):
        headers = {"User-Agent": "NTNH-mod-updater/1.0", "Accept": "application/json"}
        if github:
            headers.update(
                {
                    "Accept": "application/vnd.github+json",
                    "X-GitHub-Api-Version": "2022-11-28",
                }
            )
            if self.token:
                headers["Authorization"] = f"Bearer {self.token}"
        last_error = None
        for attempt in range(4):
            try:
                request = urllib.request.Request(url, headers=headers)
                with urllib.request.urlopen(request, timeout=120) as response:
                    return json.load(response)
            except (urllib.error.URLError, TimeoutError, json.JSONDecodeError) as error:
                last_error = error
                time.sleep(2**attempt)
        raise RuntimeError(f"Failed to request {url}: {last_error}")

    def github_releases(self, project, pages):
        releases = []
        for page in range(1, pages + 1):
            batch = self.json(
                f"https://api.github.com/repos/{project}/releases?per_page=100&page={page}", github=True
            )
            releases.extend(batch)
            if len(batch) < 100:
                break
        return releases


def channel_allows(current, candidate, include_prereleases=False):
    if include_prereleases:
        return True
    ranks = {"release": 0, "beta": 1, "alpha": 2}
    return ranks.get(candidate, 2) <= ranks.get(current, 0)


def github_asset_template(url):
    path = urllib.parse.urlparse(url).path
    marker = "/releases/download/"
    if marker not in path:
        raise ValueError("not a GitHub release asset URL")
    remainder = path.split(marker, 1)[1]
    encoded_tag, encoded_asset = remainder.split("/", 1)
    tag = urllib.parse.unquote(encoded_tag)
    asset = urllib.parse.unquote(encoded_asset)
    for version in (tag, tag.removeprefix("v")):
        if version and version in asset:
            prefix, suffix = asset.split(version, 1)
            regex = "^" + re.escape(prefix) + ".+" + re.escape(suffix) + "$"
            return tag, asset, prefix, suffix, regex
    raise ValueError(f"release tag {tag!r} is not present in asset {asset!r}")


def matching_github_asset(release, prefix, suffix):
    variants = [release["tag_name"], release["tag_name"].removeprefix("v")]
    names = {prefix + version + suffix for version in variants if version}
    matches = [asset for asset in release.get("assets", []) if asset["name"] in names]
    return matches[0] if len(matches) == 1 else None


def minecraft_track(value):
    match = re.search(r"(?:^|[+_-])(1\.7(?:\.10|\.x))(?:$|[+_-])", value, re.IGNORECASE)
    return match.group(1).lower() if match else None


def plan_github(item, api, args):
    current_tag, current_asset, prefix, suffix, regex = github_asset_template(item["url"])
    track = minecraft_track(current_tag) or minecraft_track(current_asset)
    releases = api.github_releases(item["project"], args.max_github_pages)
    releases = [release for release in releases if not release["draft"]]
    releases.sort(key=lambda release: parse_time(release.get("published_at") or release.get("created_at")))
    current_index = next((index for index, release in enumerate(releases) if release["tag_name"] == current_tag), None)
    if current_index is None:
        return {"status": "manual", "reason": f"GitHub tag {current_tag} was not found"}
    current = releases[current_index]
    eligible = []
    for release in releases[current_index + 1 :]:
        if release["prerelease"] and not (args.include_prereleases or current["prerelease"]):
            continue
        asset = matching_github_asset(release, prefix, suffix)
        if asset and track and track not in {
            minecraft_track(release["tag_name"]),
            minecraft_track(asset["name"]),
        }:
            asset = None
        if asset:
            eligible.append((release, asset))
    if not eligible:
        return {"status": "current", "version": current_tag}
    target_release, target_asset = eligible[-1]
    changes = [
        {
            "version": release["tag_name"],
            "url": release["html_url"],
            "body": (release.get("body") or "No changelog supplied.").strip(),
        }
        for release, _ in eligible
        if parse_time(release.get("published_at") or release.get("created_at"))
        <= parse_time(target_release.get("published_at") or target_release.get("created_at"))
    ]
    return {
        "status": "update",
        "old_version": current_tag,
        "new_version": target_release["tag_name"],
        "filename": target_asset["name"],
        "url": target_asset["browser_download_url"],
        "changes": changes,
        "update": {
            "github": {
                "slug": item["project"],
                "tag": target_release["tag_name"],
                "regex": regex,
            }
        },
    }


def primary_modrinth_file(version):
    files = version.get("files", [])
    primary = [file for file in files if file.get("primary")]
    if len(primary) == 1:
        return primary[0]
    jars = [
        file
        for file in files
        if file["filename"].endswith(".jar")
        and not re.search(r"(?:sources|dev|api|javadoc)\.jar$", file["filename"], re.IGNORECASE)
    ]
    return jars[0] if len(jars) == 1 else None


def plan_modrinth(item, api, args):
    query = urllib.parse.urlencode(
        {
            "loaders": json.dumps(["forge"]),
            "game_versions": json.dumps([MINECRAFT_VERSION]),
        }
    )
    versions = api.json(f"https://api.modrinth.com/v2/project/{item['mod-id']}/version?{query}")
    versions.sort(key=lambda version: parse_time(version["date_published"]))
    current_index = next((index for index, version in enumerate(versions) if version["id"] == item["version"]), None)
    if current_index is None:
        return {"status": "manual", "reason": f"Modrinth version {item['version']} was not found"}
    current = versions[current_index]
    eligible = [
        version
        for version in versions[current_index + 1 :]
        if channel_allows(current["version_type"], version["version_type"], args.include_prereleases)
        and primary_modrinth_file(version)
    ]
    if not eligible:
        return {"status": "current", "version": current["version_number"]}
    target = eligible[-1]
    file = primary_modrinth_file(target)
    changes = [
        {
            "version": version["version_number"],
            "url": f"https://modrinth.com/mod/{item['mod-id']}/version/{version['id']}",
            "body": (version.get("changelog") or "No changelog supplied.").strip(),
        }
        for version in eligible
    ]
    return {
        "status": "update",
        "old_version": current["version_number"],
        "new_version": target["version_number"],
        "filename": file["filename"],
        "url": file["url"],
        "expected": file.get("hashes", {}),
        "changes": changes,
        "update": {"modrinth": {"mod-id": item["mod-id"], "version": target["id"]}},
    }


def curseforge_files(project_id, api):
    files = []
    index = 0
    while True:
        query = urllib.parse.urlencode(
            {"gameVersion": MINECRAFT_VERSION, "index": index, "pageSize": 50}
        )
        response = api.json(f"https://api.curse.tools/v1/cf/mods/{project_id}/files?{query}")
        files.extend(response["data"])
        pagination = response["pagination"]
        index += pagination["resultCount"]
        if index >= pagination["totalCount"] or pagination["resultCount"] == 0:
            break
    return files


def curseforge_changelog(project_id, file_id, api):
    response = api.json(
        f"https://api.curse.tools/v1/cf/mods/{project_id}/files/{file_id}/changelog?raw=true"
    )
    body = response.get("data") or ""
    if "<" in body and ">" in body:
        parser = HTMLTextExtractor()
        parser.feed(body)
        body = parser.text()
    return body.strip() or "No changelog supplied."


def plan_curseforge(item, api, args):
    project_id = item["project-id"]
    project = api.json(f"https://api.curse.tools/v1/cf/mods/{project_id}")["data"]
    current_file = api.json(
        f"https://api.curse.tools/v1/cf/mods/{project_id}/files/{item['file-id']}"
    )["data"]
    files = curseforge_files(project_id, api)
    files = [
        file
        for file in files
        if file.get("isAvailable", True)
        and channel_allows(
            {1: "release", 2: "beta", 3: "alpha"}.get(current_file["releaseType"], "alpha"),
            {1: "release", 2: "beta", 3: "alpha"}.get(file["releaseType"], "alpha"),
            args.include_prereleases,
        )
    ]
    files.sort(key=lambda file: parse_time(file["fileDate"]))
    eligible = [file for file in files if parse_time(file["fileDate"]) > parse_time(current_file["fileDate"])]
    if not eligible:
        return {"status": "current", "version": current_file["displayName"]}
    target = eligible[-1]
    if not target.get("downloadUrl"):
        return {
            "status": "manual",
            "reason": f"CurseForge file {target['id']} requires a manual download",
        }
    changes = [
        {
            "version": file["displayName"],
            "url": f"https://www.curseforge.com/minecraft/mc-mods/{project['slug']}/files/{file['id']}",
            "body": curseforge_changelog(project_id, file["id"], api),
        }
        for file in eligible
    ]
    hashes = {
        {1: "sha1", 2: "md5"}[entry["algo"]]: entry["value"]
        for entry in target.get("hashes", [])
        if entry["algo"] in {1, 2}
    }
    return {
        "status": "update",
        "old_version": current_file["displayName"],
        "new_version": target["displayName"],
        "filename": target["fileName"],
        "url": target["downloadUrl"],
        "expected": hashes,
        "changes": changes,
        "update": {
            "curseforge": {"project-id": project_id, "file-id": target["id"]}
        },
    }


def matches_only(item, patterns):
    if not patterns:
        return True
    values = [
        item["path"],
        item["metafile"],
        PurePosixPath(item["metafile"]).stem,
        item.get("project", ""),
        item.get("mod-id", ""),
    ]
    normalized_patterns = [
        pattern if any(character in pattern for character in "*?[") else f"*{pattern}*"
        for pattern in patterns
    ]
    return any(
        fnmatch.fnmatch(value.lower(), pattern.lower())
        for pattern in normalized_patterns
        for value in values
    )


def plan_item(item, api, args):
    try:
        if item["source"] in GITHUB_SOURCES:
            result = plan_github(item, api, args)
        elif item["source"] == "modrinth":
            result = plan_modrinth(item, api, args)
        elif item["source"] == "curseforge":
            result = plan_curseforge(item, api, args)
        else:
            result = {"status": "manual", "reason": "No automatic update provider"}
    except Exception as error:
        result = {"status": "error", "reason": str(error)}
    return {**item, **result}


def download(update, destination):
    headers = {"User-Agent": "NTNH-mod-updater/1.0"}
    request = urllib.request.Request(update["url"], headers=headers)
    hashes = {name: hashlib.new(name) for name in {"sha256", *update.get("expected", {}).keys()}}
    with urllib.request.urlopen(request, timeout=300) as response, destination.open("wb") as output:
        while chunk := response.read(1024 * 1024):
            output.write(chunk)
            for digest in hashes.values():
                digest.update(chunk)
    actual = {name: digest.hexdigest() for name, digest in hashes.items()}
    for name, expected in update.get("expected", {}).items():
        if actual[name].lower() != expected.lower():
            raise RuntimeError(
                f"{update['metafile']}: downloaded {name} {actual[name]} does not match {expected}"
            )
    return actual["sha256"]


def render_metafile(path, update, sha256):
    current = tomllib.loads(path.read_text())
    lines = [
        f"name = {toml_string(Path(update['filename']).stem)}",
        f"filename = {toml_string(update['filename'])}",
        f"side = {toml_string(current.get('side', 'both'))}",
        "pin = true",
        "",
        "[download]",
        'hash-format = "sha256"',
        f"hash = {toml_string(sha256)}",
        f"url = {toml_string(update['url'])}",
    ]
    source, values = next(iter(update["update"].items()))
    lines += ["", f"[update.{source}]"]
    for key, value in values.items():
        lines.append(f"{key} = {value if isinstance(value, int) else toml_string(value)}")
    return "\n".join(lines) + "\n"


def write_changelog(path, updates, manual):
    lines = [
        "# Mod Update Changelog",
        "",
        f"Generated {dt.datetime.now(dt.timezone.utc).strftime('%Y-%m-%d %H:%M UTC')}.",
        "",
        f"Updated mods: **{len(updates)}**",
        "",
    ]
    for update in updates:
        name = PurePosixPath(update["metafile"]).stem
        lines += [
            f"## {name}: {update['old_version']} -> {update['new_version']}",
            "",
        ]
        for release in update["changes"]:
            lines += [
                f"### [{release['version']}]({release['url']})",
                "",
                release["body"],
                "",
            ]
    if manual:
        lines += ["## Manual Sources", ""]
        for item in manual:
            lines.append(f"- `{item['metafile']}`: {item['reason']}")
        lines.append("")
    path.write_text("\n".join(lines))


def apply_updates(updates, manual, sources, args):
    packwiz = shutil.which(args.packwiz)
    if not packwiz:
        raise RuntimeError(f"Packwiz executable not found: {args.packwiz}")
    with tempfile.TemporaryDirectory(prefix="ntnh-mod-update-") as temp_dir:
        temp = Path(temp_dir)
        staged = {}
        with concurrent.futures.ThreadPoolExecutor(max_workers=args.download_workers) as executor:
            futures = {}
            for index, update in enumerate(updates):
                destination = temp / f"{index}.jar"
                futures[executor.submit(download, update, destination)] = (update, destination)
            for future in concurrent.futures.as_completed(futures):
                update, destination = futures[future]
                staged[update["metafile"]] = (destination, future.result())

        by_metafile = {item["metafile"]: item for item in sources}
        old_paths = []
        for update in updates:
            source_item = by_metafile[update["metafile"]]
            old_path = ROOT / source_item["path"]
            new_path = old_path.with_name(update["filename"])
            new_path.parent.mkdir(parents=True, exist_ok=True)
            staged_path, sha256 = staged[update["metafile"]]
            # shutil.move handles cross-device moves (/tmp vs repo on
            # different filesystems); os.replace does not (Errno 18).
            shutil.move(str(staged_path), str(new_path))
            if old_path != new_path:
                old_paths.append(old_path)

            metafile = ROOT / update["metafile"]
            metafile.write_text(render_metafile(metafile, update, sha256))
            source_item.update(
                {
                    "path": new_path.relative_to(ROOT).as_posix(),
                    "url": update["url"],
                    "sha256": sha256,
                }
            )
            source_item.pop("project-id", None)
            source_item.pop("file-id", None)
            source_item.pop("mod-id", None)
            source_item.pop("version", None)
            source_item.pop("project", None)
            source_name, values = next(iter(update["update"].items()))
            if source_name == "github":
                source_item["project"] = values["slug"]
            else:
                source_item.update(values)

        for old_path in old_paths:
            old_path.unlink(missing_ok=True)
        SOURCES_FILE.write_text(json.dumps(sources, indent=2) + "\n")
        write_changelog(ROOT / args.changelog, updates, manual)
        subprocess.run([packwiz, "refresh"], cwd=ROOT, check=True)


def main():
    parser = argparse.ArgumentParser(
        description="Plan or apply pinned NTNH mod updates and aggregate their changelogs"
    )
    parser.add_argument("--apply", action="store_true", help="Apply the planned updates")
    parser.add_argument(
        "--include-prereleases", action="store_true", help="Allow beta, alpha, and GitHub prereleases"
    )
    parser.add_argument("--only", action="append", default=[], help="Only process matching mod globs")
    parser.add_argument("--changelog", default="MOD_UPDATE_CHANGELOG.md")
    parser.add_argument("--packwiz", default=os.environ.get("PACKWIZ_BIN", "packwiz"))
    parser.add_argument("--github-token")
    parser.add_argument("--max-github-pages", type=int, default=10)
    parser.add_argument("--download-workers", type=int, default=4)
    args = parser.parse_args()

    sources = json.loads(SOURCES_FILE.read_text())
    selected = [item for item in sources if matches_only(item, args.only)]
    token = github_token(args.github_token)
    if any(item["source"] in GITHUB_SOURCES for item in selected) and not token:
        raise RuntimeError("GitHub authentication is required; run `gh auth login` or set GITHUB_TOKEN")
    api = API(token)

    plans = []
    for index, item in enumerate(selected, 1):
        print(f"[{index}/{len(selected)}] Checking {item['metafile']}...", flush=True)
        plans.append(plan_item(item, api, args))

    updates = [item for item in plans if item["status"] == "update"]
    manual = [item for item in plans if item["status"] == "manual"]
    errors = [item for item in plans if item["status"] == "error"]
    current = [item for item in plans if item["status"] == "current"]
    print(
        f"\nUpdates: {len(updates)}; current: {len(current)}; "
        f"manual: {len(manual)}; errors: {len(errors)}"
    )
    for update in updates:
        print(f"UPDATE {update['metafile']}: {update['old_version']} -> {update['new_version']}")
    for item in manual:
        print(f"MANUAL {item['metafile']}: {item['reason']}")
    for item in errors:
        print(f"ERROR {item['metafile']}: {item['reason']}")

    if not args.apply:
        print("\nDry run only. Re-run with --apply to download, update, re-pin, and write the changelog.")
        return
    if errors:
        raise RuntimeError("Refusing to apply updates while provider checks have errors")
    apply_updates(updates, manual, sources, args)
    print(f"\nApplied {len(updates)} updates and wrote {args.changelog}.")


if __name__ == "__main__":
    main()
