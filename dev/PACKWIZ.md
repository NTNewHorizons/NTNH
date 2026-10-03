# Packwiz Cheatsheet

Use this guide when adding, removing, or updating NTNH mods.

See the [official Packwiz documentation](https://packwiz.infra.link/) for command details.

## Prerequisites

Install Packwiz if necessary:

```bash
go install github.com/packwiz/packwiz@latest
source ~/.bashrc
packwiz list
```

Useful commands:

```bash
packwiz list
packwiz refresh
```

Run these from the repository root. NTNH is already initialized, so do not run `packwiz init` or an import command.

## Important rules

- Keep the JAR in `mods/` so the repository remains directly launchable.
- Keep its metadata in `mods/<project-slug>.pw.toml`.
- Use a stable project slug without the mod version, such as `angelica.pw.toml`.
- Set `pin = true` on every mod.
- Add every mod to [`packwiz-sources.json`](../packwiz-sources.json).
- Run `packwiz refresh` after every add, removal, or metadata edit.
- Source priority: NTNH GitHub -> GTNH GitHub -> other GitHub -> Modrinth/CurseForge.
- Client-only mods need `side = "client"` and an entry in [`server/client-only.txt`](../server/client-only.txt).

## Add a CurseForge mod

Use the project URL and exact file ID when possible:

```bash
packwiz curseforge add "https://www.curseforge.com/minecraft/mc-mods/PROJECT" --file-id FILE_ID
```

Packwiz will download the selected JAR and create its metadata. Rename the metadata file to a stable project slug if needed, then run:

```bash
packwiz pin PROJECT-SLUG
packwiz refresh
```

## Add a Modrinth mod

```bash
packwiz modrinth add "https://modrinth.com/mod/PROJECT" --version-id VERSION_ID
packwiz pin PROJECT-SLUG
packwiz refresh
```

## Add a GitHub mod

Prefer an exact asset-name pattern so Packwiz cannot select a sources or development JAR:

```bash
packwiz github add OWNER/REPOSITORY --regex '^project-[0-9.]+-1\.7\.10\.jar$'
packwiz pin PROJECT-SLUG
packwiz refresh
```

Use `NTNewHorizons/REPOSITORY` for NTNH mods and `GTNewHorizons/REPOSITORY` for GTNH mods.

## Add a direct-download mod

Use this only when the mod is not hosted on CurseForge, Modrinth, or GitHub Releases:

```bash
packwiz url add "Project Name" "https://example.com/project.jar"
packwiz pin PROJECT-SLUG
packwiz refresh
```

Direct-download mods are reported as manual by the automated updater.

## Update `packwiz-sources.json`

Packwiz does not update the custom source lock. After adding a mod, add an object for it to [`packwiz-sources.json`](../packwiz-sources.json).

Copy a nearby entry with the same source type. The important fields are:

| Source | Required fields |
| --- | --- |
| CurseForge | `path`, `source`, `url`, `project-id`, `file-id`, `sha256`, `side`, `metafile` |
| Modrinth | `path`, `source`, `url`, `mod-id`, `version`, `sha256`, `side`, `metafile` |
| GitHub | `path`, `source`, `url`, `project`, `sha256`, `side`, `metafile` |
| Direct | `path`, `source`, `url`, `sha256`, `side`, `metafile` |

For GitHub, `source` must be `ntnh`, `gtnh`, or `github` according to the owner.

Calculate the local SHA-256 with:

```bash
sha256sum "mods/FILE.jar"
```

## Remove a mod

Find the metadata slug with `packwiz list`, then run:

```bash
packwiz remove PROJECT-SLUG
```

Also remove that mod's object from [`packwiz-sources.json`](../packwiz-sources.json). Review and remove its configuration separately if it should no longer be distributed.

Finish with:

```bash
packwiz refresh
packwiz list
```

## Update mods

All mods are pinned, so do not use `packwiz update --all`.

Preview available updates without changing files:

```bash
python dev/update-mods.py
```

Apply all supported updates:

```bash
python dev/update-mods.py --apply
```

Update selected mods only:

```bash
python dev/update-mods.py --only angelica --apply
```

Use `--include-prereleases` only when beta, alpha, or prerelease builds are intentional.

The updater downloads and verifies replacements, updates JARs and metadata, keeps them pinned, refreshes `index.toml`, and writes `MOD_UPDATE_CHANGELOG.md`. Directly hosted mods without an update API are listed for manual handling.

## Finish any change

```bash
packwiz refresh
packwiz list
git diff --check
./build-packwiz.sh
```

Review the changed JARs, metadata, source lock, changelog, and generated archives before committing.
