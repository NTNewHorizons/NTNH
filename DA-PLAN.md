So I found a solution! We need a workflow that

1. Checkouts selected branch of a repo (default is main)
2. Downloads packwiz via Go
    2.1. "go install github.com/packwiz/packwiz@latest"
3. Opens pack.toml file and changes "version" variable (number is set by a user) and then runs "packwiz refresh"
    3.1. Pushes the changes to the selected branch 'cause why not
4. Exports Native modpack (Native is just a client for pirated launchers) to ZIP
    4.1. It should remove .toml files from the Native client specificly, to optimize the weight
5. Then if there is a .toml file of a mod in /mods/ directory it should delete the .jar file of a mod
    5.1. For example: there are two files im /mods/ - agricraft.pw.toml and AgriCraft-1.7.10-1.5.0.jar. It should delete .jar file
6. Then it runs build commands for CurseForge and/or (selected by user) Modrinth.
    6.1. For CF it's "packwiz cf export"
    6.2. For MR it's "packwiz mr export"
7. Then it should rename the zips. Native is NTNH-X.X.X-Native.zip, CF is NTNH-X.X.X-CurseForge.zip and MR is NTNH-X.X.X-Modrinth.zip
    7.1. Where X.X.X is the version selected by user
8. And then it publishes a release named X.X.X with a tag X.X.X, and attaching files Mary-TTS.zip, NTNH-X.X.X-Native.zip, NTNH-X.X.X-CurseForge.zip and NTNH-X.X.X-Modrinth.zip to it. And also it reads the CHANGELOG.md file and sets its content as a description of a release.

(Maybe)
9. Sends a Discord announcement via Webhook.