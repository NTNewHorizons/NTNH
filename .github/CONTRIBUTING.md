# NTNH Contribution Guidelines, Version 1.2
##### (srry [bob](https://github.com/HbmMods) for stealing your guidelines)


## Pull Request Requirements:

* All PRs must be reviewed by at least one member of the [@Admins](https://github.com/orgs/NTNewHorizons/teams/admins) team or [@Leads](https://github.com/orgs/NTNewHorizons/teams/leads) team
* Description must be detailed and clear, explain what you did and how is it going to affect the repository


## How to create a PR:

1. Create a branch from main
2. Make your changes
3. Create a PR to main branch
4. Wait for approval from @admin/@leads team member
5. Merge the changes after approval


## Keep it concise

The best PRs are the ones that are small and to the point. The entire PR should focus on the thing you're trying to do, whether it's a fix or a feature PR. If your PR adds the Super Weldtronic 9000, there's no reason to include changes and tweaks to other things that have nothing to do with the Super Weldtronic 9000. If you think those changes are still necessary, open a new PR.


## Keep it clean

Things you should avoid in you PRs include:
* duplicate functions (we don't need 100 mods telling "yeag, this is definetly a pig")
* unused or half finished features (for obvious reasons)
* half finished or obviously broken features (à la "they'll will fix it, i'm sure of it", please don't do that)
* updating the changelog (you're guaranteed to cause a merge conflict with that)

Things you should include in you PRs:
* write clear commit messages (optional, increases your PR's chances to be merged) (anyways commits like "femboy milk" or "shit" are ok)


## Test your things

This should go without saying, but please don't PR that was never actually tested or has obvious errors in it.

**Addendum:** Because apparently some people think that testing is somehow optional, it is now **mandatory** to test the PR both on a client and on a server. If the PR contains compat code for any of the mods, the game has to work **with and without** the mod that the compat is for.


## No refactor PRs (with exception)

Please consult the [@Admins](https://github.com/orgs/NTNewHorizons/teams/admins) or [@Leads](https://github.com/orgs/NTNewHorizons/teams/leads) team on GitHub or Dev team in Discord.


## Communication

If you're planning on adding some new thing or doing a grand change, it's best to ask whether that's a good idea before spending 50 hours on a project that won't end up getting merged, due to issues that could have been entirely avoidable with communication. On Discord server, we got [#developers-contributors](https://discord.com/channels/1365318764895277136/1541179745881030696), where you can discuss your edits with other developers


## No guarantees

This ties together with the previous point - there's no guarantees that your PR gets merged no matter how hard or long you've worked on it. However, if you follow these guidelines, there's a good chance that your PR will be accepted.


## I want to help but don't know where to start

If you want to help the project, consider getting involved with the [Crowdin project](https://crowdin.com/project/ntnh) first. Translating NTNH is the easiest and quickest way of helping, and requires no programming knowledge. If you do know Java/HTML/CSS/JavaScript/etc. and want to help, consider these places first:

* Contributing to upstreams ([NTM](https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT) and [NTM: Space](https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT)) or directly to the [NTNH NTM repo](https://github.com/NTNewHorizons/Hbm-s-Nuclear-Tech-GIT)
* Contributing to [our website repo](https://github.com/NTNewHorizons/NTNewHorizons.github.io)

If you want to suggest any ideas to NTNH, do it in [#suggestions](https://discord.com/channels/1365318764895277136/1416584800457723965) channel in our Discord (you can post anything but please no shitposts)
