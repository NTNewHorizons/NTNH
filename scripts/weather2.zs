// Weather, Storms & Tornadoes (Weather2) Integration for NTNH
// Execution order: Removals first, additions second

// --- Recipe Removals ---
recipes.remove(<weather2:WindVane>);
recipes.remove(<weather2:Anemometer>);
recipes.remove(<weather2:TornadoSensor>);
recipes.remove(<weather2:TornadoSiren>);
recipes.remove(<weather2:WeatherForecast>);
recipes.remove(<weather2:WeatherDeflector>);
recipes.remove(<weather2:WeatherMachine>);

// --- Recipe Additions ---

// 1. Wind Vane (LV1 - Early meteorology)
recipes.addShaped(<weather2:WindVane>, [
    [null, <ore:plateIron>, <ore:plateIron>],
    [null, <minecraft:iron_bars>, null],
    [null, <ore:plateIron>, null]
]);

// 2. Anemometer (LV1 - Wind velocity cups: iron & redstone, NO steel)
recipes.addShaped(<weather2:Anemometer>, [
    [<ore:plateIron>, <minecraft:iron_bars>, <ore:plateIron>],
    [null, <minecraft:iron_bars>, null],
    [<ore:plateIron>, <ore:dustRedstone>, <ore:plateIron>]
]);

// 3. Tornado Sensor (LV2 - Atmospheric vortex barometer: steel & analog circuit)
recipes.addShaped(<weather2:TornadoSensor>, [
    [<ore:plateSteel>, <weather2:Anemometer>, <ore:plateSteel>],
    [<ore:dustRedstone>, <hbm:item.circuit:8>, <ore:dustRedstone>],
    [<ore:plateSteel>, <ore:dustRedstone>, <ore:plateSteel>]
]);

// 4. Tornado Siren (LV2 - Acoustic air-raid warning horn: steel & analog circuit)
recipes.addShaped(<weather2:TornadoSiren>, [
    [<ore:plateSteel>, <minecraft:noteblock>, <ore:plateSteel>],
    [<ore:plateSteel>, <hbm:item.circuit:8>, <ore:plateSteel>],
    [<ore:plateSteel>, <ore:dustRedstone>, <ore:plateSteel>]
]);

// 5. Weather Forecast (LV3 - Radar weather station: titanium & advanced circuit)
recipes.addShaped(<weather2:WeatherForecast>, [
    [<ore:plateTitanium>, <weather2:WindVane>, <ore:plateTitanium>],
    [<ore:paneGlassColorless>, <hbm:item.circuit:10>, <weather2:Anemometer>],
    [<ore:plateTitanium>, <ore:plateTitanium>, <ore:plateTitanium>]
]);

// 6. Weather Deflector (LV4 - Endgame High-Tech Forcefield Generator: combine steel, tesla coil, QPU circuit, magnetized tungsten, bismuth & magnetron)
recipes.addShaped(<weather2:WeatherDeflector>, [
    [<hbm:item.plate_combine_steel>, <hbm:tile.tesla>, <hbm:item.plate_combine_steel>],
    [<hbm:item.coil_magnetized_tungsten>, <hbm:item.circuit:18>, <hbm:item.coil_magnetized_tungsten>],
    [<hbm:item.plate_bismuth>, <hbm:item.magnetron>, <hbm:item.plate_bismuth>]
]);

// 7. Weather Machine (Creative / Admin Only - Gated to prevent multiplayer base griefing)
// Crafting recipe disabled in survival: prevents players from maliciously summoning F5 tornadoes over neighboring bases.
