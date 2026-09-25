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
    [null, <ore:stickSteel>, null],
    [null, <ore:plateIron>, null]
]);

// 2. Anemometer (LV1 - Wind velocity cups)
recipes.addShaped(<weather2:Anemometer>, [
    [<ore:plateIron>, <ore:stickSteel>, <ore:plateIron>],
    [null, <ore:stickSteel>, null],
    [<ore:plateSteel>, <ore:dustRedstone>, <ore:plateSteel>]
]);

// 3. Tornado Sensor (LV2 - Atmospheric vortex barometer)
recipes.addShaped(<weather2:TornadoSensor>, [
    [<ore:plateSteel>, <weather2:Anemometer>, <ore:plateSteel>],
    [<ore:dustRedstone>, <hbm:item.circuit:3>, <ore:dustRedstone>],
    [<ore:plateSteel>, <ore:dustRedstone>, <ore:plateSteel>]
]);

// 4. Tornado Siren (LV2 - Acoustic air-raid warning horn)
recipes.addShaped(<weather2:TornadoSiren>, [
    [<ore:plateSteel>, <minecraft:noteblock>, <ore:plateSteel>],
    [<ore:plateSteel>, <hbm:item.circuit:3>, <ore:plateSteel>],
    [<ore:plateSteel>, <ore:dustRedstone>, <ore:plateSteel>]
]);

// 5. Weather Forecast (LV3 - Radar weather station)
recipes.addShaped(<weather2:WeatherForecast>, [
    [<ore:plateTitanium>, <weather2:WindVane>, <ore:plateTitanium>],
    [<ore:paneGlassColorless>, <hbm:item.circuit:4>, <weather2:Anemometer>],
    [<ore:plateTitanium>, <ore:plateTitanium>, <ore:plateTitanium>]
]);

// 6. Weather Deflector (LV3 - High-tech repulsive electromagnetic field generator)
recipes.addShaped(<weather2:WeatherDeflector>, [
    [<ore:plateTitanium>, <hbm:tile.tesla>, <ore:plateTitanium>],
    [<ore:plateTitanium>, <hbm:item.circuit:4>, <ore:plateTitanium>],
    [<ore:plateLead>, <ore:plateSteel>, <ore:plateLead>]
]);

// 7. Weather Machine (LV4 - End-game planetary weather manipulator)
recipes.addShaped(<weather2:WeatherMachine>, [
    [<ore:plateSchrabidium>, <weather2:WeatherDeflector>, <ore:plateSchrabidium>],
    [<weather2:WeatherForecast>, <hbm:item.circuit:9>, <weather2:WeatherForecast>],
    [<ore:plateSchrabidium>, <ore:plateTitanium>, <ore:plateSchrabidium>]
]);
