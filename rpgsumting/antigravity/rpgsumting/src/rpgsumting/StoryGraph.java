package rpgsumting;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class StoryGraph {
    public static String[] getStartNodeIds(Job job) {
        if (job == Job.MAGE) {
            return new String[] { "mage_start", "mage_observatory_start", "mage_grimoire_start" };
        }
        if (job == Job.KNIGHT) {
            return new String[] { "knight_start", "knight_tournament_start", "knight_watch_start" };
        }
        if (job == Job.ROGUE) {
            return new String[] { "rogue_start", "rogue_prison_start", "rogue_ball_start" };
        }
        if (job == Job.THIEF) {
            return new String[] { "thief_start", "thief_vault_start", "thief_rooftop_start" };
        }
        if (job == Job.ARCHER) {
            return new String[] { "archer_start", "archer_beast_start", "archer_border_start" };
        }

        return new String[] { "crossroads" };
    }

    public static Map<String, StoryNode> create() {
        Map<String, StoryNode> nodes = new LinkedHashMap<String, StoryNode>();

        add(nodes, new StoryNode(
                "mage_start",
                "Mage Scenario: The Rift Exam",
                "Your final exam begins at midnight inside the broken wizard tower. A blue rift opens above the spell circle, and your teacher vanishes into it before finishing the warning.",
                SceneType.TOWER,
                false,
                Arrays.asList(
                        choice("Seal the rift with a forbidden spell",
                                "The rift obeys, but the spell burns a silver mark into your hand.",
                                "cave_omen", -8, -25, 1, 1, 3),
                        choice("Study the old runes before acting",
                                "The runes reveal that the rift is tied to a cursed crown.",
                                "hidden_library", 0, -10, 0, 3, 2),
                        choice("Step into the rift after your teacher",
                                "The rift throws you into a cave where names are carved before people die.",
                                "cave_omen", -15, 15, 2, 0, 1))));

        add(nodes, new StoryNode(
                "knight_start",
                "Knight Scenario: The Burning Gate",
                "You are sworn to Castle Ashwake when the enemy breaks the outer gate. Civilians run behind you, the captain is wounded, and the war horn snaps in half.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Hold the gate alone",
                                "Your shield becomes the line between panic and survival.",
                                "captain_oath", -18, 0, 4, 0, 2),
                        choice("Challenge the raider chief",
                                "The raiders pause when you call their leader by name.",
                                "castle_siege", -10, 0, 3, 1, 0),
                        choice("Break formation to evacuate civilians",
                                "You save lives, but some soldiers call it desertion.",
                                "prisoner_secret", -7, 0, 1, 2, 1))));

        add(nodes, new StoryNode(
                "rogue_start",
                "Rogue Scenario: The Masked Contract",
                "A guild contact meets you above the city rooftops with three sealed contracts. One names a royal spy, one names a traitor captain, and one names you.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Steal the royal spy's ledger",
                                "The ledger points to a prisoner who knows why the kingdom is being hunted.",
                                "prisoner_secret", -4, -5, 0, 4, 2),
                        choice("Frame the traitor captain",
                                "The guild pays well, but the lie will echo through the castle.",
                                "royal_letter", 4, 0, 0, 3, -2),
                        choice("Open the contract with your name",
                                "The page is blank until your blood touches it.",
                                "mirror_room", -10, -10, 1, 3, 1))));

        add(nodes, new StoryNode(
                "thief_start",
                "Thief Scenario: The Crown Job",
                "You sneak through a moonlit market after hearing that a merchant caravan carries a map to a buried crown. Your crew wants gold. The city guard wants your head.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Pickpocket the map from the caravan master",
                                "Your fingers find parchment, wax, and a warning written in royal ink.",
                                "buried_crown", -3, 0, 0, 4, 1),
                        choice("Free a captured street kid first",
                                "The kid knows a tunnel under the old tower.",
                                "hidden_library", -8, 0, 1, 3, 2),
                        choice("Double-cross your crew and keep the loot",
                                "You escape richer, but your name becomes a bounty.",
                                "ending_shadow", 12, 0, 0, 5, -4))));

        add(nodes, new StoryNode(
                "archer_start",
                "Archer Scenario: The Last Watch",
                "From the forest watchtower, you see smoke rising from Castle Ashwake and unnatural wolves circling the refugee road. You have three arrows ready before the night notices you.",
                SceneType.FOREST,
                false,
                Arrays.asList(
                        choice("Shoot the lead shadow wolf",
                                "The pack turns toward you instead of the refugees.",
                                "wolf_ambush", -6, 0, 3, 1, 1),
                        choice("Guide the refugees through the old pines",
                                "The trees answer your quiet signals and reveal a silver-eyed spirit.",
                                "forest_spirit", -4, -5, 1, 2, 2),
                        choice("Track the raiders leaving the castle",
                                "Their wagon drops a letter sealed in black wax.",
                                "royal_letter", -5, 0, 2, 2, 0))));

        add(nodes, new StoryNode(
                "mage_observatory_start",
                "Mage Scenario: Stars Out Of Place",
                "You wake inside the academy observatory while every star in the sky forms your name. The brass telescope points by itself toward the forest, then cracks down the middle.",
                SceneType.TOWER,
                false,
                Arrays.asList(
                        choice("Read the star pattern as a prophecy",
                                "The prophecy names a cave where fate can be edited.",
                                "cave_omen", -4, -18, 0, 2, 2),
                        choice("Follow the telescope's aim into the forest",
                                "The broken lens still glows when the pines begin whispering.",
                                "forest_spirit", -3, -8, 1, 1, 1),
                        choice("Report the omen to the royal court",
                                "The court panics when your warning matches a sealed royal letter.",
                                "royal_letter", 0, -10, 1, 2, 0))));

        add(nodes, new StoryNode(
                "mage_grimoire_start",
                "Mage Scenario: The Book That Breathes",
                "A locked grimoire in the library starts breathing under your desk. When you open it, the pages show three possible deaths and one empty page waiting for ink.",
                SceneType.TOWER,
                false,
                Arrays.asList(
                        choice("Write your name on the empty page",
                                "The book accepts you as part of its unfinished spell.",
                                "mirror_room", -6, -16, 1, 2, 1),
                        choice("Burn the death pages",
                                "The smoke forms a map to the roots hiding the cursed crown.",
                                "buried_crown", -5, -20, 2, 1, 3),
                        choice("Hide the book and pretend nothing happened",
                                "The book waits. Patient curses are usually the worst kind.",
                                "ending_bound", 0, 5, 0, 1, -3))));

        add(nodes, new StoryNode(
                "knight_tournament_start",
                "Knight Scenario: The False Tournament",
                "The king hosts a tournament to choose a new champion, but every opponent wears the same black wax seal. The crowd cheers before the first sword is drawn.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Win honorably in front of the crowd",
                                "Your victory forces the king to acknowledge you.",
                                "captain_oath", -8, 0, 3, 0, 2),
                        choice("Expose the sealed symbol",
                                "The symbol matches orders found in a hidden royal letter.",
                                "royal_letter", -4, 0, 1, 2, 2),
                        choice("Lose on purpose to follow the champion",
                                "The fake champion leads you toward a prisoner wagon.",
                                "prisoner_secret", -2, 0, 0, 3, 1))));

        add(nodes, new StoryNode(
                "knight_watch_start",
                "Knight Scenario: The Haunted Watch",
                "Your patrol reaches an abandoned watchtower where old shields hang from the rafters. Each shield carries the name of a knight who died protecting a secret.",
                SceneType.RUINS,
                false,
                Arrays.asList(
                        choice("Swear an oath to the dead knights",
                                "Their shields rise and point toward Castle Ashwake.",
                                "castle_siege", 0, -5, 3, 0, 2),
                        choice("Search the tower records",
                                "The records say the crown was buried to prevent another civil war.",
                                "buried_crown", 0, 0, 0, 2, 1),
                        choice("Take the strongest shield",
                                "The shield protects your body but whispers orders at night.",
                                "ending_bound", 12, 0, 1, 0, -3))));

        add(nodes, new StoryNode(
                "rogue_prison_start",
                "Rogue Scenario: The Cell With No Lock",
                "You wake in a royal prison cell that has no lock, no guard, and no memory of how you arrived. A prisoner across the hall already knows your real name.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Free the prisoner who knows your name",
                                "She claims she used to read fate for the royal family.",
                                "prisoner_secret", -3, -5, 0, 4, 2),
                        choice("Escape through the guard records room",
                                "The records include a letter proving the siege was planned.",
                                "royal_letter", -5, 0, 0, 3, 1),
                        choice("Stay and set a trap for the warden",
                                "The warden arrives carrying a mirror that shows the wrong version of you.",
                                "mirror_room", -8, -5, 1, 3, 0))));

        add(nodes, new StoryNode(
                "rogue_ball_start",
                "Rogue Scenario: The Glass Mask Ball",
                "At a noble ball, every guest wears a glass mask. Your job is simple: steal one dance, one key, and one secret before midnight.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Steal the royal key",
                                "The key opens a tower library that should not exist.",
                                "hidden_library", 0, 0, 0, 4, 1),
                        choice("Dance with the masked seer",
                                "She tells you the crown is choosing its next owner.",
                                "buried_crown", -2, -5, 0, 3, 2),
                        choice("Sell the guest list to the highest bidder",
                                "By dawn, every noble owes you a favor or a threat.",
                                "ending_shadow", 8, 0, 0, 5, -3))));

        add(nodes, new StoryNode(
                "thief_vault_start",
                "Thief Scenario: The Breathing Vault",
                "You slip into a noble vault, but the walls pulse like lungs and the gold stacks arrange themselves into arrows. Something in the room wants to be stolen.",
                SceneType.CAVE,
                false,
                Arrays.asList(
                        choice("Steal the crown-shaped relic",
                                "The relic is only a copy, but it knows where the real crown sleeps.",
                                "buried_crown", -4, 0, 0, 4, 1),
                        choice("Crack the vault's hidden door",
                                "The door leads into the old tower's buried library.",
                                "hidden_library", -3, 0, 0, 3, 2),
                        choice("Take every coin you can carry",
                                "The vault lets you leave because greed is easy to track.",
                                "ending_shadow", 15, 0, 0, 4, -4))));

        add(nodes, new StoryNode(
                "thief_rooftop_start",
                "Thief Scenario: Chase Over Red Tiles",
                "The city guard chases you across rain-slick rooftops after a job goes wrong. Below, raiders enter the city gate disguised as merchants.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Lead the guards into the raiders",
                                "Your enemies collide loudly enough to reveal the raider plot.",
                                "castle_siege", -8, 0, 1, 3, 1),
                        choice("Drop into the old aqueduct",
                                "The tunnel carries you beneath the broken wizard tower.",
                                "hidden_library", -5, 0, 0, 4, 1),
                        choice("Escape alone with the stolen purse",
                                "You survive, but a child at the gate does not.",
                                "ending_shadow", 10, 0, 0, 3, -4))));

        add(nodes, new StoryNode(
                "archer_beast_start",
                "Archer Scenario: Hunt Of The Moonbeast",
                "Your village sends you after a moonbeast that has been taking livestock without leaving tracks. In the mud, you find boot prints instead of claws.",
                SceneType.FOREST,
                false,
                Arrays.asList(
                        choice("Track the boot prints",
                                "The trail leads to raiders dragging treasure from Castle Ashwake.",
                                "royal_letter", -4, 0, 2, 2, 1),
                        choice("Bait the beast with silver arrows",
                                "The moonbeast is a forest spirit wearing anger as a skin.",
                                "forest_spirit", -7, -5, 2, 1, 2),
                        choice("Warn the village before hunting",
                                "Your caution saves lives and earns a captain's attention.",
                                "captain_oath", 0, 0, 2, 1, 1))));

        add(nodes, new StoryNode(
                "archer_border_start",
                "Archer Scenario: The Broken Border",
                "At the kingdom border, every warning bell rings at once. A messenger collapses beside you with an arrow in her back and a black-wax letter in her hand.",
                SceneType.RUINS,
                false,
                Arrays.asList(
                        choice("Carry the letter to the castle",
                                "The letter reveals the king planned the fear spreading through the realm.",
                                "royal_letter", -5, 0, 2, 1, 2),
                        choice("Follow the arrow's flight path",
                                "The shooter hides near a tower that remembers every betrayal.",
                                "tower_gate", -6, 0, 2, 2, 0),
                        choice("Save the messenger first",
                                "Before passing out, she whispers about a prisoner seer.",
                                "prisoner_secret", -3, -5, 1, 2, 1))));

        add(nodes, new StoryNode(
                "crossroads",
                "Moonfall Crossroads",
                "A red moon rises over the old road. Three paths wait: a haunted forest, a ruined tower, and the king's road to a castle under siege. Travelers whisper that tonight one choice can rewrite a life.",
                SceneType.FOREST,
                false,
                Arrays.asList(
                        choice("Follow the whispering forest trail",
                                "The trees bend aside, but the shadows steal warmth from your skin.",
                                "forest_spirit", -8, -4, 1, 1, 0),
                        choice("Climb toward the broken wizard tower",
                                "Old runes flare when you touch the gate.",
                                "tower_gate", 0, -8, 0, 2, 1),
                        choice("Take the king's road to the burning castle",
                                "You walk toward smoke, shouting, and the sound of steel.",
                                "castle_siege", -5, 0, 2, 0, 0))));

        add(nodes, new StoryNode(
                "forest_spirit",
                "The Spirit In The Pines",
                "A silver-eyed spirit blocks the path. It says the forest is hungry because people have forgotten their promises. It offers a bargain: power, truth, or safe passage.",
                SceneType.FOREST,
                false,
                Arrays.asList(
                        choice("Accept the spirit's power",
                                "Cold magic rushes through you and leaves a mark on your palm.",
                                "cave_omen", -5, 20, 0, 0, 2),
                        choice("Ask what promise was broken",
                                "The spirit shows you a stolen crown buried beneath the roots.",
                                "buried_crown", 0, -5, 1, 2, 1),
                        choice("Refuse and push deeper into the woods",
                                "Branches scrape your armor and your pride.",
                                "wolf_ambush", -15, 0, 2, 0, -1))));

        add(nodes, new StoryNode(
                "tower_gate",
                "The Broken Tower",
                "The tower door has no handle. Five stones are carved with job marks: staff, shield, dagger, coin, and bow. One stone glows when your shadow crosses it.",
                SceneType.TOWER,
                false,
                Arrays.asList(
                        choice("Touch the glowing stone",
                                "The tower recognizes your path and opens a stairway upward.",
                                "mirror_room", 0, -10, 1, 1, 1),
                        choice("Force the door open",
                                "The door breaks, but the tower punishes careless strength.",
                                "trap_hall", -18, -5, 2, 0, -1),
                        choice("Search the base for another entrance",
                                "You find a servant tunnel and avoid the first curse.",
                                "hidden_library", 0, 0, 0, 3, 1))));

        add(nodes, new StoryNode(
                "castle_siege",
                "Castle Ashwake",
                "The outer wall is broken. Civilians hide near the well while raiders drag treasure through the gate. The captain begs for help, but a prisoner calls your name from a wagon.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Defend the civilians",
                                "Your stand buys time for the helpless to escape.",
                                "captain_oath", -12, -5, 3, 0, 2),
                        choice("Free the prisoner",
                                "The prisoner knows why the raiders came: they hunt a cursed crown.",
                                "prisoner_secret", -6, -5, 0, 3, 1),
                        choice("Chase the treasure wagon",
                                "You recover gold and a sealed royal letter.",
                                "royal_letter", -8, 0, 1, 2, -1))));

        add(nodes, new StoryNode(
                "buried_crown",
                "Roots Around Gold",
                "The crown under the roots pulses like a sleeping heart. The spirit says it belonged to a ruler who traded the kingdom's future for victory.",
                SceneType.RUINS,
                false,
                Arrays.asList(
                        choice("Destroy the crown",
                                "The forest exhales. Something ancient is finally allowed to die.",
                                "ending_warden", -5, -20, 3, 0, 3),
                        choice("Wear the crown",
                                "The crown fits perfectly, as if it waited for you.",
                                "ending_tyrant", 15, -15, 1, 1, -4),
                        choice("Hide it and tell no one",
                                "Secrets are lighter than gold until they become chains.",
                                "ending_shadow", 0, 0, 0, 4, -2))));

        add(nodes, new StoryNode(
                "cave_omen",
                "The Cave Of Names",
                "The spirit's power leads you to a cave wall covered with names. Yours is being carved by an invisible hand, but the ending is still blank.",
                SceneType.CAVE,
                false,
                Arrays.asList(
                        choice("Carve your own ending",
                                "The cave accepts your will, but takes a piece of your life in payment.",
                                "ending_free", -18, -20, 3, 2, 3),
                        choice("Erase every name you can reach",
                                "Many fates loosen at once. The cave shakes with anger.",
                                "ending_liberator", -25, -15, 2, 3, 4),
                        choice("Leave the wall untouched",
                                "You keep your power, but the cave keeps its prisoners.",
                                "ending_bound", 10, 10, 0, 0, -3))));

        add(nodes, new StoryNode(
                "wolf_ambush",
                "Eyes Between Trees",
                "Wolves made of smoke surround you. They do not bark. They wait for fear.",
                SceneType.FOREST,
                false,
                Arrays.asList(
                        choice("Stand your ground",
                                "The wolves bow to courage and vanish.",
                                "captain_oath", -10, 0, 3, 0, 2),
                        choice("Distract them and escape",
                                "A clever trick keeps your bones unbitten.",
                                "hidden_library", -5, -5, 0, 3, 1),
                        choice("Run blindly",
                                "Panic has a cost.",
                                "fallen", -200, 0, 0, 0, -5))));

        add(nodes, new StoryNode(
                "mirror_room",
                "Room Of Possible Selves",
                "Mirrors show versions of you: one crowned, one forgotten, one feared, and one surrounded by friends. The center mirror is empty.",
                SceneType.TOWER,
                false,
                Arrays.asList(
                        choice("Choose the crowned reflection",
                                "Ambition sharpens your voice.",
                                "ending_tyrant", 10, -10, 1, 1, -3),
                        choice("Choose the forgotten reflection",
                                "You step away from glory and toward peace.",
                                "ending_peace", 5, 0, 0, 2, 2),
                        choice("Break the mirrors",
                                "The tower loses control of the story.",
                                "ending_free", -10, -15, 3, 2, 3))));

        add(nodes, new StoryNode(
                "trap_hall",
                "Hall Of Little Blades",
                "The broken entrance leads into a narrow hallway. Hidden blades click behind the stone walls.",
                SceneType.TOWER,
                false,
                Arrays.asList(
                        choice("Dash through before the trap resets",
                                "Speed saves you from the worst of it.",
                                "mirror_room", -14, 0, 2, 1, 0),
                        choice("Study the rhythm",
                                "Pattern beats panic.",
                                "hidden_library", -4, -4, 0, 3, 1),
                        choice("Turn back to the crossroads",
                                "The night gives you one more chance.",
                                "crossroads", 0, 0, 0, 0, 0))));

        add(nodes, new StoryNode(
                "hidden_library",
                "Library Below Dust",
                "Under the tower is a library no king could burn. A book opens by itself and writes: The crown is not treasure. It is a question.",
                SceneType.TOWER,
                false,
                Arrays.asList(
                        choice("Read the forbidden chapter",
                                "The truth hurts, but it makes the next lie easier to see.",
                                "prisoner_secret", -5, -15, 0, 3, 2),
                        choice("Take the map inside the book",
                                "The map marks a crown buried under forest roots.",
                                "buried_crown", 0, 0, 0, 2, 1),
                        choice("Burn the book",
                                "Knowledge screams when it dies.",
                                "ending_bound", 0, -10, 1, 0, -3))));

        add(nodes, new StoryNode(
                "captain_oath",
                "The Captain's Oath",
                "The captain kneels and offers you command of the surviving guard. With one order, you can save the castle, pursue the raiders, or leave the war behind.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Lead the guard in a counterattack",
                                "Your name becomes a battle cry.",
                                "ending_champion", -18, -5, 4, 0, 2),
                        choice("Send scouts after the raiders",
                                "A careful hunt finds the trail to the buried crown.",
                                "buried_crown", -4, 0, 0, 3, 1),
                        choice("Walk away from the crown and war",
                                "You choose a smaller life that belongs to you.",
                                "ending_peace", 8, 0, 0, 1, 2))));

        add(nodes, new StoryNode(
                "prisoner_secret",
                "The Prisoner's Secret",
                "The prisoner was once the royal seer. She says the crown chooses people who think fate is something to conquer.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Trust the seer",
                                "She leads you to the crown's resting place.",
                                "buried_crown", 0, -5, 0, 2, 2),
                        choice("Trade the secret for power",
                                "The wrong people pay very well.",
                                "ending_shadow", 10, 0, 0, 4, -3),
                        choice("Ask her to teach you fate magic",
                                "Magic answers, but not gently.",
                                "cave_omen", -10, 20, 0, 1, 1))));

        add(nodes, new StoryNode(
                "royal_letter",
                "A Letter Sealed In Black Wax",
                "The royal letter admits the siege was planned. The king wanted fear, because fear makes people beg for chains.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Expose the king",
                                "Truth spreads faster than fire.",
                                "ending_liberator", -10, -5, 3, 2, 4),
                        choice("Blackmail the king",
                                "The throne becomes your purse.",
                                "ending_shadow", 15, 0, 0, 4, -4),
                        choice("Give the letter to the captain",
                                "The guard finally knows where loyalty should point.",
                                "captain_oath", 0, 0, 2, 1, 1))));

        add(nodes, ending("ending_warden", "Forest Warden",
                "You break the curse and become guardian of the roads. People still argue about destiny, but when they enter the forest, they ask permission first."));
        add(nodes, ending("ending_tyrant", "Crowned Tyrant",
                "You take the crown and bend the kingdom around your will. The world becomes safer, quieter, and afraid to breathe."));
        add(nodes, ending("ending_shadow", "Master Of Shadows",
                "You never sit on a throne, but every throne learns to listen for your footsteps. Fate did not catch you. You caught it first."));
        add(nodes, ending("ending_free", "Fatebreaker",
                "You reject the path written for you. The road ahead is uncertain, but every step is yours."));
        add(nodes, ending("ending_liberator", "Liberator",
                "You free people from a future chosen by rulers, spirits, and old magic. The kingdom becomes messy, loud, and alive."));
        add(nodes, ending("ending_bound", "Bound To The Old Story",
                "You keep your strength, but the world keeps its chains. Years later, you still dream of the blank space where your ending should have been."));
        add(nodes, ending("ending_champion", "Champion Of Ashwake",
                "You save the castle and become the hero people needed. Songs simplify what happened, but you remember every cost."));
        add(nodes, ending("ending_peace", "Quiet Road",
                "You walk away before the crown can name you. Some call that cowardice. Others call it wisdom. You call it peace."));
        add(nodes, ending("fallen", "Fallen",
                "Your journey ends before the red moon sets. Fate moves on, but another hero may still choose differently."));

        return nodes;
    }

    private static Choice choice(String text, String result, String nextNodeId,
            int healthDelta, int manaDelta, int courageDelta, int cunningDelta, int fateDelta) {
        return new Choice(text, result, nextNodeId, healthDelta, manaDelta, courageDelta, cunningDelta, fateDelta);
    }

    private static StoryNode ending(String id, String title, String body) {
        return new StoryNode(id, title, body, SceneType.ENDING, true, Arrays.<Choice>asList());
    }

    private static void add(Map<String, StoryNode> nodes, StoryNode node) {
        nodes.put(node.getId(), node);
    }
}
