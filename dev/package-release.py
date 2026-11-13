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
    "Mary-TTS.zip",
    "MOD_UPDATE_CHANGELOG.md",
    "README.md",
    "build-packwiz.sh",
    "index.toml",
    "dev/package-release.py",
    "oauthprofiles.json",
    "pack.toml",
    "packwiz",
    "packwiz-sources.json",
}

# Mods that cannot use Packwiz metadata (for example, a mod that is not
# open-source yet). They stay as raw JARs in mods/ and are injected into every
# release archive instead of being resolved from a Packwiz download source.
RAW_MODS = (
    "mods/immersivevehicleslegacy-0.1.0-ntmv2.jar",
)


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


def is_mod_override_path(path):
    """Return True for any location under an overrides/mods/ tree.

    Canonical CurseForge layout is ``overrides/mods/<filename>``. Recent
    packwiz builds also nest JARs inside ``overrides/mods/``; any depth is
    accepted so we do not depend on flat layout.
    """
    ancestors = path.parts[:-1]
    return "overrides" in ancestors and "mods" in ancestors


def prefix_curseforge_overrides(archive, sources_file):
    """Add the GTNH_/NTNH_ prefix to override JARs that need it.

    This function is idempotent: if an override has already been renamed
    (by a prior invocation of this script on the same archive), that
    archive's file is treated as satisfying the source entry. This guards
    against accidental double-invocation - e.g. by both build-packwiz.sh
    and a workflow step - which would otherwise report every GTNH/NTNH
    mod as "not present in archive" because the bare filename has been
    replaced by the prefixed one.
    """
    sources = json.loads(sources_file.read_text())
    prefixes = {}
    for item in sources:
        prefix = {"gtnh": "GTNH_", "ntnh": "NTNH_"}.get(item["source"])
        if prefix:
            filename = PurePosixPath(item["path"]).name
            if filename in prefixes:
                raise RuntimeError(f"Duplicate override filename: {filename}")
            prefixes[filename] = prefix

    # bare name <-> already-prefixed name
    prefixed_to_bare = {prefix + bare: bare for bare, prefix in prefixes.items()}

    with tempfile.NamedTemporaryFile(dir=archive.parent, suffix=".zip", delete=False) as temp_file:
        temp_path = Path(temp_file.name)

    satisfied = set()
    misplaced = []
    try:
        with zipfile.ZipFile(archive, "r") as source, zipfile.ZipFile(
            temp_path, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9, allowZip64=True
        ) as target:
            target.comment = source.comment
            for info in source.infolist():
                name = info.filename
                path = PurePosixPath(name)

                # Already prefixed (a previous run of this script already
                # renamed the file). Accept it as-is.
                if path.name in prefixed_to_bare:
                    satisfied.add(prefixed_to_bare[path.name])
                    copy_zip_entry(source, target, info, name)
                    continue

                # Bare GTNH/NTNH filename that needs prefixing.
                if path.name in prefixes:
                    if is_mod_override_path(path):
                        satisfied.add(path.name)
                        name = str(path.with_name(prefixes[path.name] + path.name))
                    else:
                        misplaced.append((path.name, name))
                    copy_zip_entry(source, target, info, name)
                    continue

                # Anything else - copy through unchanged.
                copy_zip_entry(source, target, info, name)
        temp_path.replace(archive)
    finally:
        temp_path.unlink(missing_ok=True)

    if misplaced:
        raise RuntimeError(
            "GTNH/NTNH mod files were found outside overrides/mods/: "
            + "; ".join(f"{bare} at {actual!r}" for bare, actual in misplaced)
        )

    # Anything still missing is a CurseForge manifest entry, not a bug.
    # validate_archives() reconciles the manifest + override counts
    # against packwiz-sources.json.
    missing = sorted(set(prefixes) - satisfied)
    if missing:
        preview = missing[:5]
        suffix = "..." if len(missing) > 5 else ""
        print(
            f"NOTE: {len(missing)} NTNH/GTNH mod file(s) not shipped under "
            f"overrides/mods/ (treated as manifest.json entries): {preview}{suffix}",
            flush=True,
        )


def raw_mod_paths(sources_file):
    """Yield tracked raw mods that must be shipped without Packwiz metadata."""
    sources = json.loads(sources_file.read_text())
    source_paths = {Path(item["path"]).as_posix() for item in sources}
    seen = set()
    for posix in RAW_MODS:
        relative = Path(posix)
        normalized = relative.as_posix()
        if normalized in seen:
            raise RuntimeError(f"Duplicate raw mod path: {normalized}")
        seen.add(normalized)
        if normalized in source_paths:
            raise RuntimeError(
                f"Raw mod {normalized} must not also be listed in packwiz-sources.json"
            )
        path = ROOT / relative
        if not path.is_file():
            raise RuntimeError(f"Expected raw mod file is missing: {normalized}")
        yield relative, path


