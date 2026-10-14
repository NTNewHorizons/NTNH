recipes.remove(<minecraft:stonebrick:1>);
recipes.remove(<minecraft:mossy_cobblestone>);
recipes.remove(<minecraft:chainmail_boots>);
recipes.remove(<minecraft:chainmail_helmet>);
recipes.remove(<minecraft:chainmail_leggings>);
recipes.remove(<minecraft:chainmail_chestplate>);
recipes.remove(<minecraft:anvil>);
recipes.remove(<minecraft:flint_and_steel>);
recipes.removeShaped(<minecraft:cauldron>, [[<ore:ingotIron>, null, <ore:ingotIron>], [<ore:ingotIron>, null, <ore:ingotIron>], [<ore:ingotIron>, <ore:ingotIron>, <ore:ingotIron>]]);
recipes.remove(<minecraft:iron_door>);


recipes.removeShaped(<minecraft:fire>, [[<ore:cobblestone>, null, null], [<ore:cobblestone>, null, null], [<minecraft:dirt>, <minecraft:dirt>, <minecraft:dirt>]]);


recipes.addShapeless(<minecraft:mossy_cobblestone>, [<minecraft:cobblestone>, <minecraft:vine>]);
recipes.addShapeless(<minecraft:stonebrick:1>, [<minecraft:stonebrick>, <minecraft:vine>]);
recipes.addShapeless(<minecraft:flint_and_steel>, [<ore:ingotSteel>, <minecraft:flint>]);
recipes.addShaped(<minecraft:anvil>, [[<ore:ingotIron>, <ore:ingotIron>, <ore:ingotIron>], [null, <ore:ingotIron>, null], [<ore:ingotIron>, <ore:ingotIron>, <ore:ingotIron>]]);
recipes.addShaped(<minecraft:cauldron>, [[<ore:plateLead>, null, <ore:plateLead>], [<ore:plateLead>, null, <ore:plateLead>], [<ore:plateLead>, <ore:plateLead>, <ore:plateLead>]]);

recipes.remove(<minecraft:wooden_axe>);
recipes.remove(<minecraft:wooden_pickaxe>);

recipes.addShapeless(<minecraft:stick> * 2, [<ore:treeSapling>]);

recipes.addShaped(<minecraft:flint> * 2, [[<minecraft:gravel>, <minecraft:gravel>], [<minecraft:gravel>, null]]);

recipes.addShaped(<minecraft:iron_door>, [[<ore:ingotIron>, <ore:ingotIron>], [<ore:ingotIron>, <ore:ingotIron>], [<ore:ingotIron>, <ore:ingotIron>]]);
