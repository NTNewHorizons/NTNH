---
name: bq_theming
description: Use when designing, customizing, calibrating, or troubleshooting Better Questing (GTNH 3.8.74) themes, bq_themes.json configurations, GUI atlas layouts, 9-slice sliced textures, scale-invariant quest lines, color pulse animations, or quest interface controls.
---

# Better Questing Theme Engineering (`bq_theming`)

## Overview
A comprehensive technical reference for authoring, styling, and calibrating visual themes for **Better Questing 3.8.74-GTNH** (Minecraft 1.7.10). Covers atlas layout, 9-slice slicing mathematics, hardware-scaled quad lines, color pulse timing, and checkbox icon tinting.

---

## When to Use

### Triggers & Symptoms:
- Creating a new visual theme or redesigning an existing one (`vault_terminal`, `midnight`, etc.).
- Quest dependency lines scale incorrectly (becoming excessively thick when zooming out or invisible when zooming in).
- 9-slice frames stretch or repeat decorative corner brackets along borders.
- Buttons, text boxes, scrollbars, or quest nodes have incorrect padding, slice modes, or contrast.
- Configuring status indicators (`locked`, `unlocked`, `pending`, `complete`, `repeatable`) with static or pulsating colors.
- Customizing task checkboxes (`icon_tick`, `icon_cross`) for quest objectives.

### When NOT to Use:
- Modifying standard item tooltips across the modpack (managed globally outside BQ).
- Writing quest tasks, prerequisite trees, or reward tables (`DefaultQuests.json`) without changing GUI styles.
- Creating or editing container GUIs for non-BQ mods (use `gui_styling`).

---

## 1. Directory Structure & Registration

Themes reside in `config/betterquesting/resources/<theme_id>/`:
```
config/betterquesting/resources/<theme_id>/
├── bq_themes.json              # Main theme definition, textures, colors, lines
└── textures/gui/
    └── <atlas_name>.png        # 256x256 ARGB atlas containing all GUI elements
```

### Registration Syntax (`bq_themes.json`):
```json
[
  {
    "themeID": "my_theme:dark",
    "themeName": "My Theme Name",
    "themeParent": "none",
    "textures": { ... },
    "colors": { ... },
    "lines": { ... }
  }
]
```
- `themeParent`: Set to `"none"` for standalone themes, or specify a parent theme ID to inherit undefined properties.

---

## 2. Atlas Layout & 9-Slice Geometry

The UI renderer relies on 9-slice textures (`betterquesting:texture_sliced`) split into 4 corners, 4 edges, and a center.

### Texture Definition Format:
```json
"betterquesting:panel_main": {
  "textureType": "betterquesting:texture_sliced",
  "atlas": "my_theme:textures/gui/atlas.png",
  "bounds": [0, 0, 48, 48],
  "padding": [16, 16, 16, 16],
  "sliceMode": 1
}
```

### Parameter Reference:
- **`bounds`**: `[x, y, width, height]` — Pixel coordinates on the 256×256 atlas.
- **`padding`**: `[left, top, right, bottom]` — Slice boundary insets. Corner size equals `left × top`.
- **`sliceMode`**:
  - `1` (**Tiled**): Repeated pattern along edges and center. Ideal for structured chassis panels (`panel_main`, `panel_inner`) to prevent blurry borders.
  - `2` (**Stretched**): Bilinear stretch along edges and center. Ideal for scrollbars, progress tracks, and item slot sockets (`item_frame`, `scroll_v_*`).

### Corner Isolation Rule:
> [!CRITICAL]
> Any decorative corner elements (such as 4px mounting brackets, rivets, or corner notches) **MUST stay strictly within the `left × top` corner region** (e.g., within `x ∈ [0..left-1], y ∈ [0..top-1]`).
> If a corner feature extends past the padding boundary, that pixel column/row will tile or stretch across the entire panel edge.

