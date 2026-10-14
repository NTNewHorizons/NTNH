---
navigation:
  title: "Crop Stats & Genetics"
  icon: AgriCraft:seedAnalyzer
  parent: ./index.md
  position: 60
categories:
  - farming
  - agriculture
---
# Crop Stats & Genetics

Every AgriCraft seed has three stats, rated from 1 to 10. Higher is always better.

## The Three Stats

| Stat | Effect |
|------|--------|
| **Growth** | Speed of growth. Higher = faster maturation. |
| **Gain** | Harvest yield. Higher = more produce per harvest. |
| **Strength** | Ability to pass stats to offspring. Higher = better inheritance. |

## Mendelian Genetics

AgriCraft uses Mendelian genetics. Each stat has a pair of alleles (genes). When cross-breeding, offspring inherit one allele from each parent.

Example for a stat where 10 is dominant and 1 is recessive:

| Parent 1 | Parent 2 | Offspring Possibilities |
|----------|----------|------------------------|
| 10 / 1 | 10 / 1 | 10/10 (25%), 10/1 (50%), 1/1 (25%) |

The displayed stat is always the **higher** allele - so 10/1 shows as 10.

| Gene Pair | Displayed Stat |
|-----------|---------------|
| 10 / 10 | 10 |
| 10 / 1 | 10 |
| 1 / 1 | 1 |

## Improving Stats

1. Plant several crops of the same species next to a Crosscrop.
2. When a new sprout appears on the Crosscrop, it inherits stats from all nearby mature plants.
3. The more high-stat neighbors, the better the chance of improvement.
4. Rinse and repeat until you reach 10/10/10.

> [!IMPORTANT]
> Only crops of the same species or direct mutation parents count as valid for stat boosting. Unrelated crops will **lower** the offspring's stats instead. A single crop spreading alone cannot improve stats - you need at least 2 valid parents.

> [!TIP]
> Always breed from your best seed. Analyze offspring in the Seed Analyzer to track improvement. Use the Clipper to copy your best seeds.

## Mutations and Stats

When a mutation occurs (producing a new species), the resulting crop's stats are divided by 2 (the configured stat divisor). This means a mutation from two 10/10/10 parents will start at 5/5/5. You must then improve these stats again through cross-breeding.

> [!IMPORTANT]
> Always max out stats (10/10/10) on a crop before using it as a parent for mutations. Your mutated crop will start with much better base stats.

## Seed Analyzer

Place any seed in the **Seed Analyzer** to see its exact stats:

- Opens your **Journal** with crop information
- Each seed must be analyzed once; analyzed seeds can be viewed anytime
- The Journal tracks all analyzed species and their known stats

## Seed Storage Controller

Pair a Seed Storage Controller with chests to organize your breeding stock. It auto-sorts seeds by type and stat level, making it easy to find your best specimens.

## Tips for 10/10/10

1. **Start small** - find a seed in grass or craft one
2. **Analyze everything** - only the Analyzer reveals true stats
3. **Breed high with high** - only use your best seeds as parents
4. **Copy winners** - use the Clipper on mature 10/10/10 crops
5. **Use the Trowel** - move your best crops to the center of breeding arrays
6. **Check NEI** - view stat information on any seed by hovering
