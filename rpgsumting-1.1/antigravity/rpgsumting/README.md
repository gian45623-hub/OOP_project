# RPGSumting

A Java Swing starter project for a choice-driven RPG where the player's job and decisions change the story path, stats, and ending.

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
- Three random opening scenarios for every job when creating a new character.
- Branching story choices with different outcomes.
- Timed story choices that randomly pick for the player when time runs out.
- Stats that change based on choices.
- Procedural UI backgrounds for different locations.
- Procedural character sprites for each job.
- Built-in MIDI background music toggle.
- Restart flow for replaying different choices.

## Where To Customize

- Add or edit jobs in `src/rpgsumting/Job.java`.
- Add job-specific starts, story branches, and endings in `src/rpgsumting/StoryGraph.java`.
- Change sprites and backgrounds in `src/rpgsumting/ScenePanel.java`.
- Change music notes in `src/rpgsumting/SoundEngine.java`.
