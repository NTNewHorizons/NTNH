# 2.15.0 Release

## What's changed?
**Added**
* Made upright steel scaffold variants climbable while preserving horizontal scaffold placements as platforms
* New depletable "rich ore" system
  * Multi‑unit ore blocks that deplete to stone and drop "rich chunks"
  * Spawns in big patches
  * Can be found using Neutrino Lens or Survey Scanner
  * Used to automate mining in the midgame
  * Factorio reference
* Armors that fully protect from fire/nether heat now say so in the tooltip
* PanicleCraft
* Added new structures
  * Abandoned house with shelter (plains)
  * Military base (snowy biomes)
* Added the ability to hold backspace in the qb search bar
* Added colored AE2 interfaces textures
* Added fishing handler to NEI
* Added new barrel recipes and upgrades
* Backported modern wither spawning behavior
* Water splash effects and waves
* Сustom fonts support on MacOS
* Hex Text and other Font Mod Compat System
* Make ME Controllers colorable
* Added command autocompletion/suggestions
* Region preview on world load
* Patpatpat
* Pet glyphids :3
  * The biggest feature of this update
* Gmod-style main menu
* NTNH Poem
* New structures
  * Camp (2 variants)
  * Airplane
* Full translate-ability of NTM (I18n)
  * Can be contributed to at [Crowdin](https://crowdin.com/project/ntnh)
  * More mods planned



**Fixed**
* Fixed rotary furnace consuming the fuel WITH the bucket (#204)
* Fixed a bug that caused flux scraps being unable to be put back into the crucible (#117)
* Fixed a bug that caused the quests to break an not complete when hosting a LAN game (#199)
* Fixed kitchen door hinges ending up on the wrong side
* Fixed baby Enderman item duplication
* Fixed world progress loss from backups in singleplayer
* Fixed hungry mob crash when eating items from lunchbags that dont extend ItemFood
* Fixed protected drawer extraction
* Fixed wireless level terminal bauble navigation
* Fixed single-fluid cell contents tooltip
* Fixed a **long-standing** issue with shadow distortion / self-shadowing (dark spots) on large machines
* Fixed issues with Schematica
* DH: Fix shaderpack fading
* Fixed cloud elevation setting
* Fixed microblock transparency in the hotbar
* Fixed borking modded mobs with broken eyes
* Fixed chunk NBT corruption during concurrent saves
* Fixed duplicate players on reconnect
* Various major and minor bug fixes
* Fixed Annihilation Plane crash when loading Forge Multipart cable buses



**Changed**
* Reworked ore generation making it MUCH MORE STABLE (as far as we tested it)
* Now bedrock ore has oredict so you can sort it with ae2
* No more cosmetic burning (having armor that fully protects from fire will extingish you)
* Conveyors no longer explode with 25+ items and instead just stop moving the items until the path ahead is clear
* Conveyor related blocks no longer spew items when full
* Conveyor inserters no longer defaults to destroying items
* Compat between backhand and ntm (chargers and refueler now affect offhand)
* Compat between baubles and ntm (chargers and refueler now affect baubles and some ntm items can be put in baubles)
* Compat between ae2 and ntm (chargers can now directly charge ae2 items)
* Optimized background images in the loading screen and main menu, saving WHOLE 7MB
* Removed Toggle Projection and Toggle Entity Interaction keybinds
* Updated russian, japanese and ukrainian localizations
* Hungry mob variant behaviour change/fix
* Updateed trophy pedestal model and texture
* Optimize resource reload
* More Cloud Optimizations
* Reduce resource pack manifest memory usage
* Reduce memory allocation pressure
* Reduced the amount of food found in dungeons
* Electric Press remodel and retexture
* Updated NEI info pages on ore generation
* Removed thief mobs



[Full Changelog](https://github.com/Nuclear-Tech-New-Horizons/NTNH/compare/2.14.0...2.15.0)

## Download
[GitGub](https://github.com/Nuclear-Tech-New-Horizons/NTNH/releases/tag/2.15.0)
[CurseForge](https://www.curseforge.com/minecraft/modpacks/ntnewhorizons)
[Website](https://ntnewhorizons.com/download)
[Technic](https://www.technicpack.net/modpack/nuclear-tech-new-horizons)
