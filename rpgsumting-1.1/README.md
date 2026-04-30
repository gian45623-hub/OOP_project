# RPGSumting

A Java Swing starter project for a choice-driven tabletop-fantasy RPG where every job joins the same Emberdeep campaign, and the player's decisions change the path, stats, and ending.

## Run In Eclipse

1. Open Eclipse.
2. Choose `File > Import > Existing Projects into Workspace`.
3. Select this folder: `C:\Users\gian4\OneDrive\Documents\antigravity\rpgsumting`.
4. Click `Finish`.
5. Open `src/rpgsumting/Main.java`.
6. Click `Run`.

No external libraries are needed.

## What Is Included

- Job selection: Mage, Knight, Rogue, Thief, Archer.
- One consistent job-specific prologue for every class, all leading into the same main quest.
- Branching story choices with different outcomes across one connected dungeon campaign.
- A D&D-style structure with a tavern contract, village clues, party travel, shrine lore, dungeon rooms, traps, a dragon oath, and a final ritual.
- Final-act chapters for every ending path, giving each playthrough a longer climax with fixed ending text.
- Stats that change based on choices.
- Procedural UI backgrounds for forests, villages, roads, inns, graveyards, rivers, castles, caves, towers, markets, academies, battlefields, temples, swamps, docks, mountains, ruins, and endings.
- Procedural character sprites for each job.
- Built-in MIDI background music toggle.
- Restart flow for replaying different choices.

## Where To Customize

- Add or edit jobs in `src/rpgsumting/Job.java`.
- Add job-specific starts, campaign branches, and endings in `src/rpgsumting/StoryGraph.java`.
- Change sprites and backgrounds in `src/rpgsumting/ScenePanel.java`.
- Change music notes in `src/rpgsumting/SoundEngine.java`.
