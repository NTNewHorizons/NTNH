---
name: texture_curation
description: Use when adding, curating, or modifying in-game world textures (mob entities, blocks, ores, items, connected textures CTM, .mcmeta animations) or recording assets in asset_attribution.md.
---

# In-Game World Texture Curation (`texture_curation`)

## Overview
A technical reference and protocol for integrating, adapting, and curating 3D/world textures (mobs, blocks, ores, items, and connected textures) in **Nuclear Tech New Horizons (NTNH)** for Minecraft 1.7.10. Enforces the strict 16×16 resolution rule, 1.7.10 UV mapping compatibility, and ethical asset governance.

---

## When to Use

### Triggers & Symptoms:
- Adding or replacing mob textures (vanilla mobs, Et Futurum models, or Special Mobs variants).
- Mob limbs appear black, transparent, or scrambled due to UV mapping format mismatch (64×32 vs 64×64).
- Creating or adapting block textures that need seamless 4-way tiling.
- Modernizing ore blocks to fit JAPPA/Modernity depth and HBM industrial stone palettes.
- Configuring animated block/item textures via `.mcmeta`.
- Setting up connected textures (CTM) via Angelica (OptiFine CTM format).
- Recording borrowed or modified community textures in [`docs/standards/asset_attribution.md`](file:///C:/Users/S_N_A/AppData/Roaming/PrismLauncher/instances/NTNH_tx_bcraft/minecraft/docs/standards/asset_attribution.md).

### When NOT to Use:
- Designing or modifying Better Questing theme atlases, buttons, or lines (use `bq_theming`).
- Adapting 2D container interfaces, furnaces, or machine inventories (use `gui_styling`).

---

## 1. Aesthetic Standards & Resolution Discipline

1. **Strict 16×16 Native Resolution**:
   All blocks and items MUST use native 16×16 pixel resolution. HD textures (32×32, 64×64) destroy Minecraft's voxel visual cohesion and cause performance penalties.
2. **Deep Shading & Volume (JAPPA Modernity Style)**:
   Avoid flat, unshaded surfaces from early Minecraft. Use directional highlights (top-left) and deep shadow bevels (bottom-right).
3. **Atmospheric Harmony with HBM Nuclear Tech**:
   Textures must visually integrate with industrial machinery, concrete bunkers, radiation hazmats, and devastated wasteland biomes.

---

## 2. Entity & Mob Textures

### 2.1. 1.7.10 UV Mapping Compatibility (64×32 vs 64×64):
Minecraft 1.7.10 utilizes two distinct entity model formats:
- **Classic 64×32 Format**:
  Used by vanilla 1.7.10 mobs (zombies, skeletons, creepers, spiders). Left and right arms/legs mirror the exact same UV coordinates.
- **Modern 64×64 Format**:
  Used by Et Futurum, modern player skins, and specialized mob renderers. Features distinct UV islands for left/right limbs and outer jacket/layer meshes.

> [!WARNING]
> Applying a modern `64×64` texture to a classic `64×32` model will cause severe rendering artifacts (bottom half ignored, black limbs, broken leg geometry). Always inspect the original entity texture in the mod jar first.

### 2.2. Glowing Eye Overlays (`_eyes.png`):
- For mobs with night-visible eyes (endermen, spiders, radioactive mutants), provide a separate texture with suffix `_eyes.png`.
- **Canvas Rule**: The entire body must be 100% transparent (`#00000000`). Only the glowing eye pixels contain color. The engine renders this overlay at full brightness regardless of ambient light level.

### 2.3. Special Mobs Integration:
Textures for Special Mobs variants are located in:
```
resourcepacks/NTNH-Modernity-2.1/assets/specialmobs/textures/entity/<mob_type>/<variant>.png
```
*(e.g., `creeper/death.png`, `zombie/fire.png`, `ghast/fighter.png`).*

---

## 3. Block & Ore Textures

### 3.1. Seamless 4-Way Tiling:
Every terrain block must tile seamlessly in a 3×3 grid (48×48 px) with zero visible boundary seams, harsh edges, or repetitive repeating artifacts.

### 3.2. Ore Design & Stone Consistency:
- Mineral crystals require high-contrast specular highlights and deep shadow crevasses.
- **Base Stone Rule**: The background stone behind ore pixels MUST exactly match the active pack's `stone.png` pixel-for-pixel. A mismatch creates a visible square bounding box around the ore deposit in-game.

### 3.3. Texture Animation (`.mcmeta`):
For pulsing, glowing, or flowing textures:
1. Stack animation frames vertically: width = 16px, height = `16 × N` px.
2. Place a matching `<texture_name>.png.mcmeta` alongside the PNG:
   ```json
   {
     "animation": {
       "frametime": 2,
       "interpolate": true
     }
   }
   ```
   - `frametime`: Frame duration in game ticks (1 tick = 0.05s).
   - `interpolate: true`: Enables hardware bilinear interpolation between frames for smooth transitions.

### 3.4. Connected Textures (CTM via Angelica):
Angelica supports the OptiFine CTM format in 1.7.10:
- **Directory**: `resourcepacks/NTNH-Modernity-2.1/assets/<modid>/mcpatcher/ctm/<block>/`
- **Configuration**: `<block>.properties`
  - `method=ctm_compact`: Standard 47-tile seamless texture connection (glass, lab panels).
  - `method=horizontal`: Horizontal-only connection (bookshelves, lab counters).
  - `method=random`: Natural random variant rotation (stone, dirt, gravel).

---

## 4. Item Textures

- Dimensions: Exactly **16×16 pixels**.
- Readability: Silhouettes must be instantly identifiable at small inventory scales. Use a 1px dark perimeter outline with an upper-left specular rim.
- Path: `resourcepacks/NTNH-Modernity-2.1/assets/<modid>/textures/items/<item>.png`

---

## 5. Asset Attribution & Governance (`asset_attribution.md`)

In NTNH, all borrowed or modified community assets must be legally documented per [`docs/standards/asset_attribution.md`](file:///C:/Users/S_N_A/AppData/Roaming/PrismLauncher/instances/NTNH_tx_bcraft/minecraft/docs/standards/asset_attribution.md):

| Permission Status | Symbol | Meaning | Action |
| :--- | :---: | :--- | :--- |
| **APPROVED** | 🟢 | Direct permission granted by author | Screenshot stored in `docs/assets/proofs/<name>.png` |
| **OPEN_LICENSE** | 🔵 | CC-BY, MIT, CC0, Public Domain | Include author link and license name in registry |
| **PENDING** | 🟡 | Request sent, waiting for reply | Restricted to local dev branches; release forbidden |
| **DENIED** | 🔴 | Author refused permission | Remove texture immediately |

---

## 6. Step-by-Step Integration Workflow

1. **Verify Dimensions**: Confirm 16×16 for blocks/items or verify UV dimensions (64×32 vs 64×64) for entities.
2. **Deploy to Resource Pack**: Copy to `resourcepacks/NTNH-Modernity-2.1/assets/<modid>/...`.
3. **Synchronize Instances**: Mirror the file to the secondary dev instance and verify hash match.
4. **Hot-Reload In-Game**: Press **`F3 + T`** in the running Minecraft client.
5. **Multi-Condition Inspection**:
   - Inspect blocks in day, night, and torchlight.
   - Spawn mobs via eggs and verify limb animations for UV tearing.
   - Test seamless tiling by placing a 5×5 block wall.
6. **Log Attribution**: Add entry to [`docs/standards/asset_attribution.md`](file:///C:/Users/S_N_A/AppData/Roaming/PrismLauncher/instances/NTNH_tx_bcraft/minecraft/docs/standards/asset_attribution.md).

---

## 7. Common Pitfalls & Solutions

| Pitfall | Cause | Solution |
| :--- | :--- | :--- |
| **Mob limbs render black or mirrored incorrectly** | 64×64 texture placed on a 64×32 classic model | Re-map texture to classic 64×32 layout or convert model renderer. |
| **Ore has visible rectangular boundary in rock** | Background stone pixels differ from active pack's `stone.png` | Sample the exact stone texture from Modernity and re-bake background pixels. |
| **CTM glass/blocks do not connect** | Missing Angelica or misnamed `.properties` file | Check `mcpatcher/ctm/` path and confirm `matchBlocks=<id>` is correctly specified. |
| **Animated texture appears as a static tall column** | `.mcmeta` file missing or invalid JSON syntax | Ensure file is named `<texture>.png.mcmeta` with valid JSON brackets. |
