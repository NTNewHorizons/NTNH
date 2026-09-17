---
name: gui_styling
description: Use when adapting, redesigning, or troubleshooting container GUIs, inventory slot alignments, progress indicators, or machine interfaces in Minecraft 1.7.10 via TxLoader or resource packs.
---

# Container GUI Adaptation & Engineering (`gui_styling`)

## Overview
A technical specification and procedure for adapting, reskinning, and aligning 2D container interfaces (GUIs) in **Minecraft 1.7.10**. Enforces strict slot coordinate matching with Forge/mod `Container` Java classes, TxLoader override rules, and Modernity dark-slate aesthetics.

---

## When to Use

### Triggers & Symptoms:
- Modernizing outdated, flat-gray (`#C6C6C6`) container GUIs from 2013-era mods.
- Reskinning furnaces, crafting tables, chests, Bibliocraft workstations, or custom machine containers.
- Items in a container appear misaligned, floating outside slot frames, or clipping into borders.
- Progress bars, burn flames, or energy/fluid tank overlays fail to align with container textures.
- Overriding mod GUIs without modifying or unpacking mod `.jar` files.

### When NOT to Use:
- Modifying Better Questing windows, quest nodes, or lines (use `bq_theming`).
- Editing world textures: mob skins, block models, or item icons (use `texture_curation`).

---

## 1. Container Anatomy & Coordinate Locking

### The Golden Coordinate Invariant:
> [!CRITICAL]
> In Minecraft 1.7.10, slot positions, progress arrow bounds, and gauge overlays are **hardcoded in Java classes (`Container` and `GuiContainer`)**.
> **NEVER shift slot or indicator positions by even a single pixel relative to the original mod texture.** Any displacement causes items to float outside their slots in-game.

### Standard Layout Dimensions:
- Canvas: **256×256 pixels** (ARGB).
- Active Container Frame: typically **176×166 pixels** (standard) or **176×184 pixels** (extended).
- Player Inventory Grid:
  - Main inventory (3 rows of 9 slots): typically starts at `x = 8, y = 84`.
  - Hotbar (1 row of 9 slots): typically starts at `x = 8, y = 142`.
  - Grid step: strictly **18 pixels** horizontal and vertical.

### Single Slot Micro-Geometry (18×18 px):
```
 (0,0) +------------------+ (17,0)
       | Outer Contour    |  <- 1px dark border (#141A16 or #373737)
       | +--------------+ |
       | | Inset Shadow | |  <- Top & Left: 1px deep shadow (#040605 or #181818)
       | |   16x16      | |
       | |  Item Well   | |  <- Center: 16x16 flat dark fill (#0A100C or #202020)
       | |              | |
       | | Bevel Light  | |  <- Bottom & Right: 1px light rim (#26362C or #555555)
       | +--------------+ |
(0,17) +------------------+ (17,17)
```

---

## 2. Override Pipelines

### Pipeline A: TxLoader Forceload (Preferred for Mods)
TxLoader intercepts and replaces mod textures at early Forge initialization without unpacking mod archives.
- **Path**: `config/txloader/forceload/<modid>/textures/gui/<filename>.png`
- **Examples**:
  - `config/txloader/forceload/bibliocraft/textures/gui/bigbookGUI.png`
  - `config/txloader/forceload/hbm/textures/gui/gui_reactor.png`

### Pipeline B: Resource Pack (Vanilla & Compatible Mods)
For vanilla containers or mods supporting standard resource pack asset loading.
- **Path**: `resourcepacks/NTNH-Modernity-2.1/assets/<modid>/textures/gui/container/<filename>.png`
- **Examples**:
  - `.../assets/minecraft/textures/gui/container/crafting_table.png`
  - `.../assets/minecraft/textures/gui/container/furnace.png`
  - `.../assets/etfuturum/textures/gui/container/smithing.png`

---

## 3. Step-by-Step Adaptation Procedure

### Step 1: Extract Original Texture
Inspect the mod jar to obtain the exact file name and reference coordinates:
```powershell
Add-Type -AssemblyName System.IO.Compression.FileSystem
$zip = [System.IO.Compression.ZipFile]::OpenRead("mods/<Mod_File>.jar")
$entry = $zip.GetEntry("assets/<modid>/textures/gui/<gui_name>.png")
[System.IO.Compression.ZipFileExtensions]::ExtractToFile($entry, "scratch/ref_<gui_name>.png", $true)
$zip.Dispose()
```

### Step 2: Overlay Alignment Verification
1. Import `ref_<gui_name>.png` into your editor as a semi-transparent background guide layer.
2. Align the new Modernity dark-slate chassis:
   - Match outer window dimensions (`176×166`, etc.).
   - Align every 18×18 slot socket exactly over the reference slot.
   - Keep progress arrow / gauge backgrounds at identical pixel positions.
3. Export as 32-bit ARGB PNG.

### Step 3: Deployment & Synchronization
1. Copy the adapted texture to `config/txloader/forceload/<modid>/textures/gui/` or the resource pack.
2. Mirror the file to the secondary instance (`DEV_NTNH` / `NTNH_tx_bcraft`) and confirm hash match via `Get-FileHash`.

### Step 4: In-Game Verification
1. Open the container in a running test client.
2. Place items in the four extreme corners of the inventory grid:
   - Slot (0, 0), slot (8, 0), hotbar (0, 0), hotbar (8, 0).
3. Hover mouse cursor over each slot:
   - Confirm the 16×16 white selection highlight is centered within the 18×18 socket with exactly 1px padding on all four sides.
4. If animated (furnace, smelter): smelt an item and ensure the fill sprite does not leak outside the arrow track.

### Step 5: Asset Attribution Record
Record the modified/borrowed asset in [`docs/standards/asset_attribution.md`](file:///C:/Users/S_N_A/AppData/Roaming/PrismLauncher/instances/NTNH_tx_bcraft/minecraft/docs/standards/asset_attribution.md) Section 4.1 with author, license status, and file path.

---

## 4. Common Pitfalls & Solutions

| Pitfall | Cause | Solution |
| :--- | :--- | :--- |
| **Hover highlight displaced by 1px** | New slot frame was drawn at an offset from original reference | Re-overlay the extracted jar texture and snap slot frames to original pixel coordinates. |
| **TxLoader not loading modified GUI** | Incorrect directory tree inside `forceload` | Ensure the relative path strictly follows `<modid>/textures/gui/<name>.png`. |
| **Black silhouettes or lost transparency** | Saved as 24-bit RGB instead of 32-bit ARGB | Export with an alpha channel enabled. |
| **Progress arrow clipped or desynced** | The empty track and filled sprite in sprite sheet have different dimensions | Measure original sprite bounds in jar and match pixel-for-pixel. |
