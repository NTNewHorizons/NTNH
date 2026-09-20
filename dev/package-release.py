#!/usr/bin/env python3
import argparse
import json
from pathlib import Path, PurePosixPath
import shutil
import subprocess
import tempfile
import tomllib
import zipfile


ROOT = Path(__file__).resolve().parent.parent
NATIVE_EXCLUDES = {
    ".gitattributes",
    ".gitignore",
    ".packwizignore",
    "CHANGELOG.md",
    "LICENSE",
    "MOD_UPDATE_CHANGELOG.md",
    "README.md",
    "build-packwiz.sh",
    "index.toml",
    "dev/package-release.py",
    "pack.toml",
    "packwiz-sources.json",
}


def copy_zip_entry(source, target, info, name):
    output_info = zipfile.ZipInfo(name, date_time=info.date_time)
    output_info.compress_type = info.compress_type
    output_info.comment = info.comment
    output_info.extra = info.extra
    output_info.internal_attr = info.internal_attr
    output_info.external_attr = info.external_attr
    output_info.create_system = info.create_system
    with source.open(info) as input_file, target.open(output_info, "w", force_zip64=True) as output_file:
        shutil.copyfileobj(input_file, output_file, length=1024 * 1024)


def prefix_curseforge_overrides(archive, sources_file):
    sources = json.loads(sources_file.read_text())
    prefixes = {}
    for item in sources:
        prefix = {"gtnh": "GTNH_", "ntnh": "NTNH_"}.get(item["source"])
        if prefix:
            filename = PurePosixPath(item["path"]).name
            if filename in prefixes:
                raise RuntimeError(f"Duplicate override filename: {filename}")
            prefixes[filename] = prefix

    with tempfile.NamedTemporaryFile(dir=archive.parent, suffix=".zip", delete=False) as temp_file:
        temp_path = Path(temp_file.name)

    renamed = set()
    try:
        with zipfile.ZipFile(archive, "r") as source, zipfile.ZipFile(
            temp_path, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9, allowZip64=True
        ) as target:
            target.comment = source.comment
            for info in source.infolist():
                name = info.filename
                path = PurePosixPath(name)
                if len(path.parts) == 3 and path.parts[:2] == ("overrides", "mods"):
                    prefix = prefixes.get(path.name)
                    if prefix:
                        name = str(path.with_name(prefix + path.name))
                        renamed.add(path.name)
                copy_zip_entry(source, target, info, name)
        temp_path.replace(archive)
    finally:
        temp_path.unlink(missing_ok=True)

    missing = sorted(set(prefixes) - renamed)
    if missing:
        raise RuntimeError("Expected NTNH/GTNH overrides were not found: " + ", ".join(missing))


def native_files():
    output = subprocess.check_output(["git", "ls-files", "-z"], cwd=ROOT)
    for raw_path in output.split(b"\0"):
        if not raw_path:
            continue
        relative = Path(raw_path.decode())
        posix = relative.as_posix()
        if posix in NATIVE_EXCLUDES:
            continue
        if relative.parts[0] in {".github", "dev", "server"}:
            continue
        if relative.name.endswith(".pw.toml"):
            continue
        path = ROOT / relative
        if path.is_file():
            yield relative, path


def build_native_archive(archive):
    archive.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(
        archive, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9, allowZip64=True
    ) as output:
        for relative, path in native_files():
            info = zipfile.ZipInfo(relative.as_posix(), date_time=(1980, 1, 1, 0, 0, 0))
            info.compress_type = zipfile.ZIP_DEFLATED
            info.external_attr = 0o100644 << 16
            with path.open("rb") as input_file, output.open(info, "w", force_zip64=True) as output_file:
                shutil.copyfileobj(input_file, output_file, length=1024 * 1024)


def validate_archives(curseforge, modrinth, native, sources_file):
    sources = json.loads(sources_file.read_text())
    expected_mods = len(sources)

    with zipfile.ZipFile(curseforge) as archive:
        if archive.testzip() is not None:
            raise RuntimeError("CurseForge archive failed its integrity check")
        names = set(archive.namelist())
        manifest = json.loads(archive.read("manifest.json"))
        overrides = [name for name in names if name.startswith("overrides/mods/") and name.endswith(".jar")]
        if len(manifest["files"]) + len(overrides) != expected_mods + 2:
            raise RuntimeError("CurseForge archive does not contain every mod and resource pack")
        for item in sources:
            prefix = {"gtnh": "GTNH_", "ntnh": "NTNH_"}.get(item["source"])
            if prefix:
                expected = f"overrides/mods/{prefix}{PurePosixPath(item['path']).name}"
                if expected not in names:
                    raise RuntimeError(f"Missing prefixed CurseForge override: {expected}")

    with zipfile.ZipFile(modrinth) as archive:
        if archive.testzip() is not None:
            raise RuntimeError("Modrinth archive failed its integrity check")
        index = json.loads(archive.read("modrinth.index.json"))
        mod_files = [item for item in index["files"] if item["path"].startswith("mods/")]
        if len(mod_files) != expected_mods:
            raise RuntimeError("Modrinth archive does not contain every mod")

    with zipfile.ZipFile(native) as archive:
        if archive.testzip() is not None:
            raise RuntimeError("Native archive failed its integrity check")
        names = archive.namelist()
        if any(name.endswith(".pw.toml") for name in names):
            raise RuntimeError("Native archive contains Packwiz metafiles")
        native_mods = [name for name in names if name.startswith("mods/") and name.endswith(".jar")]
        if len(native_mods) != expected_mods:
            raise RuntimeError(f"Native archive contains {len(native_mods)} mods; expected {expected_mods}")


def main():
    parser = argparse.ArgumentParser(description="Finish and validate NTNH release packages")
    parser.add_argument("--curseforge", required=True, type=Path)
    parser.add_argument("--modrinth", required=True, type=Path)
    parser.add_argument("--native", required=True, type=Path)
    parser.add_argument("--sources", default=ROOT / "packwiz-sources.json", type=Path)
    args = parser.parse_args()

    with (ROOT / "pack.toml").open("rb") as pack_file:
        version = tomllib.load(pack_file)["version"]
    for archive in (args.curseforge, args.modrinth, args.native):
        if version not in archive.name:
            raise RuntimeError(f"Archive name does not contain pack version {version}: {archive.name}")

    prefix_curseforge_overrides(args.curseforge, args.sources)
    build_native_archive(args.native)
    validate_archives(args.curseforge, args.modrinth, args.native, args.sources)


if __name__ == "__main__":
    main()
