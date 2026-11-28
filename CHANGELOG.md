# 2.16.0 Release

## What's changed?

**Added**

* 500+ new quests
* Hilariously impractical multiple rocket anti air launcher
  * Nine-barrel rocket launcher that uses micro rockets
* Modern parts holograms for the Immersive Vehicles: Legacy
* Fully implemented recipes for IVL
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
* Moxer
  * A new multiblock machine that mixes isotope slurries into fully custom nuclear fuels, using nitric acid, ammonia, and krypton to produce custom fuel ingots and billets.
  * The GUI lets you select which isotope to buffer, shows total mB and per-cycle consumption, has an auto-mode toggle, and displays a molten-colour buffer bar. Buffered isotopes drop as scraps when the machine is broken.
  * Comes with an assembly recipe and NEI handler.
* Custom fuels
  * Ingots, billets, RBMK rods and pellets, PWR fuel, Zirnox fuel, and Watz fuel, including hot and depleted forms.
  * Fuels are tinted from their isotope mix, with spent forms darkened. Tooltips show composition, fissile share, the active fuel model, and reactor-specific stats.
  * Creative-tab presets include LEU, HEU, MOX, Th-233, a Pu/Np blend, and a schrabidium-doped mix.
* Three fuel models decide how a mix behaves in a reactor:
  * **Weighted sum:** each fissile isotope uses its own curve scaled by its share.
  * **Derived stats:** the mix becomes an equivalent classic fuel based on enrichment tier, strength, and heat.
  * **Hybrid:** native curves plus a neutron economy — fertile isotopes absorb and breed, schrabidium catalyses, diverse mixes run cooler, and thorium suppresses xenon and sludge.
  * Profiles are provided for RBMK, PWR, Zirnox, and Watz.
* Spent custom fuel
  * Burn-up shifts the mix: fissile isotopes fission away and fertile isotopes breed into fissile ones.
  * Spent fuel can be configured to recycle back into the Moxer, go through normal reprocessing, or both.
  * Reprocessing can produce the closest vanilla spent item, use PUREX recipes that recover isotopes matching the actual composition, or both.
  * Custom RBMK rods can be broken back into pellets with the same depletion-stage meta as vanilla pellets.
* Custom fuel crafting and NEI
  * Crafting blends mixes across inputs:
    * 3 billets → 2 ingots, 2 ingots → 3 billets
    * billets + empty RBMK rod → custom RBMK rod
    * billets + empty Zirnox rod → custom Zirnox rod
    * billet + polymer plates → PWR fuel
    * ingot + graphite → Watz fuel
  * Supports shaped and shapeless matching, ore dictionary entries, rod breaking, and spent-fuel conversion to vanilla items.
  * A dedicated NEI handler builds recipes for the exact mix you look up.
* Hazards
  * Radiation and blinding now come from the actual custom fuel mix, scaled by isotope content and fission products.
* Materials
  * Added Cm-242, Cm-244, Cm-245, Cm-246, Cm-247, Bk-247, Cf-251, Cf-252, and Es-253 as smeltable materials.

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
* Added three machine config options for custom fuel:
  * `9.02_customFuelModel` picks the fuel model (default: hybrid)
  * `9.03_customFuelSpentPath` picks whether spent fuel recycles, reprocesses, or both (default: both)
  * `9.04_customFuelReprocessing` picks whether reprocessing uses the closest vanilla item, PUREX, or both (default: both)
* The Zirnox now accepts custom Zirnox fuel when shift-transferring
* The PWR now correctly displays custom PWR fuel in its GUI
* Removed Weather 2 (tornadoes and shi) mod until better implementation

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
