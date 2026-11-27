recipes.remove(<StorageDrawers:halfDrawers4>);
recipes.remove(<StorageDrawers:halfDrawers2>);
recipes.remove(<StorageDrawers:fullDrawers4>);
recipes.remove(<StorageDrawers:fullDrawers2>);
recipes.remove(<StorageDrawers:fullDrawers1>);
recipes.remove(<StorageDrawers:controllerSlave>);
recipes.remove(<StorageDrawers:compDrawers>);
recipes.remove(<StorageDrawers:controller>);

recipes.removeShaped(<StorageDrawers:compDrawers>, [[<minecraft:piston>, <hbm:tile.mass_storage>, <ore:craftingPiston>]]);

recipes.addShaped(<StorageDrawers:halfDrawers4> * 4, [[<hbm:tile.mass_storage:3>, <ore:slabWood>, <hbm:tile.mass_storage:3>], [<ore:slabWood>, <ore:slabWood>, <ore:slabWood>], [<hbm:tile.mass_storage:3>, <ore:slabWood>, <hbm:tile.mass_storage:3>]]);
recipes.addShaped(<StorageDrawers:halfDrawers2> * 2, [[<ore:slabWood>, <hbm:tile.mass_storage:3>, <ore:slabWood>], [<ore:slabWood>, <ore:slabWood>, <ore:slabWood>], [<ore:slabWood>, <hbm:tile.mass_storage:3>, <ore:slabWood>]]);
recipes.addShaped(<StorageDrawers:controllerSlave>, [[null, <ore:plateSteel>, null], [<ore:plateSteel>, <StorageDrawers:trim>, <ore:plateSteel>], [null, <ore:plateSteel>, null]]);
recipes.addShaped(<StorageDrawers:controller>, [[null, <ore:plateSteel>, null], [<ore:plateSteel>, <ore:drawerBasic>, <ore:plateSteel>], [null, <ore:plateSteel>, null]]);
recipes.addShaped(<StorageDrawers:compDrawers>, [[<ore:craftingPiston>, <ore:drawerBasic>, <ore:craftingPiston>]]);
recipes.addShaped(<StorageDrawers:fullDrawers4> * 4, [[<hbm:tile.mass_storage:3>, <ore:plankWood>, <hbm:tile.mass_storage:3>], [<ore:plankWood>, <ore:plankWood>, <ore:plankWood>], [<hbm:tile.mass_storage:3>, <ore:plankWood>, <hbm:tile.mass_storage:3>]]);
recipes.addShaped(<StorageDrawers:fullDrawers2> * 2, [[<ore:plankWood>, <hbm:tile.mass_storage:3>, <ore:plankWood>], [<ore:plankWood>, <ore:plankWood>, <ore:plankWood>], [<ore:plankWood>, <hbm:tile.mass_storage:3>, <ore:plankWood>]]);
recipes.addShaped(<StorageDrawers:fullDrawers1>, [[<ore:plankWood>, <ore:plankWood>, <ore:plankWood>], [null, <hbm:tile.mass_storage:3>, null], [<ore:plankWood>, <ore:plankWood>, <ore:plankWood>]]);

// Framed drawers: same Mass Storage Unit cost as the normal drawers, with sticks for the frame
recipes.remove(<StorageDrawers:fullCustom1>);
recipes.remove(<StorageDrawers:fullCustom2>);
recipes.remove(<StorageDrawers:fullCustom4>);
recipes.remove(<StorageDrawers:halfCustom2>);
recipes.remove(<StorageDrawers:halfCustom4>);

recipes.addShaped(<StorageDrawers:fullCustom1>, [[<ore:stickWood>, <ore:stickWood>, <ore:stickWood>], [null, <hbm:tile.mass_storage:3>, null], [<ore:stickWood>, <ore:stickWood>, <ore:stickWood>]]);
recipes.addShaped(<StorageDrawers:fullCustom2> * 2, [[<ore:stickWood>, <hbm:tile.mass_storage:3>, <ore:stickWood>], [<ore:stickWood>, <ore:stickWood>, <ore:stickWood>], [<ore:stickWood>, <hbm:tile.mass_storage:3>, <ore:stickWood>]]);
recipes.addShaped(<StorageDrawers:fullCustom4> * 4, [[<hbm:tile.mass_storage:3>, <ore:stickWood>, <hbm:tile.mass_storage:3>], [<ore:stickWood>, <ore:stickWood>, <ore:stickWood>], [<hbm:tile.mass_storage:3>, <ore:stickWood>, <hbm:tile.mass_storage:3>]]);
recipes.addShaped(<StorageDrawers:halfCustom2> * 2, [[<ore:stickWood>, <hbm:tile.mass_storage:3>, <ore:stickWood>], [<ore:slabWood>, <ore:slabWood>, <ore:slabWood>], [<ore:stickWood>, <hbm:tile.mass_storage:3>, <ore:stickWood>]]);
recipes.addShaped(<StorageDrawers:halfCustom4> * 4, [[<hbm:tile.mass_storage:3>, <ore:stickWood>, <hbm:tile.mass_storage:3>], [<ore:slabWood>, <ore:slabWood>, <ore:slabWood>], [<hbm:tile.mass_storage:3>, <ore:stickWood>, <hbm:tile.mass_storage:3>]]);
