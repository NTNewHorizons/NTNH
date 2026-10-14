# Better Questing: `editor_icons.png` (256×256) Atlas Reference Map

This reference documents the pixel coordinates, `PresetIcon` constants, and visual specifications for the sidebar and editor icon atlas in **Better Questing 3.8.74-GTNH** under the **NTNH JAPPA UI 3D** design standard.

---

## 1. Safe-Zone Inset Invariant (10px Inner Face Rule)

In Better Questing, sidebar navigation buttons (`PanelButton`) measure $32 \times 16$ px (or $16 \times 16$ px).

- **Vertical Anatomy of a 16px Button:**
  - `y ∈ [0..2]` (3px): Top Highlight Bevel (illuminated slope).
  - `y ∈ [3..12]` (10px): **Flat Clickable Inner Face (Safe-Zone)**.
  - `y ∈ [13..15]` (3px): Bottom Shadow Bevel (recessed slope).
- **The Rule:** Any pixel rendered at $y < 3$ or $y > 12$ overlaps the 3D bevels, causing the optical illusion that the icon is hovering above the button rather than being embossed/engraved into it.
- **Maximum Glyph Dimensions:** $10 \times 10$ pixels. Keep margins of $\ge 3$ px from the top/bottom and $\ge 2$ px from left/right edges within each $16 \times 16$ cell.

---

## 2. Atlas Coordinate Map (256×256 ARGB)

Grid unit: $16 \times 16$ pixels per slot.

| Coordinate `[X, Y]` | PresetIcon Identifier | In-Game Button / Function | Visual Description (JAPPA 3D) | Safe-Zone Bounds |
| :---: | :--- | :--- | :--- | :---: |
| `[176, 64]` | `ICON_BOOKMARK` | Bookmarks Panel | Crimson silk ribbon with gold mounting clip and notched fishtail | $8 \times 10$ ($y \in [3..12]$) |
| `[96, 0]` | `ICON_VIEW_MODE_ON` | Free View Mode (Active) | Sapphire observer eye with deep pupil and specular reflection | $10 \times 6$ ($y \in [5..10]$) |
| `[240, 80]` | `ICON_VIEW_MODE_OFF` | Free View Mode (Inactive) | Sapphire eye with ruby diagonal slash | $10 \times 8$ ($y \in [4..11]$) |
| `[192, 80]`<br>`[208, 80]`<br>`[224, 80]` | `ICON_VISIBILITY_NORMAL` | Draw Implicit Dependencies | Quest link diagram: cyan diamond node $\to$ white arrow $\to$ golden square node | $11 \times 7$ ($y \in [4..10]$) |
| `[144, 64]`<br>`[160, 64]` | `ICON_CHEST_ALL` | Claim All Rewards | JAPPA treasure chest (oak wood, gold latch, phosphor reward sparkle) | $10 \times 9$ ($y \in [3..11]$) |
| `[176, 0]` | `ICON_NOTICE` | Notification Settings | Golden heraldic shield badge with dark graphite exclamation mark `!` | $9 \times 10$ ($y \in [3..12]$) |
| `[224, 48]`<br>`[160, 0]` | `ICON_PG_PREV` | Return / Back / Exit | 100% mathematically symmetric return arrow $\↰$ (centerline $y=7.5$, 90° corner) | $9 \times 8$ ($y \in [4..11]$) |
| `[48, 48]`<br>`[208, 0]` | `ICON_THEME_SELECT` | Theme Selection | Birch artist palette with ruby, gold, and cyan paint drops | $9 \times 9$ ($y \in [3..11]$) |
| `[112, 64]` | `ICON_HEART` | Hardcore Lives | Faceted ruby heart with top-left specular highlight | $9 \times 8$ ($y \in [4..11]$) |
| `[80, 0]` | `ICON_TREE` | Quest Line Navigation | Golden trunk line branching into diamond and emerald quest nodes | $8 \times 9$ ($y \in [3..11]$) |
| `[208, 64]` | `ICON_DESC` | Quest Description Panel | Parchment document with folded corner and 3 dark text lines | $8 \times 10$ ($y \in [3..12]$) |
| `[64, 80]`<br>`[80, 80]` | `ICON_INFO` | Quest Info / Guide | Stylized golden exclamation mark with 2px gap above dot | $4 \times 10$ ($y \in [3..12]$) |
| `[80, 64]` | `ICON_ZOOM_FIT` | Fit Quest Line to Screen | 4 silver corner brackets framing the viewport area | $8 \times 9$ ($y \in [3..11]$) |
| `[0, 96]` | `ICON_FILTER_LOCKED` | Hide / Show Locked Quests | Mini clipboard with green completed and red locked checkmarks | $8 \times 9$ ($y \in [3..11]$) |
| `[64, 16]`<br>`[80, 16]` | `ICON_SORT` | Sort Quests Alphabetically | Bi-directional arrows: silver Up arrow and golden Down arrow | $8 \times 9$ ($y \in [3..11]$) |
| `[96, 64]` | `ICON_SEARCH` | Search Quests (Loupe) | Brass magnifying glass with cyan glass reflection and 45° wooden handle | $10 \times 10$ ($y \in [3..12]$) |

---

## 3. Disambiguation Invariants

Never map identical icons to functions in the same sidebar:
1. **`ICON_CHEST_ALL` vs `ICON_NOTICE`**:
   - `ICON_CHEST_ALL` **must** remain a chest (physical container for reward items).
   - `ICON_NOTICE` **must** remain a heraldic shield badge with `!` (configuration setting).
2. **`ICON_VIEW_MODE_ON` vs `ICON_VISIBILITY_NORMAL`**:
   - `ICON_VIEW_MODE_ON` **must** remain a sapphire eye (viewport observer).
   - `ICON_VISIBILITY_NORMAL` **must** remain a diagram of connected dependency nodes (graph connections).

---

## 4. Toggle Modulation Note

Better Questing toggle buttons **do NOT switch textures** between on/off states in code (both states reference the same texture key).  
The engine modulates the icon using OpenGL color multiplication:
- **Active / ON**: `0xFFFFFFFF` (full asset color).
- **Inactive / OFF**: `0xFF444444` (darkened to 27% brightness).

**Design Consequence:** Every icon must possess a distinct, high-contrast silhouette that is immediately recognizable in pure dark grayscale.
