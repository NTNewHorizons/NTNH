# 2.16.0 Release

## What's changed?
**Added**
* **350+ new quests**
* Hilariously impractical multiple rocket anti air launcher
	* Nine-barrel rocket launcher that uses micro rockets
* Modern parts holograms for the Immersive Vehicles: Legacy
* **FULLY WORKING RECIPES FOR IVL**
* Wdmla support for IVL
* NTNH OST - Disc 2
* Day/night cycle length configurability
	* The vanilla is set to 20 minutes
	* In the modpack specifically I've set it to 40 minutes
	* Can be configured using `/ntmtime` command (per-world) or in hbm.cfg (global)
* Proper Pam's Harvestcraft integration with NTM machines
	* Shredder: grains, rice, corn and potatoes into flour, fruits and beets into sugar, plus all former Quern recipes
	* Centrifuge: all former Presser recipes (juices, olive oil, honey, beeswax, tofu, soy milk)
	* Press: waxcomb into beeswax, soybeans into silken tofu
	* Liquefaction: Pam's raw fish into fish oil, fruits into ethanol, almonds into milk
	* Nuts and oily seeds can be turned into seed oil in the Liquefaction Machine and the Mixer, nuts yield more



**Changed**
* Updated russian and chinese localization
* Halved graphite in the chicago pile brick recipe
* Upon erroring with error ignore enabled, AUTOCALs now save the error name to a variable called last_error
* Updated various electric port textures
* Changed the custom material/autogen shape config structure
	* There can now be multiple molds per shape, allowing single and multi molds (like with 1x and 9x ingots)
	* Examples are now "custom metal demo" and "custom shape demo" instead of "bronze" and "gear" to avoid confusion
* Some common materials like tungsten, copper, aluminium and boron now have nugget forms
	* This allows foundry setups to use up materials with nugget precision
* Removed saltPeter ore dictionary entry from Pam's Harvestrcraft salt
* Made ProspectingCraft Geological Surveyor able to detect Pam's Harvestcraft salt
* Added Pam's Harvestcraft salt crafting recipe in the Chemical Plant
* Lunar Armor helmet can now be used as a part of the spacesuit
* Returned the Lunar Armor set eyepatch crafting recipe
	* Fuck, why did I even remove it in the first place?
* Biomass recipes now accept any fruit, vegetable or grain from Pam's Harvestcraft
* Renamed Sunflower Seed Oil to Seed Oil



**Fixed**
* Fixed crashes caused by the QMAW browser
* Fixed a game breaking bug where one of the oil QMAW entries has a word misseplled
* Fixed pumpjack's ports using the wrong metadata value for the rotations, causing ports to be concentrated on the core block on relog
* Fixed mobs still being able to spawn with legacy alloy gear
* Conk oredict missing on some recipes
* Fixed HTR-3 fluid port facing the wrong way
* Fixed drive processor ports, now only connect on the correct sides instead of all around
* Fixed xenon thruster fluid ports facing the wrong way
* Fixed magma drill fluid ports facing the wrong way
* Fixed orbital station and orbital station launcher north/south ports being swapped
* Fixed 256k tanks crasjh with OpenComputers installed
* Fixed jittering of detached stirling engine cogs that have gotten stuck in blocks
* Fixed ports for the lambda launchpad and the fluid burner
* Fixed steel barrels being able to hold highly corrosive liquids
* Fixed catalytic reformer not receiving power from cables
* Fixed missing ilmenite material localization
* Fixed recipe collision in the rotary furnace caused by a missing fluid type check, affecting the sodium aluminate and titanium tetrachloride recipes
* Fixed port offset on assembled machines
* Fixed exploits and issues related to the NTM Fluids <-> Forge Fluids system
* Fixed a bug where wedging yourself into an open IV/MTS car door on the driver or passenger side would instantly kill you
* Fixed a bug in which if you disassemble a working PWR using a Gravity Gun it still works
* Fixed the Thresher not replanting Pam's Harvestcraft crops



[Full Changelog](https://github.com/NTNewHorizons/NTNH/compare/2.15.3...2.16.0)

## Download
[GitHub](https://github.com/NTNewHorizons/NTNH/releases/tag/2.16.0)
[CurseForge](https://www.curseforge.com/minecraft/modpacks/ntnewhorizons)
[Website](https://ntnewhorizons.com/download)
[Technic](https://www.technicpack.net/modpack/nuclear-tech-new-horizons)
