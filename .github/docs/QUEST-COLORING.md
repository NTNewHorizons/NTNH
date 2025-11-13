# 🎯 **NTNH Quest Color System v1.0**  
*Simple. Consistent. Powerful.*

A standardized color-coding system for quest text in modpacks. Designed for clarity, readability, and scalability — works with any quest mod (QuestBook, KubeJS, CustomNPCs, etc.).

---

## 🎨 Color Code Reference

| Color | Code | Purpose | When to Use | Example | Rationale |
|-------|------|---------|-------------|---------|-----------|
| **White** | `§f` | **Base Text / Context** | All neutral, grammatical, or connecting text. Forms the background of the sentence. Never use for emphasis. | *“is a solid fuel-powered furnace used to...”* | White provides visual breathing room. It prevents cognitive overload. Players read it naturally, like normal prose. |
| **Cyan** | `§b` | **Objects / Entities / Systems** | Names of blocks, items, mods, mechanics, or systems the player must recognize or remember. | *Blast Furnace*, *Advanced Alloy*, *NTNH Discord* | These are the “nouns” of your progression. Players need to identify and recall them. Cyan is calm, technical, and stands out without shouting. |
| **Green** | `§a` | **Actions / Goals / Benefits** | Verbs and outcomes — what the player *does*, *gains*, or *achieves*. The core purpose of the quest. | *create*, *construct*, *unlock*, *gain vein mining* | Green = progress. It triggers a subconscious “win” response. This is the *why* of the quest. Always highlight the *result* or *action*. |
| **Yellow** | `§e` | **Critical Hints / Hidden Mechanics / Commands** | Non-obvious instructions, keybinds, commands, future implications, or hidden features. | *pressing the ALT key*, *"/igi config"*, *“later on”* | Yellow is the game’s whisper. It says: *“This matters — but you might miss it.”* Use it for anything that could break progression if ignored. |

---

## 🧩 Example: Applying the System — *Blast Furnace Quest*

### 📜 Original Text (Uncolored):
> The Blast Furnace is a solid fuel-powered furnace used to create different alloys out of two inputs. The main starter alloys are: Minecraft Grade Copper, Steel and Advanced Alloy, which is a combination of the former.  
>   
> Advanced alloy can be used to construct very durable tools that come with vein mining. For more advanced tools later on, pressing the ALT key will bring up a menu from which one can select multiple abilities at once.

### ✅ Colored Version (Using NTNH System):

```text
§fThe §bBlast Furnace§f is a solid fuel-powered furnace used to §acreate§f different §balloys§f out of two inputs. The main starter §balloys§f are: §bMinecraft Grade Copper§f, §bSteel§f and §bAdvanced Alloy§f, which is a combination of the former.

§aAdvanced Alloy§f can be used to §aconstruct§f very durable tools that come with §avein mining§f. §eFor more advanced tools later on§f, §epressing the ALT key§f will bring up a menu from which one can §aselect multiple abilities at once§f.
```

### 🔍 Rendered Visual (How It Appears In-Game):

> The **Blast Furnace** is a solid fuel-powered furnace used to **create** different **alloys** out of two inputs. The main starter **alloys** are: **Minecraft Grade Copper**, **Steel** and **Advanced Alloy**, which is a combination of the former.  
>   
> **Advanced Alloy** can be used to **construct** very durable tools that come with **vein mining**. *For more advanced tools later on*, *pressing the ALT key* will bring up a menu from which one can **select multiple abilities at once**.

---

### 🧠 Breakdown by Color (Why It Works)

| Color | Highlighted Text | Role | Why It Matters |
|-------|------------------|------|----------------|
| **Cyan (`§b`)** | `Blast Furnace`, `alloys`, `Minecraft Grade Copper`, `Steel`, `Advanced Alloy` | **Objects** | These are the *core components* of the mechanic. The player must recognize and remember these names to progress. Cyan makes them stand out as “things to know.” |
| **Green (`§a`)** | `create`, `construct`, `vein mining`, `select multiple abilities` | **Actions & Benefits** | These are the *goals*. The player doesn’t care about the furnace — they care about *what they can build*. Green tells them: *“This is your reward.”* |
| **Yellow (`§e`)** | `For more advanced tools later on`, `pressing the ALT key` | **Hidden Mechanics** | The ALT key is *not obvious*. “Later on” hints at future depth. These are *critical tips* that prevent frustration. Yellow says: *“Pay attention — this unlocks something bigger.”* |
| **White (`§f`)** | Everything else | **Context** | The rest is scaffolding. It holds the meaning together without distracting. White keeps the text readable and natural. |