def native_files(sources_file, raw):
    sources = json.loads(sources_file.read_text())
    seen = set()
    for relative, path in raw:
        seen.add(relative.as_posix())
        yield relative, path
    for item in sources:
        relative = Path(item["path"])
        posix = relative.as_posix()
        if posix in seen:
            raise RuntimeError(f"Duplicate mod path in sources: {posix}")
        seen.add(posix)
        path = ROOT / relative
        if not path.is_file():
            raise RuntimeError(f"Expected mod file is missing: {posix}")
        yield relative, path

    output = subprocess.check_output(["git", "ls-files", "-z"], cwd=ROOT)
    for raw_path in output.split(b"\0"):
        if not raw_path:
            continue
        relative = Path(raw_path.decode())
        posix = relative.as_posix()
        if posix in seen:
            continue
        # Mod JARs are driven by packwiz-sources.json so renamed updates
        # are included without requiring manual `git add` first.
        if posix.startswith("mods/") and posix.endswith(".jar"):
            continue
        if posix in NATIVE_EXCLUDES:
            continue
        if relative.parts[0] in {".github", "dev", "server"}:
            continue
        if relative.name.endswith(".pw.toml"):
            continue
        path = ROOT / relative
        if path.is_file():
            yield relative, path


def build_native_archive(archive, sources_file, raw):
    archive.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(
        archive, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9, allowZip64=True
    ) as output:
        for relative, path in native_files(sources_file, raw):
            info = zipfile.ZipInfo(relative.as_posix(), date_time=(1980, 1, 1, 0, 0, 0))
            info.compress_type = zipfile.ZIP_DEFLATED
            info.external_attr = 0o100644 << 16
            with path.open("rb") as input_file, output.open(info, "w", force_zip64=True) as output_file:
                shutil.copyfileobj(input_file, output_file, length=1024 * 1024)


def inject_raw_mods(archive, raw, directory):
    """Copy raw JARs into an exported CurseForge/Modrinth archive."""
    with zipfile.ZipFile(
        archive, "a", compression=zipfile.ZIP_DEFLATED, compresslevel=9, allowZip64=True
    ) as output:
        existing = set(output.namelist())
        for relative, path in raw:
            name = f"{directory}/{relative.name}"
            if name in existing:
                continue
            info = zipfile.ZipInfo(name, date_time=(1980, 1, 1, 0, 0, 0))
            info.compress_type = zipfile.ZIP_DEFLATED
            info.external_attr = 0o100644 << 16
            with path.open("rb") as input_file, output.open(info, "w", force_zip64=True) as output_file:
                shutil.copyfileobj(input_file, output_file, length=1024 * 1024)


def build_mmc_prism_archive(archive, native, pack):
    components = {
        "formatVersion": 1,
        "components": [
            {"uid": "net.minecraft", "version": pack["versions"]["minecraft"], "important": True},
            {"uid": "net.minecraftforge", "version": pack["versions"]["forge"]},
        ],
    }
    config = f"[General]\nInstanceType=OneSix\nname=NTNH {pack['version']}\niconKey=default\n"
    archive.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(native) as source, zipfile.ZipFile(
        archive, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9, allowZip64=True
    ) as target:
        target.writestr("instance.cfg", config)
        target.writestr("mmc-pack.json", json.dumps(components, indent=2) + "\n")
        for info in source.infolist():
            copy_zip_entry(source, target, info, ".minecraft/" + info.filename)


def validate_mmc_prism_archive(archive, native, pack):
    with zipfile.ZipFile(archive) as output, zipfile.ZipFile(native) as source:
        if output.testzip() is not None:
            raise RuntimeError("MMC/Prism archive failed its integrity check")
        expected = {"instance.cfg", "mmc-pack.json"} | {
            ".minecraft/" + name for name in source.namelist()
        }
        if len(output.namelist()) != len(expected) or set(output.namelist()) != expected:
            raise RuntimeError("MMC/Prism archive does not match the Native payload")
        for info in source.infolist():
            copied = output.getinfo(".minecraft/" + info.filename)
            if (copied.CRC, copied.file_size) != (info.CRC, info.file_size):
                raise RuntimeError(f"MMC/Prism payload differs from Native: {info.filename}")
        components = json.loads(output.read("mmc-pack.json"))
        versions = {item["uid"]: item["version"] for item in components["components"]}
        if components["formatVersion"] != 1 or versions != {
            "net.minecraft": pack["versions"]["minecraft"],
            "net.minecraftforge": pack["versions"]["forge"],
        }:
            raise RuntimeError("MMC/Prism component versions do not match pack.toml")