### Component Atlas Reference:
| Texture Key | Atlas Bounds | Padding `[L, T, R, B]` | Slice Mode | Visual Purpose |
| :--- | :---: | :---: | :---: | :--- |
| `betterquesting:panel_main` | `[0, 0, 48, 48]` | `[16, 16, 16, 16]` | `1` (Tiled) | Main window frame with phosphor corner accents |
| `betterquesting:btn_normal_0` | `[48, 0, 200, 20]` | `[2, 2, 2, 3]` | `1` (Tiled) | Disabled button state |
| `betterquesting:btn_normal_1` | `[48, 20, 200, 20]` | `[2, 2, 2, 3]` | `1` (Tiled) | Idle button state (deep emerald fill, subdued frame) |
| `betterquesting:btn_normal_2` | `[48, 40, 200, 20]` | `[2, 2, 2, 3]` | `1` (Tiled) | Hover/Active button state (phosphor green border) |
| `betterquesting:item_frame` | `[0, 48, 18, 18]` | `[2, 2, 2, 2]` | `2` (Stretched)| Recessed item socket with shadow and bevel |
| `betterquesting:text_box_0..2`| `[48..80, 64, 16, 16]`| `[3, 3, 3, 3]`| `1` (Tiled) | Text input / Search field states |
| `betterquesting:panel_dark` | `[96, 64, 32, 32]` | `[8, 8, 8, 8]` | `1` (Tiled) | Modal confirmation popups (5px corner brackets) |
| `betterquesting:scroll_v_bg` | `[248, 0, 8, 60]` | `[1, 1, 1, 1]` | `2` (Stretched)| Vertical scroll track groove |
| `betterquesting:scroll_v_0..2`| `[248, 60..100, 8, 20]`| `[1, 6, 1, 6]`| `2` (Stretched)| Tactile grooved scroll thumb |
| `betterquesting:panel_inner` | `[128, 128, 64, 64]` | `[8, 8, 8, 8]` | `1` (Tiled) | Quest card panel with 4px corner tick brackets |
| `betterquesting:icon_tick` | `[208, 64, 16, 16]` | — | Simple Texture | Checkmark for task checkbox |
| `betterquesting:icon_cross` | `[224, 64, 16, 16]` | — | Simple Texture | X-mark for task checkbox |

---

## 3. Scale-Invariant Quad Lines (`line_directional`)

### The `glLineWidth` Engine Flaw:
`line_simple` invokes OpenGL `glLineWidth()`, rendering lines in **fixed monitor pixels**:
- **Zoom Out**: Quest nodes shrink, but lines stay at fixed screen pixels, turning into massive thick bars that obscure icons.
- **Zoom In**: Lines become hairline thin.

### Solution: Hardware-Scaled GL Quads
Replace all line entries in `lines` with `betterquesting:line_directional`:
```json
"betterquesting:quest_unlocked": {
  "lineType": "betterquesting:line_directional",
  "arrowWidth": 0.5,
  "arrowSize": 0.75,
  "arrowOpacity": 0.0,
  "widthScale": 0.75
}
```
- Lines are rendered as **quad polygons directly in world-canvas coordinates**. The GPU scales them proportionally with quest icons at every zoom level.
- `"arrowOpacity": 0.0` removes distracting chevron arrows, providing clean technical lines.
- `"widthScale": 0.75` provides optimal visual weight for standard 24×24 quest nodes.
- Apply to all 5 keys: `quest_locked`, `quest_unlocked`, `quest_pending`, `quest_complete`, `quest_repeatable`.

---

## 4. Color Physics & Pulse Signaling

### Design Principle: Contrast-First (No Neon Glare):
- Keep backgrounds and passive borders dark (graphite `#0E110F`, dark slate `#242E27`, deep emerald `#0E2716`).
- Reserve high-intensity phosphor green (`#2FFF21`) strictly for active input, mouse hover, and actionable rewards.

### Color Pulse Syntax (`color_pulse`):
```json
"betterquesting:quest_icon_unlocked": {
  "colorType": "betterquesting:color_pulse",
  "period": 2.0,
  "phase": 0.0,
  "color1": {
    "colorType": "betterquesting:color_static",
    "color": "FFFFB830"
  },
  "color2": {
    "colorType": "betterquesting:color_static",
    "color": "FF8F5E12"
  }
}
```

