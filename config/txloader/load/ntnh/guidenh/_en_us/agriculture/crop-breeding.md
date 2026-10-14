---
navigation:
  title: "Crop Cross-breeding"
  icon: AgriCraft:crops
  parent: ./index.md
  position: 50
categories:
  - farming
  - agriculture
---
# Crop Cross-breeding

Cross-breeding is the core of AgriCraft. By placing mature crops next to a Crosscrop, you can produce mutations - entirely new plant species.

<GameScene width="384" height="256" zoom={3} perspective="isometric-north-east" interactive={true}>
  <ImportStructure src="/assets/agriculture_ponders/farm_plot.snbt" />
  <ImportPonder src="/assets/agriculture_ponders/planting_and_crossbreeding.json" />
</GameScene>

Press **Play** to see an animated walkthrough of setting up a breeding plot.

## Setup

Place Crop Sticks on farmland and right-click again to turn them into a **Crosscrop**. Surround it with mature (fully grown) crops. The more mature neighbors, the higher the chance of a mutation.

> [!NOTE]
> A Crosscrop must have at least **2** mature neighbors for mutations to occur.

## How Mutations Work

1. The Crosscrop checks for a mutation (20% chance by default).
2. If a mutation is possible from the surrounding parent crops, it picks one at random.
3. The new plant must be able to grow on the soil it is on.
4. Higher-tier crops are harder to mutate.
5. If no mutation triggers, one of the parent crops spreads to the crosscrop instead (copying its species with averaged stats).

## Important Mechanics

**Bonemeal does not force mutations** - Using bonemeal on a Crosscrop has no special effect.

**Valid parents for stat boosting** - Only crops of the same species or mutation parents count. Crops that are neither will lower stat gain.

**Non-parent crops hurt stats** - Any crop next to a crosscrop that is not a valid parent reduces stat gain. Keep your breeding plots clean.

## Patterns for Stat Boosting

Place low-stat crops between high-stat crops of the same species. The offspring inherits stats from all valid parents.

| Parents | Stat Outcome |
|---------|-------------|
| 2 matching species | 25% +1 increment, 50% no change, 25% -1 decrement |
| 3 matching species | 25% increment, 50% no change, 25% decrement |
| 4 matching species | 50% increment, 25% no change, 25% decrement |

> [!TIP]
> Keep only your target species around the crosscrop. Unrelated crops will lower the offspring's stats.

## Mutation Table

These are the mutations configured in NTNH. The first listed crop + the second produce the result.

### Vanilla & Basic Crops

| Result | Parent A | Parent B |
|--------|----------|----------|
| Sugarcane | Wheat Seeds | Carrot |
| Cactus | Sugarcane | Poppy |
| Pumpkin | Potato | Carrot |
| Melon | Carrot | Pumpkin |
| Poppy | Sugarcane | Melon |
| Dandelion | Sugarcane | Melon |
| Blue Orchid | Poppy | Dandelion |
| Allium | Poppy | Blue Orchid |
| Red Tulip | Poppy | Allium |
| Orange Tulip | Daisy | Blue Orchid |
| White Tulip | Daisy | Dandelion |
| Pink Tulip | Allium | Dandelion |
| Daisy | Dandelion | Blue Orchid |
| Red Mushroom | Nether Wart | Poppy |
| Brown Mushroom | Nether Wart | Potato |

### Pam's HarvestCraft Mutations

| Result | Parent A | Parent B |
|--------|----------|----------|
| Artichoke | Asparagus | Lettuce |
| Asparagus | Scallion | Corn |
| Bamboo Shoot | Corn | Rice |
| Bean | Pumpkin Seeds | Potato |
| Beet | Radish | Carrot |
| Bell Pepper | Chili Pepper | Spice Leaf |
| Blackberry | Strawberry | Blueberry |
| Blueberry | Strawberry | Blue Orchid |
| Broccoli | Lettuce | Daisy |
| Brussels Sprout | Cabbage | Pea |
| Cabbage | Broccoli | Lettuce |
| Cactus Fruit | Kiwi | Bamboo Shoot |
| Candleberry | Cactus Fruit | Grape |
| Cantaloupe | Melon Seeds | Strawberry |
| Cauliflower | Cabbage | Lettuce |
| Celery | Wheat Seeds | Allium |
| Chili Pepper | Tomato | Onion |
| Coffee | Bean | Sugarcane |
| Corn | Barley | Rye |
| Cotton | Barley | Daisy |
| Cranberry | Blueberry | Grape |
| Cucumber | Pea | Okra |
| Eggplant | Zucchini | Tomato |
| Garlic | Onion | Ginger |
| Ginger | Mustard | Peanut |
| Grape | Blueberry | Cantaloupe |
| Kiwi | Cantaloupe | Strawberry |
| Leek | Scallion | Celery |
| Lettuce | Daisy | Celery |
| Mustard | Chili Pepper | Bean |
| Oats | Corn | Rice |
| Okra | Bean | Leek |
| Onion | Celery | Brussels Sprout |
| Parsnip | Carrot | Beet |
| Peanut | Pea | Bamboo Shoot |
| Pea | Soybean | Okra |
| Pineapple | Bamboo Shoot | Cantaloupe |
| Radish | Tomato | Brussels Sprout |
| Raspberry | Strawberry | Red Tulip |
| Rhubarb | Sugarcane | Lettuce |
| Rice | Rye | Sugarcane |
| Rutabaga | Beet | Turnip |
| Rye | Barley | Wheat Seeds |
| Scallion | Carrot | Sugarcane |
| Seaweed | Lettuce | Celery |
| Soybean | Bean | Rice |
| Spice Leaf | Tea | Chili Pepper |
| Spinach | Lettuce | Cactus |
| Strawberry | Radish | Poppy |
| Sweet Potato | Potato | Sugarcane |
| Tea | Seaweed | Daisy |
| Tomato | Sweet Potato | Carrot |
| Turnip | Parsnip | Radish |
| White Mushroom | Red Mushroom | Brown Mushroom |
| Winter Squash | Pumpkin Seeds | Zucchini |
| Zucchini | Pumpkin Seeds | Cucumber |
| Curry Leaf | Spice Leaf | Mustard |
| Sesame Seeds | Rice | Coffee |
| Water Chestnut | Sesame Seeds | Seaweed |
| Barley | Wheat Seeds | Sugarcane |

> [!TIP]
> Use NEI mutations view (R on the seed) to see all breeding paths in-game.