def validate_archives(curseforge, modrinth, native, sources_file, raw):
    sources = json.loads(sources_file.read_text())
    expected_mods = len(sources)
    expected_raw = len(raw)
    raw_mod_names = {f"overrides/mods/{relative.name}" for relative, _ in raw}

    with zipfile.ZipFile(curseforge) as archive:
        if archive.testzip() is not None:
            raise RuntimeError("CurseForge archive failed its integrity check")
        names = set(archive.namelist())
        manifest = json.loads(archive.read("manifest.json"))
        overrides = [
            name for name in names
            if name.startswith("overrides/mods/") and name.endswith(".jar")
        ]
        total = len(manifest["files"]) + len(overrides)
        if total != expected_mods + expected_raw + 2:
            sample = sorted(names)[:10]
            raise RuntimeError(
                f"CurseForge archive does not contain every mod and resource pack: "
                f"{len(manifest['files'])} manifest + {len(overrides)} overrides = {total}, "
                f"expected {expected_mods + expected_raw + 2}. Sample zip entries: {sample}"
            )
        missing_raw = sorted(raw_mod_names - names)
        if missing_raw:
            raise RuntimeError(
                f"CurseForge archive is missing raw mod(s): {missing_raw}"
            )

        # Fail loudly only on the real hazard: a GTNH/NTNH override still
        # sitting under its bare filename after prefixing. Manifest entries
        # are legitimate and need no prefix.
        override_basenames = {PurePosixPath(name).name for name in overrides}
        for item in sources:
            prefix = {"gtnh": "GTNH_", "ntnh": "NTNH_"}.get(item["source"])
            if not prefix:
                continue
            basename = PurePosixPath(item["path"]).name
            if basename in override_basenames:
                raise RuntimeError(
                    f"GTNH/NTNH mod {basename} is unprefixed in overrides/mods/; "
                    f"expected {prefix}{basename}"
                )

    with zipfile.ZipFile(modrinth) as archive:
        if archive.testzip() is not None:
            raise RuntimeError("Modrinth archive failed its integrity check")
        index = json.loads(archive.read("modrinth.index.json"))
        mod_files = [item for item in index["files"] if item["path"].startswith("mods/")]
        if len(mod_files) != expected_mods:
            raise RuntimeError("Modrinth archive does not contain every mod")
        names = set(archive.namelist())
        missing_raw = sorted(raw_mod_names - names)
        if missing_raw:
            raise RuntimeError(
                f"Modrinth archive is missing raw mod(s): {missing_raw}"
            )

    with zipfile.ZipFile(native) as archive:
        if archive.testzip() is not None:
            raise RuntimeError("Native archive failed its integrity check")
        names = archive.namelist()
        if any(name.endswith(".pw.toml") for name in names):
            raise RuntimeError("Native archive contains Packwiz metafiles")
        native_mods = [name for name in names if name.startswith("mods/") and name.endswith(".jar")]
        if len(native_mods) != expected_mods + expected_raw:
            raise RuntimeError(
                f"Native archive contains {len(native_mods)} mods; "
                f"expected {expected_mods + expected_raw}"
            )
        missing_raw = sorted(
            f"mods/{relative.name}" for relative, _ in raw
            if f"mods/{relative.name}" not in names
        )
        if missing_raw:
            raise RuntimeError(f"Native archive is missing raw mod(s): {missing_raw}")


def main():
    parser = argparse.ArgumentParser(description="Finish and validate NTNH release packages")
    parser.add_argument("--curseforge", required=True, type=Path)
    parser.add_argument("--modrinth", required=True, type=Path)
    parser.add_argument("--native", required=True, type=Path)
    parser.add_argument("--mmc-prism", required=True, type=Path)
    parser.add_argument("--sources", default=ROOT / "packwiz-sources.json", type=Path)
    args = parser.parse_args()

    with (ROOT / "pack.toml").open("rb") as pack_file:
        pack = tomllib.load(pack_file)
    version = pack["version"]
    for archive in (args.curseforge, args.modrinth, args.native, args.mmc_prism):
        if version not in archive.name:
            raise RuntimeError(
                f"Archive name does not contain pack version {version}: {archive.name}"
            )

    raw = tuple(raw_mod_paths(args.sources))
    prefix_curseforge_overrides(args.curseforge, args.sources)
    inject_raw_mods(args.curseforge, raw, "overrides/mods")
    inject_raw_mods(args.modrinth, raw, "overrides/mods")
    build_native_archive(args.native, args.sources, raw)
    validate_archives(args.curseforge, args.modrinth, args.native, args.sources, raw)
    build_mmc_prism_archive(args.mmc_prism, args.native, pack)
    validate_mmc_prism_archive(args.mmc_prism, args.native, pack)


if __name__ == "__main__":
    main()
