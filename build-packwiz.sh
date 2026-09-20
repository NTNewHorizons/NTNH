#!/usr/bin/env bash
set -euo pipefail

PACKWIZ_BIN="${PACKWIZ_BIN:-packwiz}"
VERSION="$(awk -F '"' '/^version = / { print $2; exit }' pack.toml)"
OUTPUT_DIR="${OUTPUT_DIR:-dist}"
IMPORT_DIR="${XDG_CACHE_HOME:-$HOME/.cache}/packwiz/cache/import"

if [[ -z "$VERSION" ]]; then
  echo "Unable to read the modpack version from pack.toml" >&2
  exit 1
fi

mkdir -p "$OUTPUT_DIR" "$IMPORT_DIR"

# Seed Packwiz's import cache from this launchable workspace. This also handles
# manual-download files and avoids silently incomplete exports after network
# interruptions while Packwiz is retrieving large release assets.
shopt -s nullglob globstar
for file in mods/**/*.jar resourcepacks/*.zip; do
  install -m 0644 "$file" "$IMPORT_DIR/$(basename "$file")"
done

"$PACKWIZ_BIN" refresh
CURSEFORGE="$OUTPUT_DIR/NTNH-$VERSION-CurseForge.zip"
MODRINTH="$OUTPUT_DIR/NTNH-$VERSION-Modrinth.mrpack"
NATIVE="$OUTPUT_DIR/NTNH-$VERSION-Native.zip"

"$PACKWIZ_BIN" curseforge export -o "$CURSEFORGE"
"$PACKWIZ_BIN" modrinth export --restrictDomains=false -o "$MODRINTH"
python3 dev/package-release.py \
  --curseforge "$CURSEFORGE" \
  --modrinth "$MODRINTH" \
  --native "$NATIVE"
