---
navigation:
  title: "Ore Generation"
  icon: minecraft:iron_ore
  position: 6
categories:
  - world
  - ores
---

# &6Ore Generation Reference

> [!NOTE]
> This page documents the ore generation configured in NT:NH, driven entirely by
> **CustomOreGen**. See `config/CustomOreGen/modules/custom/` for the source of truth.

---

## &6How to Read This Page

| &bConvention | &bMeaning |
|---|---|
| &bBiome.* | Matches the biome and every variant of it, e.g. &oForest.* covers Forest, Forest Hills and Flower Forest |
| &bAll Taiga variants | Matches Taiga, Taiga Hills, Taiga M, Cold Taiga, Mega Taiga, Redwood Taiga and their variants |
| &bAll Ocean variants | Matches Ocean and Deep Ocean |
| &bY range | Minimum and maximum height the deposit centre can appear at |
| &bFreq | Distribution frequency, scaled by the global &ooreFreq &oslider |
| &bDensity | Fraction of each cloud volume that actually becomes ore |

The global **Ore Frequency** and **Ore Deposit Size** sliders (`oreFreq`, `oreSize`, both ranging
0.25-3 with a default of 1) scale every frequency and size listed here. Raising frequency or size
will not change which biomes an ore appears in.

Vanilla ore generation is **disabled** (`vanillaOreGen = false`) so that the distributions below
are the only source of ore. Nether Quartz is the one exception: it is reproduced with vanilla's
own vein parameters, copied into the Nether module so it still generates normally.

---

## &6Kerbin (Overworld)

