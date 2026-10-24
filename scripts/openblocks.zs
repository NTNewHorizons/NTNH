recipes.remove(<OpenBlocks:hangglider>);
recipes.remove(<OpenBlocks:generic>);
recipes.remove(<OpenBlocks:sponge>);

recipes.addShaped(<OpenBlocks:hangglider>, [[<OpenBlocks:generic>, <hbm:tile.steel_beam>, <OpenBlocks:generic>]]);
recipes.addShaped(<OpenBlocks:generic>, [[null, null, <ore:wireFineSteel>], [null, <ore:wireFineSteel>, <minecraft:leather>], [<ore:wireFineSteel>, <minecraft:leather>, <minecraft:leather>]]);
recipes.addShapeless(<OpenBlocks:sponge>, [<minecraft:sponge>, <ore:container1000amat>]);

recipes.remove(<OpenBlocks:elevator>);

recipes.addShaped(<OpenBlocks:elevator>, [
    [<ore:ingotAnyHardPlastic>, <ore:ingotAnyHardPlastic>, <ore:ingotAnyHardPlastic>],
    [<hbm:item.fins_tri_steel>, <hbm:item.entanglement_kit>, <hbm:item.fins_tri_steel>],
    [<ore:ingotAnyHardPlastic>, <ore:ingotAnyHardPlastic>, <ore:ingotAnyHardPlastic>]
]);
