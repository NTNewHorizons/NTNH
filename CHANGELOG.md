# 2.14.0 Release

## What's changed?
**Added**
* Eggman's announcement
* Curvy Pipes
* Full Forge Fluids <-> NTM Fluids compat
    * NTM fluids can be imported and exported into/out of ME system
    * All the mods that use Forge Fluids see and use NTM fluids
    * Fluid tanks are recognized as fluid storage
        * Just like the water bucket
    * Use universal fluid identifier or a fluid container to set the ID
    * Pippetes also work!
    * Containers can be refilled/emptiet too
* Salt ore generation in Roofed Forests
    * Salt is no longer restricted to HarvestCraft's own generator, which is disabled
    * Now placed by CustomOreGen as a rare cloud deposit, roughly Y 16-48



**Fixed**
* Fixed a crash caused by upgrading the second half of a double Re:Chest
* Fixed squashed GUIs
* Fixed a crash caused by placing down Et Futurum shulker boxes
* Fixed nether quartz and lapis generation
* Fixed missing measure tape recipe
* Fixed weird-ass hopper crafting recipes
* Fixed an exploit that allowed cheaters to grab items out of nowhere using Mass Storage Units
* Fixed tileentity wobbling caused by nausea



**Changed**
* Removed Lumy Skin Patch due to incompatibility w/ Et Futurum Requiem
* Changed the behavior of the siphon
    * Previously only worked when completely filling a single empty container, failing silently otherwise
    * Now tops up compatible partially filled barrels first
    * Then selects the largest compatible empty barrel and fills it completely or partially
    * Continues through additional containers until the source is depleted
    * Respects barrel restrictions, fluid type, pressure, and exact remaining amounts
    * Safely separates stacked empty barrels when filling one
    * Preserves existing registered-container and pipette behavior
* Reverted Gergification until proper implementation
    * Will probably come with the Stone Age update and/or Factorization update
* Reworked Geological Surveyor
    * 3 tiers available - Iron, Steel, Desh
    * Different tiers have different accuracy
    * The ores show on Journey/Xaero map, instead of creating an unreadable map item
    * Ore overlay can be toggled
    * Ore selection included
* Added "High" effect caused by "spongeboy me bob i am overdosing on ketamine agagagaga"
* Changed the CustomOreGen frequency and size slider range from 0-5 to 0.25-2.5
    * Ore generation is now noticeably less prone to being cranked up past balance
    * The lower bound no longer reaches 0, so an individual ore can no longer be switched off via the slider
    * Global worldgen can still be disabled entirely with the Populate toggle



[Full Changelog](https://github.com/Nuclear-Tech-New-Horizons/NTNH/compare/2.13.0...2.14.0)

## Download
[GitGub](https://github.com/Nuclear-Tech-New-Horizons/NTNH/releases/tag/2.14.0)
[CurseForge](https://www.curseforge.com/minecraft/modpacks/ntnewhorizons)
[Website](https://ntnewhorizons.com/download)
[Technic](https://www.technicpack.net/modpack/nuclear-tech-new-horizons)