| &bOre Name | &bBiomes / Regions | &bDistribution | &bFreq | &bDensity | &bHeight Range (Y) |
|---|---|---|---|---|---|
| Aluminium | All Taiga variants | Cloud | 0.019 | 10% | 16 - 56 |
| Aluminium | Jungle.* | StandardGen | 0.060 | - | 7 - 59 |
| Asbestos | All Taiga variants | Cloud | 0.018 | 9% | 16 - 48 |
| Beryllium | Extreme Hills.*, Ice Mountains | Cloud | 0.016 | 8% | 8 - 40 |
| Coal | Extreme Hills.*, Stone Beach, Ice Mountains | Cloud (seam) | 0.015 | 14% | 20 - 60 |
| Cobalt | Savanna.* | Cloud | 0.016 | 8% | 8 - 40 |
| Coltan | Jungle.* | StandardGen | 0.040 | - | 10 - 30 |
| Copper | Plains, Sunflower Plains, Ice Plains.* | Cloud | 0.019 | 10% | 16 - 56 |
| Diamond | Extreme Hills.*, Ice Mountains | StandardGen | 0.080 | - | 6 - 16 |
| Diamond | All Taiga variants | StandardGen | 0.060 | - | 6 - 16 |
| Fluorite | All Ocean variants | Cloud | 0.019 | 10% | 16 - 56 |
| Gold | Desert.* | Cloud | 0.016 | 8% | 8 - 40 |
| Gold | Jungle.* | StandardGen | 0.060 | - | 14 - 38 |
| Iron | Savanna.* | Cloud | 0.014 | 11% | 16 - 56 |
| Iron | Plains, Sunflower Plains, Ice Plains.* | Cloud | 0.019 | 10% | 16 - 56 |
| Lapis Lazuli | Forest.*, Roofed Forest.*, All Ocean variants | Cloud | 0.016 | 8% | 8 - 40 |
| Lead | Forest.*, Roofed Forest.* | Cloud | 0.018 | 9% | 16 - 48 |
| Lignite | Swampland.* | StandardGen | 0.050 | - | 26 - 54 |
| Lithium | Mesa.* | Cloud | 0.018 | 9% | 16 - 48 |
| Niter | Desert.* | Cloud | 0.018 | 9% | 16 - 48 |
| Quartz | All Ocean variants | Cloud (AE2) | 0.018 | 9% | 16 - 48 |
| Rare Earth | Graphite Gneiss, all biomes | StandardGen | 22.000 | - | 6 - 66 |
| Redstone | Mesa.* | Cloud | 0.016 | 8% | 8 - 40 |
| Redstone | Savanna.* | Cloud | 0.018 | 9% | 16 - 48 |
| Redstone | Forest.*, Roofed Forest.* | Cloud | 0.019 | 10% | 16 - 56 |
| Salt (Pam's) | Roofed Forest.* | Cloud | 0.019 | 10% | 16 - 56 |
| Sulfur | Mesa.* | Cloud | 0.019 | 10% | 16 - 56 |
| Sulfur | Swampland.* | StandardGen | 0.060 | - | 10 - 50 |
| Thorium | Savanna.* | Cloud | 0.016 | 8% | 8 - 40 |
| Titanium | Desert.*, Mesa.*, Jungle.* | Cloud | 0.016 | 8% | 8 - 40 |
| Tungsten | Extreme Hills.*, Ice Mountains | Cloud | 0.018 | 9% | 16 - 48 |
| Uranium | All Taiga variants | Cloud | 0.016 | 8% | 8 - 40 |
| Zinc | Forest.*, Roofed Forest.* | Cloud | 0.016 | 8% | 8 - 40 |

> [!TIP]
> Rare Earth only generates inside **Graphite Gneiss**, never in ordinary stone. Its frequency is
> very high because it is restricted to that single block type.
>
> Pam's **Salt** ore is a separate item from HBM **Niter**. Salt is a Roofed Forest evaporite;
> Niter is a Desert deposit. They are unrelated and are not interchangeable in recipes.

---

## &6Mun

*Dimension 15, host rock: Moon Rock*

| &bOre Name | &bDistribution | &bFreq | &bDensity | &bHeight Range (Y) |
|---|---|---|---|---|
| Fluorite | Cloud | 0.030 | 80% | 25 - 45 |
| Iron | Cloud | 0.030 | 80% | 30 - 60 |
| Lead | Cloud | 0.030 | 80% | 10 - 30 |
| Nickel | Cloud | 0.030 | 80% | 15 - 45 |
| Sulfur | Cloud | 0.030 | 80% | 45 - 65 |

---

## &6Minmus

*Dimension 21, host rock: Minmus Stone*

| &bOre Name | &bBiomes | &bDistribution | &bFreq | &bDensity | &bHeight Range (Y) |
|---|---|---|---|---|---|
| Copper | Minmus Basins, Minmus Hills | Cloud | 0.040 | 70% | 30 - 60 |
| Niter | Minmus Basins, Minmus Hills | Cloud | 0.040 | 70% | 15 - 35 |
| Quartz | Minmus Basins, Minmus Hills | Cloud | 0.040 | 70% | 15 - 35 |

---

## &6Duna

*Dimension 16, host rock: Duna Rock. All deposits are thin sinuous Bezier veins.*

| &bOre Name | &bDistribution | &bFreq | &bDensity | &bHeight Range (Y) |
|---|---|---|---|---|
| Aluminium | Veins | 0.040 | 90% | 15 - 55 |
| Beryllium | Veins | 0.040 | 90% | 15 - 55 |
| Rare Earth | Veins | 0.040 | 90% | 15 - 55 |
| Redstone | Veins | 0.040 | 90% | 15 - 55 |
| Titanium | Veins | 0.040 | 90% | 15 - 55 |
| Zinc | Veins | 0.040 | 90% | 15 - 55 |

---

## &6Ike

*Dimension 17, host rock: Ike Stone. All deposits are thick Bezier veins.*

| &bOre Name | &bDistribution | &bFreq | &bDensity | &bHeight Range (Y) |
|---|---|---|---|---|
| Lanthanum | Veins | 0.050 | 90% | 15 - 55 |
| Plutonium | Veins | 0.050 | 90% | 15 - 55 |
| Uranium | Veins | 0.050 | 90% | 15 - 55 |

---

## &6Laythe

*Dimension 22, host rock: Laythe Stone. Deposits are wide, flat and thin.*

| &bOre Name | &bBiomes | &bDistribution | &bFreq | &bDensity | &bHeight Range (Y) |
|---|---|---|---|---|---|
| Aluminium | Laythe Islands, Laythe Poles, Laythe Reef, Sagan Sea | Cloud | 0.020 | 85% | 25 - 55 |
| Asbestos | Laythe Islands, Laythe Poles, Laythe Reef, Sagan Sea | Cloud | 0.020 | 85% | 40 - 60 |
| Tungsten | Laythe Islands, Laythe Poles, Laythe Reef, Sagan Sea | Cloud | 0.020 | 85% | 10 - 20 |

---

## &6Eve

*Dimension 18, host rock: Eve Rock*

| &bOre Name | &bBiomes | &bDistribution | &bFreq | &bDensity | &bHeight Range (Y) |
|---|---|---|---|---|---|
| Cobalt | Eve Mountains, Eve Plains, Eve Seismic Plains, Explodium Ocean | Cloud | 0.030 | 80% | 40 - 70 |
| Iodine | Eve Mountains, Eve Plains, Eve Seismic Plains, Explodium Ocean | Cloud | 0.030 | 80% | 6 - 62 |
| Niobium | Eve Mountains, Eve Plains, Eve Seismic Plains, Explodium Ocean | Cloud | 0.030 | 80% | 20 - 50 |
| Schrabidium | Eve Mountains, Eve Plains, Eve Seismic Plains, Explodium Ocean | Cloud | 0.030 | 80% | 7 - 25 |

---

## &6Dres

*Dimension 19, host rock: Dres Rock*

| &bOre Name | &bBiomes | &bDistribution | &bFreq | &bDensity | &bHeight Range (Y) |
|---|---|---|---|---|---|
| Coltan | Dres Large Basins, Dresian Flains | Cloud | 0.030 | 80% | 15 - 45 |
| Diamond | Dres Large Basins, Dresian Flains | Cloud | 0.030 | 80% | 6 - 20 |
| Lanthanum | Dres Large Basins, Dresian Flains | Cloud | 0.030 | 80% | 10 - 40 |
| Niobium | Dres Large Basins, Dresian Flains | Cloud | 0.030 | 80% | 20 - 50 |

---

## &6Moho

*Dimension 20, host rock: Moho Stone. Deposits are deliberately rare.*

| &bOre Name | &bBiomes | &bDistribution | &bFreq | &bDensity | &bHeight Range (Y) |
|---|---|---|---|---|---|
| Australium | Moho Lava Sea, Moho Crag, Moho Plateau | Cloud | 0.010 | 75% | 8 - 16 |
| Cinnabar | Moho Lava Sea, Moho Crag, Moho Plateau | Cloud | 0.010 | 75% | 20 - 40 |
| Red Phosphorus | Moho Lava Sea, Moho Crag, Moho Plateau | Cloud | 0.010 | 75% | 40 - 60 |

---

## &6Tekto

*Dimension 24, host rock: Basalt. All deposits are straight Bezier veins.*

| &bOre Name | &bBiomes | &bDistribution | &bFreq | &bDensity | &bHeight Range (Y) |
|---|---|---|---|---|---|
| Beryllium | Halogen Hills, Polyvinyl Plains, Vinyl Desert, Tekto Forest | Veins | 0.050 | 90% | 45 - 65 |
| Lithium | Halogen Hills, Polyvinyl Plains, Vinyl Desert, Tekto Forest | Veins | 0.050 | 90% | 29 - 49 |
| Plutonium | Halogen Hills, Polyvinyl Plains, Vinyl Desert, Tekto Forest | Veins | 0.050 | 90% | 10 - 20 |

---

## &6Nether

| &bOre Name | &bBiomes | &bDistribution | &bFreq | &bHeight Range (Y) |
|---|---|---|---|---|
| Nether Quartz | All biomes | StandardGen (vanilla) | 8.667 | 10 - 108 |

> [!NOTE]
> Nether Quartz reproduces vanilla Minecraft's quartz generation, including its wide depth range,
> because disabling vanilla ore generation would otherwise leave the Nether with no quartz at all.
> No other vanilla ore is generated anywhere.

---

## &6Reference Tables

### &eDistribution Types

| &bDistribution | &bDescription |
|---|---|
| &bCloud | Large, diffuse ore clouds with variable radius and thickness. Deposits spread across a wide area with noise-based density, replacing stone in blob-like formations. Tuned to stay diffuse so VeinMiner cannot chain through them. |
| &9StandardGen | Classic Minecraft-style ore clusters. Uses fixed Size and Frequency values to generate compact, spherical ore blobs at a specific height range. |
| &dVeins | Long, branching vein networks using Bezier curves. Motherlodes spawn branch segments that snake through the rock, creating realistic geological vein patterns. |

### &eReading Frequency and Density

| &bValue | &bMeaning |
|---|---|
| High **Freq** (0.018 and up) | Several deposits per chunk region. Common, easy to find. |
| Low **Freq** (0.010 - 0.014) | Rare. Moho's entire ore set sits here, so expect to dig for it. |
| Low **Density** (8-14%) | Ore is scattered as isolated one or two block inclusions. VeinMiner will not chain. |
| High **Density** (70-90%) | Solid ore. Planetary and lunar deposits are close to fully solid veins. |

Planet and moon bodies use much higher density but far lower frequency than Kerbin, so deposits
there are large and solid but sparse. Kerbin is the opposite: frequent, thin and scattered.