### Standard Status Signal Map:
| Quest State | Node Color (`quest_icon_*`) | Line Color (`quest_line_*`) | Signal Semantics |
| :--- | :---: | :---: | :--- |
| **Locked** | `#FF354238` (static gunmetal) | `#FF18221B` (static graphite) | Inactive; blends into canvas to prevent distraction |
| **Unlocked** | Pulse `FFFFB830` ⇄ `FF8F5E12` (2.0s) | Pulse `FFFFB830` ⇄ `FF7A5210` (2.0s) | **Warm amber lamp**: signals "Available to begin" |
| **Pending** | Pulse `FF55FF48` ⇄ `FF124E20` (1.2s) | `#FF2FFF21` (static phosphor) | **High-frequency beacon**: signals "Claim reward!" |
| **Complete** | `#FF2FFF21` (static phosphor) | `#FF1E5E2E` (static dark emerald) | Solid stable green: completed and archived |
| **Repeatable**| Pulse `FFFF9520` ⇄ `FF94500C` (1.5s) | Pulse `FFFF9520` ⇄ `FF94500C` (1.5s) | Warm bronze/amber indicator for repeatable quests |

---

## 5. Checkbox Icons & Color Tinting

`PanelTaskCheckbox` applies dynamic OpenGL color multiplication to checkbox icons:
- Incomplete: tinted by `0xFFFF0000` (pure red).
- Complete: tinted by `0xFF00FF00` (pure green).

### Rules for Atlas Drawing:
1. **Draw foreground strokes in pure white (`0xFFFFFFFF`)**: `(1, 1, 1) * tint = tint`. The icon becomes vivid red or green.
2. **Draw drop shadow in semi-transparent black (`0x80000000`)**: `(0, 0, 0) * tint = (0, 0, 0)`. The shadow stays black regardless of color tint, preserving depth and contrast on dark buttons.
3. Register as `betterquesting:texture_simple` in `bq_themes.json`:
   ```json
   "betterquesting:icon_tick": {
     "textureType": "betterquesting:texture_simple",
     "atlas": "my_theme:textures/gui/atlas.png",
     "bounds": [208, 64, 16, 16]
   }
   ```

---

## 6. Procedural Atlas Generation

Avoid editing 256×256 atlases manually in raster image editors. Use an automated Java builder (`references/BuildAtlasTemplate.java`) with `BufferedImage` and `Graphics2D` (`VALUE_ANTIALIAS_OFF`).  
This ensures:
- Sub-pixel alignment accuracy (no off-by-one errors).
- Clean mathematical bevels and drop shadows.
- Instant batch export to both target instances.

---

## 7. Common Pitfalls & Solutions

| Pitfall | Cause | Solution |
| :--- | :--- | :--- |
| **Lines become massive bars on zoom out** | Theme uses `line_simple` (`glLineWidth`) | Switch all line definitions to `line_directional` with `arrowOpacity: 0.0` and `widthScale: 0.75`. |
| **Corner brackets repeating along panel edges** | Insets in `padding` are smaller than corner feature dimensions | Ensure padding values `[L, T, R, B]` are strictly larger than the drawn corner decorations. |
| **Checkbox icons render in muddy/dark colors** | Icon on atlas was drawn in gray/green instead of white | Redraw icon strokes in pure `0xFFFFFFFF` so OpenGL color multiplication works cleanly. |
| **Broken JSON crashing theme load** | Trailing comma or syntax error | Run PowerShell JSON validation: `Get-Content bq_themes.json -Raw \| ConvertFrom-Json` before launching. |
| **Textures look blurry / interpolated** | Sliced texture mode set to `2` (Stretched) on pixel-art borders | Set `sliceMode: 1` (Tiled) for window panels and buttons. |

---

## 8. Verification & Sync Workflow

1. Validate JSON syntax:
   ```powershell
   Get-Content config/betterquesting/resources/<theme_id>/bq_themes.json -Raw | ConvertFrom-Json | Out-Null
   ```
2. Re-bake atlas if code modified:
   ```powershell
   javac -encoding UTF-8 references/BuildAtlasTemplate.java
   java -cp references BuildAtlasTemplate
   ```
3. Sync files to both instances (`NTNH_tx_bcraft` and `DEV_NTNH`) and verify SHA-256 match.
4. Press **`F3 + T`** in the running Minecraft client to hot-reload textures and JSON instantly without restarting the game.
