package rpgsumting;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class StoryGraph {
    public static final String RESUME_NODE_ID = "__resume_story__";

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

    public static String[] getRandomScenarioNodeIds() {
        return new String[] {
                "random_moon_shrine",
                "random_traveling_merchant",
                "random_bandit_road",
                "random_wild_magic",
                "random_storm_bridge",
                "random_lost_caravan",
                "random_fever_dream",
                "random_old_companion",
                "random_forest_clearing",
                "random_village_festival",
                "random_lonely_road",
                "random_graveyard_bells",
                "random_inn_room",
                "random_river_ford" };
    }

    public static String[] getEndingVariantNodeIds(String endingNodeId) {
        if ("ending_warden".equals(endingNodeId)) {
            return new String[] { "ending_warden", "ending_warden_greenroad", "ending_warden_spirit_court" };
        }
        if ("ending_tyrant".equals(endingNodeId)) {
            return new String[] { "ending_tyrant", "ending_tyrant_iron_law", "ending_tyrant_lonely_crown" };
        }
        if ("ending_shadow".equals(endingNodeId)) {
            return new String[] { "ending_shadow", "ending_shadow_guildmaster", "ending_shadow_vanished" };
        }
        if ("ending_free".equals(endingNodeId)) {
            return new String[] { "ending_free", "ending_free_wanderer", "ending_free_new_stars" };
        }
        if ("ending_liberator".equals(endingNodeId)) {
            return new String[] { "ending_liberator", "ending_liberator_republic", "ending_liberator_restless" };
        }
        if ("ending_bound".equals(endingNodeId)) {
            return new String[] { "ending_bound", "ending_bound_crown_dream", "ending_bound_loop" };
        }
        if ("ending_champion".equals(endingNodeId)) {
            return new String[] { "ending_champion", "ending_champion_general", "ending_champion_scarred" };
        }
        if ("ending_peace".equals(endingNodeId)) {
            return new String[] { "ending_peace", "ending_peace_innkeeper", "ending_peace_hidden_hero" };
        }
        if ("fallen".equals(endingNodeId)) {
            return new String[] { "fallen", "fallen_rescued", "fallen_legend" };
        }

        return new String[0];
    }

    public static String getFinaleStartNodeId(String endingNodeId) {
        if ("ending_warden".equals(endingNodeId)) {
            return "finale_warden_roads";
        }
        if ("ending_tyrant".equals(endingNodeId)) {
            return "finale_tyrant_throne";
        }
        if ("ending_shadow".equals(endingNodeId)) {
            return "finale_shadow_network";
        }
        if ("ending_free".equals(endingNodeId)) {
            return "finale_free_unwritten_map";
        }
        if ("ending_liberator".equals(endingNodeId)) {
            return "finale_liberator_square";
        }
        if ("ending_bound".equals(endingNodeId)) {
            return "finale_bound_old_road";
        }
        if ("ending_champion".equals(endingNodeId)) {
            return "finale_champion_wall";
        }
        if ("ending_peace".equals(endingNodeId)) {
            return "finale_peace_crossing";
        }
        if ("fallen".equals(endingNodeId)) {
            return "finale_fallen_last_breath";
        }

        return null;
    }

    public static Map<String, StoryNode> create() {
        Map<String, StoryNode> nodes = new LinkedHashMap<String, StoryNode>();

        add(nodes, new StoryNode(
                "mage_start",
                "Mage Scenario: The Rift Exam",
                "Your final exam begins at midnight inside the broken wizard tower. A blue rift opens above the spell circle, and your teacher vanishes into it before finishing the warning.",
                SceneType.ACADEMY,
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
                SceneType.MARKET,
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
                SceneType.MARKET,
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
                                "city_bounty", 12, 0, 0, 5, -4))));

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
                SceneType.ACADEMY,
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
                SceneType.ACADEMY,
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
                                "book_returns", 0, 5, 0, 1, -3))));

        add(nodes, new StoryNode(
                "knight_tournament_start",
                "Knight Scenario: The False Tournament",
                "The king hosts a tournament to choose a new champion, but every opponent wears the same black wax seal. The crowd cheers before the first sword is drawn.",
                SceneType.BATTLEFIELD,
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
                                "shield_whispers", 12, 0, 1, 0, -3))));

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
                SceneType.MARKET,
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
                                "black_market_bargain", 8, 0, 0, 5, -3))));

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
                                "black_market_bargain", 15, 0, 0, 4, -4))));

        add(nodes, new StoryNode(
                "thief_rooftop_start",
                "Thief Scenario: Chase Over Red Tiles",
                "The city guard chases you across rain-slick rooftops after a job goes wrong. Below, raiders enter the city gate disguised as merchants.",
                SceneType.MARKET,
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
                                "city_bounty", 10, 0, 0, 3, -4))));

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
                SceneType.MOUNTAIN,
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
                                "crown_destroy_ritual", -5, -20, 3, 0, 3),
                        choice("Wear the crown",
                                "The crown fits perfectly, as if it waited for you.",
                                "crown_coronation", 15, -15, 1, 1, -4),
                        choice("Hide it and tell no one",
                                "Secrets are lighter than gold until they become chains.",
                                "crown_hide_chase", 0, 0, 0, 4, -2))));

        add(nodes, new StoryNode(
                "cave_omen",
                "The Cave Of Names",
                "The spirit's power leads you to a cave wall covered with names. Yours is being carved by an invisible hand, but the ending is still blank.",
                SceneType.CAVE,
                false,
                Arrays.asList(
                        choice("Carve your own ending",
                                "The cave accepts your will, but takes a piece of your life in payment.",
                                "cave_rewrite", -18, -20, 3, 2, 3),
                        choice("Erase every name you can reach",
                                "Many fates loosen at once. The cave shakes with anger.",
                                "fate_prison_break", -25, -15, 2, 3, 4),
                        choice("Leave the wall untouched",
                                "You keep your power, but the cave keeps its prisoners.",
                                "cave_leave_consequence", 10, 10, 0, 0, -3))));

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
                                "dark_marsh", -28, 0, 0, 0, -3))));

        add(nodes, new StoryNode(
                "mirror_room",
                "Room Of Possible Selves",
                "Mirrors show versions of you: one crowned, one forgotten, one feared, and one surrounded by friends. The center mirror is empty.",
                SceneType.TOWER,
                false,
                Arrays.asList(
                        choice("Choose the crowned reflection",
                                "Ambition sharpens your voice.",
                                "throne_vision", 10, -10, 1, 1, -3),
                        choice("Choose the forgotten reflection",
                                "You step away from glory and toward peace.",
                                "quiet_road_trial", 5, 0, 0, 2, 2),
                        choice("Break the mirrors",
                                "The tower loses control of the story.",
                                "mirror_breakfall", -10, -15, 3, 2, 3))));

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
                                "ash_oracle", 0, -10, 1, 0, -3))));

        add(nodes, new StoryNode(
                "captain_oath",
                "The Captain's Oath",
                "The captain kneels and offers you command of the surviving guard. With one order, you can save the castle, pursue the raiders, or leave the war behind.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Lead the guard in a counterattack",
                                "Your name becomes a battle cry.",
                                "war_council", -18, -5, 4, 0, 2),
                        choice("Send scouts after the raiders",
                                "A careful hunt finds the trail to the buried crown.",
                                "raider_trail", -4, 0, 0, 3, 1),
                        choice("Walk away from the crown and war",
                                "You choose a smaller life that belongs to you.",
                                "deserter_crossing", 8, 0, 0, 1, 2))));

        add(nodes, new StoryNode(
                "prisoner_secret",
                "The Prisoner's Secret",
                "The prisoner was once the royal seer. She says the crown chooses people who think fate is something to conquer.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Trust the seer",
                                "She leads you to the crown's resting place.",
                                "seer_escape", 0, -5, 0, 2, 2),
                        choice("Trade the secret for power",
                                "The wrong people pay very well.",
                                "black_market_bargain", 10, 0, 0, 4, -3),
                        choice("Ask her to teach you fate magic",
                                "Magic answers, but not gently.",
                                "fate_magic_lesson", -10, 20, 0, 1, 1))));

        add(nodes, new StoryNode(
                "royal_letter",
                "A Letter Sealed In Black Wax",
                "The royal letter admits the siege was planned. The king wanted fear, because fear makes people beg for chains.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Expose the king",
                                "Truth spreads faster than fire.",
                                "rebellion_square", -10, -5, 3, 2, 4),
                        choice("Blackmail the king",
                                "The throne becomes your purse.",
                                "blackmail_court", 15, 0, 0, 4, -4),
                        choice("Give the letter to the captain",
                                "The guard finally knows where loyalty should point.",
                                "loyal_guard", 0, 0, 2, 1, 1))));

        add(nodes, new StoryNode(
                "crown_destroy_ritual",
                "The Ashen Crown Ritual",
                "The crown breaks, but its ashes swirl into three shapes: a tree, a banner, and your own shadow. Destroying an artifact is easier than deciding who inherits the freedom it leaves behind.",
                SceneType.TEMPLE,
                false,
                Arrays.asList(
                        choice("Bind the ashes to the forest",
                                "The roads grow safer, and the forest accepts you as its living oath.",
                                "ending_warden", -8, -12, 3, 0, 4),
                        choice("Scatter the ashes over the rebel crowd",
                                "Every person touched by ash remembers a future stolen from them.",
                                "ending_liberator", -5, -10, 2, 3, 4),
                        choice("Seal the ashes inside your shadow",
                                "You keep the curse away from others, but your shadow starts making plans.",
                                "ending_shadow", -3, -8, 0, 4, -1))));

        add(nodes, new StoryNode(
                "crown_coronation",
                "The Crown That Kneels To No One",
                "The crown shows you every road, every army, and every hungry village. It does not ask whether you want power. It asks what kind of ruler you can survive becoming.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Command the kingdom into obedience",
                                "Peace arrives quickly when fear carries the law.",
                                "ending_tyrant", 20, -15, 2, 1, -5),
                        choice("Use the crown to end the siege",
                                "The army bows because victory is difficult to argue with.",
                                "ending_champion", -8, -20, 4, 0, 2),
                        choice("Remove the crown before it learns your heart",
                                "The crown hates being refused by someone it almost owned.",
                                "crown_hide_chase", -10, -10, 2, 2, 2))));

        add(nodes, new StoryNode(
                "crown_hide_chase",
                "The Chase Through Reedwater Marsh",
                "By dawn, crown-hunters follow you into black water and hanging fog. The crown hums from your pack, promising silence if you give it one honest secret.",
                SceneType.SWAMP,
                false,
                Arrays.asList(
                        choice("Lose the hunters in the marsh",
                                "You become a rumor with muddy boots and dangerous friends.",
                                "ending_shadow", -6, 0, 0, 4, -2),
                        choice("Give the crown to the river temple",
                                "The temple bells ring under the water for the first time in a century.",
                                "ending_warden", -4, -8, 2, 2, 3),
                        choice("Walk away and let the hunters fight over it",
                                "You leave fate behind while louder people destroy each other over it.",
                                "ending_peace", 5, 0, 0, 2, 2))));

        add(nodes, new StoryNode(
                "cave_rewrite",
                "Ink In The Cave Wall",
                "Your carved ending bleeds into the stone and opens a tunnel of unfinished lives. Voices beg you to choose not only your ending, but the rules for everyone else's.",
                SceneType.CAVE,
                false,
                Arrays.asList(
                        choice("Write that no fate is permanent",
                                "The cave cracks from floor to ceiling as choice returns to the world.",
                                "ending_free", -12, -18, 3, 2, 4),
                        choice("Write yourself as guardian of all endings",
                                "A thousand roads bend toward your judgment.",
                                "ending_warden", 0, -22, 2, 1, 1),
                        choice("Write your enemies out of the story",
                                "The cave obeys, and you learn how easy cruelty sounds when written neatly.",
                                "ending_tyrant", 10, -20, 1, 3, -5))));

        add(nodes, new StoryNode(
                "fate_prison_break",
                "The Prison Of Names",
                "The erased names become people made of candlelight. Some thank you. Some blame you for waking them into a world that already replaced them.",
                SceneType.TEMPLE,
                false,
                Arrays.asList(
                        choice("Lead the freed names into the capital",
                                "The city gates open to people history tried to bury.",
                                "ending_liberator", -12, -12, 3, 3, 4),
                        choice("Teach them to choose new lives",
                                "The world grows stranger, kinder, and harder to control.",
                                "ending_free", -8, -10, 2, 3, 3),
                        choice("Use them as an invisible army",
                                "No wall can stop soldiers who were once only names.",
                                "ending_shadow", 5, -14, 0, 4, -3))));

        add(nodes, new StoryNode(
                "cave_leave_consequence",
                "The Road That Remembers",
                "You leave the cave untouched, but every milestone on the road now has your name carved into it. The old story is following at walking speed.",
                SceneType.MOUNTAIN,
                false,
                Arrays.asList(
                        choice("Keep walking until the road gives up",
                                "You become hard to find and harder to command.",
                                "ending_peace", 6, 0, 1, 2, 2),
                        choice("Return and face the cave properly",
                                "The cave opens like it expected your second thought.",
                                "cave_rewrite", -6, -8, 2, 1, 1),
                        choice("Let the old story catch you",
                                "Comfort arrives wearing chains you recognize.",
                                "ending_bound", 10, 10, 0, 0, -4))));

        add(nodes, new StoryNode(
                "throne_vision",
                "The Throne Inside The Mirror",
                "The crowned reflection steps out and offers you a throne made from every choice you avoided. It knows your voice. It has been practicing.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Take the throne from your reflection",
                                "Only one version of you is allowed to rule.",
                                "ending_tyrant", 12, -12, 2, 2, -4),
                        choice("Command the reflection to fight beside you",
                                "A second self is a dangerous ally but a useful one.",
                                "war_council", -6, -16, 3, 1, 1),
                        choice("Refuse the throne and shatter the room",
                                "The mirror world screams as its favorite ending dies.",
                                "mirror_breakfall", -10, -10, 3, 2, 3))));

        add(nodes, new StoryNode(
                "quiet_road_trial",
                "The Trial Of The Quiet Road",
                "The road away from glory is guarded by three silent figures: a child you could save, an enemy you could spare, and a crown you could ignore.",
                SceneType.MOUNTAIN,
                false,
                Arrays.asList(
                        choice("Save the child and keep walking",
                                "No song follows you, but one family remembers.",
                                "ending_peace", 4, 0, 0, 2, 3),
                        choice("Spare the enemy and ask for truth",
                                "Mercy buys information about the raiders' last camp.",
                                "raider_trail", -3, 0, 1, 2, 1),
                        choice("Ignore the crown completely",
                                "For once, fate is the thing being abandoned.",
                                "ending_free", 0, 0, 2, 2, 3))));

        add(nodes, new StoryNode(
                "mirror_breakfall",
                "Falling Through Broken Reflections",
                "Every shard becomes a doorway. You fall past lives where you were kinder, crueler, richer, dead, loved, feared, and forgotten.",
                SceneType.TOWER,
                false,
                Arrays.asList(
                        choice("Land in the life where people are free",
                                "You drag that possibility back into your own world.",
                                "ending_liberator", -10, -12, 3, 3, 4),
                        choice("Land in the life where no one controls you",
                                "You wake with glass in your hands and no path above you.",
                                "ending_free", -8, -10, 3, 2, 3),
                        choice("Grab the shard showing your safest life",
                                "Safety has a door. It also has a lock.",
                                "ending_bound", 8, 0, 0, 1, -3))));

        add(nodes, new StoryNode(
                "war_council",
                "War Council At Dawn",
                "By sunrise, captains, scouts, rebels, and frightened nobles argue over a map stained with tea and blood. Everyone wants your decision because none of them want the blame.",
                SceneType.BATTLEFIELD,
                false,
                Arrays.asList(
                        choice("Lead a direct strike on the raiders",
                                "You turn a broken army into a spear.",
                                "ending_champion", -15, -5, 4, 0, 2),
                        choice("Reveal the king's betrayal to every soldier",
                                "The army changes sides one campfire at a time.",
                                "ending_liberator", -8, -5, 3, 3, 4),
                        choice("Take command and keep the truth hidden",
                                "Victory comes first. Explanations can be buried later.",
                                "ending_tyrant", 8, 0, 3, 2, -4))));

        add(nodes, new StoryNode(
                "raider_trail",
                "Trail Of Broken Wheels",
                "Your scouts find the raiders' wagon trail near the river docks. The tracks split three ways: toward smugglers, toward the swamp, and toward a camp flying stolen royal colors.",
                SceneType.DOCKS,
                false,
                Arrays.asList(
                        choice("Question the smugglers",
                                "The smugglers sell secrets cheaper than loyalty.",
                                "black_market_bargain", -2, 0, 0, 3, 0),
                        choice("Follow the swamp tracks",
                                "The crown's trail vanishes into Reedwater fog.",
                                "crown_hide_chase", -5, 0, 1, 2, 1),
                        choice("Attack the stolen-color camp",
                                "The camp breaks, but the survivors name the king as their buyer.",
                                "rebellion_square", -12, -4, 3, 1, 2))));

        add(nodes, new StoryNode(
                "deserter_crossing",
                "The Bridge Of Deserters",
                "On the road away from war, deserters from both armies block a narrow bridge. They are tired, hungry, and one insult away from killing each other.",
                SceneType.MOUNTAIN,
                false,
                Arrays.asList(
                        choice("Convince them to protect refugees",
                                "A broken army becomes a stubborn little shield.",
                                "ending_warden", -3, 0, 2, 2, 3),
                        choice("Lead them back to finish the fight",
                                "They return because you make courage sound possible.",
                                "war_council", 0, 0, 3, 0, 1),
                        choice("Cross alone before they choose for you",
                                "You leave the war smaller than you found it.",
                                "ending_peace", 8, 0, 0, 1, 2))));

        add(nodes, new StoryNode(
                "seer_escape",
                "Escape By The Moonlit Docks",
                "The seer leads you through fish markets and foggy docks while royal hunters search every boat. She says the crown does not predict rulers. It breeds them.",
                SceneType.DOCKS,
                false,
                Arrays.asList(
                        choice("Sail toward the buried crown",
                                "The tide carries you to ruins older than the kingdom.",
                                "buried_crown", -3, -5, 0, 2, 2),
                        choice("Ask the seer to break your prophecy",
                                "She can try, but prophecy breaks like glass: loudly and sharply.",
                                "fate_magic_lesson", -6, -10, 1, 2, 2),
                        choice("Hide the seer in a quiet village",
                                "She survives, and for once that is enough.",
                                "ending_peace", 4, 0, 0, 3, 2))));

        add(nodes, new StoryNode(
                "black_market_bargain",
                "The Black Market Under Lantern Rain",
                "Below the city, masked traders sell maps, poisons, fake crowns, stolen prayers, and memories in blue bottles. Every deal has a price written after you agree.",
                SceneType.MARKET,
                false,
                Arrays.asList(
                        choice("Buy the map no one will touch",
                                "The map points to crown roots and marks a warning in your handwriting.",
                                "buried_crown", -2, 0, 0, 3, 1),
                        choice("Sell a secret about the king",
                                "The market turns your whisper into a weapon.",
                                "rebellion_square", 6, 0, 0, 4, -1),
                        choice("Trade your mercy for influence",
                                "Power answers fast when you stop asking nicely.",
                                "ending_shadow", 10, 0, 0, 5, -4))));

        add(nodes, new StoryNode(
                "fate_magic_lesson",
                "The Lesson No Academy Teaches",
                "The seer draws a circle in salt and says fate magic is not about seeing the future. It is about noticing who keeps paying for it.",
                SceneType.ACADEMY,
                false,
                Arrays.asList(
                        choice("Use the lesson to rewrite your path",
                                "The cave of names opens in your dreams and waits for your hand.",
                                "cave_rewrite", -8, -18, 2, 2, 3),
                        choice("Use the lesson to free trapped names",
                                "Every name you speak becomes a candle in the dark.",
                                "fate_prison_break", -10, -15, 2, 3, 4),
                        choice("Refuse the lesson before it changes you",
                                "Some doors close gently. This one bites.",
                                "ending_bound", 0, 8, 0, 1, -3))));

        add(nodes, new StoryNode(
                "rebellion_square",
                "The Square Where Truth Catches Fire",
                "By noon, the king's letter has been copied onto walls, shields, tavern doors, and bread wrappers. The crowd waits for someone to aim its anger.",
                SceneType.MARKET,
                false,
                Arrays.asList(
                        choice("Turn the crowd into a rebellion",
                                "The palace gates shake under ordinary hands.",
                                "ending_liberator", -10, -5, 3, 3, 5),
                        choice("Protect the crowd from royal soldiers",
                                "Your defense becomes the first honest banner of the war.",
                                "ending_champion", -12, -4, 4, 1, 3),
                        choice("Let the crowd crown you instead",
                                "People frightened of kings can still build another one.",
                                "ending_tyrant", 12, -5, 2, 3, -4))));

        add(nodes, new StoryNode(
                "blackmail_court",
                "The Court Of Smiling Knives",
                "The king reads your blackmail note without blinking. Then every courtier in the room begins deciding whether you are useful, dangerous, or already dead.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Make the king your puppet",
                                "The kingdom never sees your hand, only the strings moving.",
                                "ending_shadow", 8, 0, 0, 5, -4),
                        choice("Take the throne openly",
                                "Subtlety dies the moment the crown touches your head.",
                                "ending_tyrant", 15, -5, 3, 2, -5),
                        choice("Release the evidence before they kill you",
                                "Your escape is ugly, but the truth survives it.",
                                "rebellion_square", -15, 0, 2, 3, 3))));

        add(nodes, new StoryNode(
                "loyal_guard",
                "The Guard Chooses A Side",
                "The captain reads the royal letter twice. Then he orders every guard to remove the king's colors until loyalty means something again.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("March with the guard to the palace",
                                "The city hears armored boots and opens its shutters.",
                                "war_council", -3, 0, 3, 1, 2),
                        choice("Send the guard to protect the square",
                                "The rebellion gains shields before it gains leaders.",
                                "rebellion_square", -2, 0, 2, 2, 3),
                        choice("Keep the letter secret until the war ends",
                                "The guard obeys, but silence starts tasting like a crown.",
                                "ending_champion", -8, 0, 3, 0, 0))));

        add(nodes, new StoryNode(
                "ash_oracle",
                "The Oracle In The Burned Pages",
                "The book burns into ash, but the ash arranges itself into a face on the floor. It says knowledge can die, but consequences are excellent at surviving.",
                SceneType.TEMPLE,
                false,
                Arrays.asList(
                        choice("Ask the oracle how to fix the damage",
                                "It points you toward the cave where stories are carved.",
                                "cave_omen", -5, -8, 1, 2, 2),
                        choice("Trap the oracle in a mirror shard",
                                "The oracle laughs because prisons still count as homes.",
                                "mirror_room", -4, -10, 0, 3, 0),
                        choice("Scatter the ash before it speaks again",
                                "The silence you wanted finally arrives.",
                                "ending_bound", 4, 0, 0, 1, -3))));

        add(nodes, new StoryNode(
                "dark_marsh",
                "The Dark Marsh After Panic",
                "Running saves you from the wolves but not from the marsh. The mud pulls at your boots while pale lanterns drift ahead, pretending to be safe.",
                SceneType.SWAMP,
                false,
                Arrays.asList(
                        choice("Follow the pale lanterns",
                                "They lead to a river temple where lost things are judged.",
                                "crown_hide_chase", -8, -6, 1, 1, 1),
                        choice("Climb a dead tree and wait for sunrise",
                                "From above, you see the old tower through the fog.",
                                "tower_gate", -4, 0, 1, 2, 0),
                        choice("Keep running until your body quits",
                                "Fear spends the last of your strength.",
                                "fallen", -200, 0, 0, 0, -5))));

        add(nodes, new StoryNode(
                "book_returns",
                "The Book Under Your Pillow",
                "You hide the grimoire, but it appears under your pillow the next morning with a fresh page titled Mistakes That Think They Are Secrets.",
                SceneType.ACADEMY,
                false,
                Arrays.asList(
                        choice("Finally read the fresh page",
                                "The page explains why the crown was buried under living roots.",
                                "hidden_library", -2, -8, 0, 3, 2),
                        choice("Bring the book to the seer",
                                "The seer recognizes the handwriting as your future self.",
                                "fate_magic_lesson", -4, -8, 1, 2, 2),
                        choice("Lock it in the academy vault",
                                "By night, the vault breathes through the walls.",
                                "ending_bound", 0, 8, 0, 0, -3))));

        add(nodes, new StoryNode(
                "shield_whispers",
                "The Shield That Remembers Wars",
                "The haunted shield blocks arrows before they are fired and whispers commands before enemies move. It is protecting you. It is also training you.",
                SceneType.BATTLEFIELD,
                false,
                Arrays.asList(
                        choice("Use the shield to save the castle",
                                "Every blocked strike becomes a reason people follow you.",
                                "war_council", 4, 0, 3, 0, 1),
                        choice("Let the shield guide you to the crown",
                                "It remembers the ruler who first carried it.",
                                "crown_coronation", 8, -5, 2, 0, -2),
                        choice("Break the shield before it owns your arm",
                                "The dead knights inside it finally sleep.",
                                "ending_warden", -10, -5, 3, 1, 3))));

        add(nodes, new StoryNode(
                "city_bounty",
                "Wanted In Every Alley",
                "Your face appears on wet posters before sunset. Bounty hunters search rooftops, guards search taverns, and your old crew searches with personal enthusiasm.",
                SceneType.MARKET,
                false,
                Arrays.asList(
                        choice("Hide in the black market",
                                "The city below the city always has room for trouble.",
                                "black_market_bargain", -3, 0, 0, 4, 0),
                        choice("Trade your bounty for the seer's location",
                                "Someone always knows where a hunted prophet sleeps.",
                                "prisoner_secret", -5, 0, 0, 3, 1),
                        choice("Turn the bounty hunters against each other",
                                "By midnight, half the city fears your name and the other half wants lessons.",
                                "ending_shadow", 8, 0, 0, 5, -3))));

        add(nodes, new StoryNode(
                "random_moon_shrine",
                "Random Scenario: Moon Shrine",
                "A roadside shrine lights itself as you pass. Three bowls wait under the moon: water, ash, and silver thread.",
                SceneType.TEMPLE,
                false,
                Arrays.asList(
                        choice("Drink from the water bowl",
                                "The water closes old cuts and tastes like rain on stone.",
                                RESUME_NODE_ID, 16, 5, 0, 0, 1),
                        choice("Mark your palm with ash",
                                "The ash burns, but your next fear feels smaller.",
                                RESUME_NODE_ID, -4, 0, 2, 0, 2),
                        choice("Steal the silver thread",
                                "The thread wraps itself around your wrist like a debt.",
                                RESUME_NODE_ID, 0, 0, 0, 3, -2))));

        add(nodes, new StoryNode(
                "random_traveling_merchant",
                "Random Scenario: Traveling Merchant",
                "A lantern merchant rolls a cart onto the road and offers potions, rumors, and one box that keeps knocking from the inside.",
                SceneType.MARKET,
                false,
                Arrays.asList(
                        choice("Buy a red healing bottle",
                                "It is too sweet, but it works.",
                                RESUME_NODE_ID, 22, -3, 0, 0, 0),
                        choice("Pay for a rumor",
                                "The rumor changes shape until it fits your problem.",
                                RESUME_NODE_ID, 0, -4, 0, 3, 1),
                        choice("Open the knocking box",
                                "A tiny curse bites your finger and runs away laughing.",
                                RESUME_NODE_ID, -12, 8, 1, 1, -1))));

        add(nodes, new StoryNode(
                "random_bandit_road",
                "Random Scenario: Road Ambush",
                "Bandits step from the ditch with rusty blades and nervous eyes. Their leader keeps glancing behind them, like something worse is following.",
                SceneType.ROAD,
                false,
                Arrays.asList(
                        choice("Fight through the ambush",
                                "The bandits scatter when you break their courage first.",
                                RESUME_NODE_ID, -12, 0, 3, 0, 1),
                        choice("Trick them into chasing a false trail",
                                "They run the wrong way with impressive confidence.",
                                RESUME_NODE_ID, -2, -2, 0, 4, 1),
                        choice("Pay them to warn you about the road ahead",
                                "Their warning saves time, pride, and possibly blood.",
                                RESUME_NODE_ID, 0, -5, 0, 2, 0))));

        add(nodes, new StoryNode(
                "random_wild_magic",
                "Random Scenario: Wild Magic Storm",
                "A storm of blue sparks rolls across the ground. Rocks float, shadows point upward, and your heartbeat starts counting backward.",
                SceneType.ACADEMY,
                false,
                Arrays.asList(
                        choice("Absorb the loose magic",
                                "Power floods you faster than comfort allows.",
                                RESUME_NODE_ID, -10, 24, 0, 1, 1),
                        choice("Ground the storm with a weapon",
                                "The sparks vanish through steel and leave your hands shaking.",
                                RESUME_NODE_ID, -5, -4, 2, 0, 1),
                        choice("Study the pattern from a distance",
                                "The storm is random, but not meaningless.",
                                RESUME_NODE_ID, 0, -6, 0, 3, 2))));

        add(nodes, new StoryNode(
                "random_storm_bridge",
                "Random Scenario: Storm Bridge",
                "Rain hammers a rope bridge over a white river. Halfway across, the far anchor snaps and the bridge drops like a dying flag.",
                SceneType.RIVER,
                false,
                Arrays.asList(
                        choice("Leap before the bridge falls",
                                "You land hard, alive, and extremely awake.",
                                RESUME_NODE_ID, -14, 0, 3, 0, 1),
                        choice("Cut a support rope to swing across",
                                "The river misses you by one clever idea.",
                                RESUME_NODE_ID, -6, 0, 0, 4, 1),
                        choice("Climb down and cross through the river",
                                "The cold steals strength but not resolve.",
                                RESUME_NODE_ID, -10, -4, 2, 1, 0))));

        add(nodes, new StoryNode(
                "random_lost_caravan",
                "Random Scenario: Lost Caravan",
                "A caravan circles the same milestone for the seventh time. The drivers offer supplies if you can prove the road is not eating them.",
                SceneType.ROAD,
                false,
                Arrays.asList(
                        choice("Break the milestone's curse",
                                "The road stops turning in circles and the caravan cheers.",
                                RESUME_NODE_ID, -3, -10, 2, 1, 3),
                        choice("Guide them by hidden paths",
                                "You find shortcuts even the map refuses to admit.",
                                RESUME_NODE_ID, 4, 0, 0, 3, 1),
                        choice("Take supplies while they argue",
                                "Nobody notices until you are already useful somewhere else.",
                                RESUME_NODE_ID, 18, 0, 0, 3, -2))));

        add(nodes, new StoryNode(
                "random_fever_dream",
                "Random Scenario: Fever Dream",
                "For one breath, the world tilts into a dream. A child made of candlelight asks which memory you are willing to lose.",
                SceneType.SWAMP,
                false,
                Arrays.asList(
                        choice("Lose a painful memory",
                                "You wake lighter, but less careful.",
                                RESUME_NODE_ID, 10, 0, -1, 0, 2),
                        choice("Lose a proud memory",
                                "Humility hurts in a clean way.",
                                RESUME_NODE_ID, 0, 8, 1, 1, 2),
                        choice("Refuse the bargain",
                                "The dream leaves claw marks on the air.",
                                RESUME_NODE_ID, -8, 0, 2, 0, 0))));

        add(nodes, new StoryNode(
                "random_old_companion",
                "Random Scenario: An Old Companion",
                "Someone from your past waits beside a dead campfire. They know one truth about you and one lie everyone else believes.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Ask for the truth",
                                "The truth hurts less than wondering.",
                                RESUME_NODE_ID, 0, -4, 1, 2, 2),
                        choice("Ask them to spread the lie",
                                "Sometimes reputation is a weapon with no handle.",
                                RESUME_NODE_ID, 6, 0, 0, 4, -2),
                        choice("Share your supplies and part kindly",
                                "Kindness costs something, which is why it counts.",
                                RESUME_NODE_ID, -2, 0, 2, 1, 2))));

        add(nodes, new StoryNode(
                "random_forest_clearing",
                "Random Scenario: Forest Clearing",
                "You find a quiet clearing where old lanterns hang from branches with no hooks. Each lantern shows a place you have not visited yet.",
                SceneType.FOREST,
                false,
                Arrays.asList(
                        choice("Follow the lantern showing a safe path",
                                "The forest opens a shortcut and leaves green light on your boots.",
                                RESUME_NODE_ID, 6, 0, 1, 1, 2),
                        choice("Break the darkest lantern",
                                "The clearing shudders, but a hidden fear loses its grip.",
                                RESUME_NODE_ID, -6, -4, 3, 0, 2),
                        choice("Take a lantern for later",
                                "It glows only when you are about to lie.",
                                RESUME_NODE_ID, 0, -2, 0, 4, 0))));

        add(nodes, new StoryNode(
                "random_village_festival",
                "Random Scenario: Village Festival",
                "A village festival blocks the road with music, masks, hot food, and suspiciously cheerful guards. Everyone insists you join before asking why you are armed.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Rest and eat with the villagers",
                                "A full meal and honest laughter put strength back in your bones.",
                                RESUME_NODE_ID, 24, 0, 0, 0, 1),
                        choice("Join the mask contest to gather rumors",
                                "Behind masks, people tell the truth more easily.",
                                RESUME_NODE_ID, 0, -3, 0, 4, 1),
                        choice("Quietly check why the guards are nervous",
                                "You spot trouble early and leave with useful warnings.",
                                RESUME_NODE_ID, -3, 0, 2, 2, 2))));

        add(nodes, new StoryNode(
                "random_lonely_road",
                "Random Scenario: The Lonely Road",
                "The road stretches too straight for too long. Behind you, your footprints begin pointing the wrong direction.",
                SceneType.ROAD,
                false,
                Arrays.asList(
                        choice("Keep walking without looking back",
                                "The road respects stubbornness more than fear.",
                                RESUME_NODE_ID, -4, 0, 3, 0, 2),
                        choice("Mark the road with your weapon",
                                "The false path peels away like old paint.",
                                RESUME_NODE_ID, -2, -3, 1, 2, 1),
                        choice("Turn around and confront the wrong footprints",
                                "Your shadow takes one step late for the rest of the day.",
                                RESUME_NODE_ID, -8, 8, 0, 3, -1))));

        add(nodes, new StoryNode(
                "random_graveyard_bells",
                "Random Scenario: Graveyard Bells",
                "A graveyard bell rings under the earth. The headstones lean toward you as if trying to read your face.",
                SceneType.GRAVEYARD,
                false,
                Arrays.asList(
                        choice("Answer the buried bell",
                                "The dead lend you courage, but their grief is heavy.",
                                RESUME_NODE_ID, -5, -5, 3, 0, 2),
                        choice("Read the oldest headstone",
                                "The name is almost yours, but not quite.",
                                RESUME_NODE_ID, 0, -6, 0, 3, 2),
                        choice("Leave an offering and move on",
                                "Something unseen accepts the gift and lets the road quiet down.",
                                RESUME_NODE_ID, -2, 0, 1, 1, 2))));

        add(nodes, new StoryNode(
                "random_inn_room",
                "Random Scenario: The Locked Inn Room",
                "At a roadside inn, the keeper gives you a key to a room that is already locked from the inside. Someone behind the door is humming your name.",
                SceneType.INN,
                false,
                Arrays.asList(
                        choice("Open the room with force",
                                "The door breaks, and whatever waited inside leaves clawed marks in the floor.",
                                RESUME_NODE_ID, -10, 0, 3, 0, 0),
                        choice("Talk through the door first",
                                "The voice trades a secret for a promise you may regret.",
                                RESUME_NODE_ID, 0, -4, 0, 4, 1),
                        choice("Sleep in the common room instead",
                                "Practical fear is still practical.",
                                RESUME_NODE_ID, 18, 0, 0, 1, 0))));

        add(nodes, new StoryNode(
                "random_river_ford",
                "Random Scenario: River Ford",
                "A cold river cuts across your path. The bridge is damaged, the ford is fast, and a sealed message floats in an oilskin pouch near the far bank.",
                SceneType.RIVER,
                false,
                Arrays.asList(
                        choice("Cross the damaged bridge",
                                "The planks scream, but you make it across with time to spare.",
                                RESUME_NODE_ID, -8, 0, 3, 0, 1),
                        choice("Swim for the message",
                                "The river steals warmth but gives you a useful warning.",
                                RESUME_NODE_ID, -12, -3, 1, 3, 2),
                        choice("Build a safer crossing",
                                "Slow work saves pain and earns respect from passing travelers.",
                                RESUME_NODE_ID, 4, 0, 2, 1, 1))));

        add(nodes, new StoryNode(
                "finale_warden_roads",
                "Final Act: The Roads Ask Your Name",
                "The forest opens every road at once. Refugees, spirits, soldiers, thieves, and children all arrive at the same green crossroads, waiting to see what kind of guardian you will become.",
                SceneType.FOREST,
                false,
                Arrays.asList(
                        choice("Promise protection to anyone who walks honestly",
                                "The trees bend into archways and the road itself remembers your oath.",
                                "finale_warden_spirit_court", -4, -6, 2, 1, 3),
                        choice("Secretly choose who the road should favor",
                                "The forest obeys, but shadows gather beside every hidden decision.",
                                "ending_shadow", 0, -4, 0, 4, -2),
                        choice("Let the roads choose their own guardian",
                                "The forest tests whether humility is stronger than control.",
                                "finale_warden_spirit_court", 4, 0, 1, 2, 2))));

        add(nodes, new StoryNode(
                "finale_warden_spirit_court",
                "Final Act: Court Beneath Living Branches",
                "At the center of the woods, the spirit court argues over humans. Some want mercy, some want punishment, and one ancient root wants the kingdom sealed away forever.",
                SceneType.TEMPLE,
                false,
                Arrays.asList(
                        choice("Judge humans and spirits by the same law",
                                "The court falls silent because fairness is harder to fight than favor.",
                                "ending_warden", -5, -8, 3, 1, 4),
                        choice("Give every village a voice in the court",
                                "The spirits dislike democracy, but the forest remembers every broken promise.",
                                "ending_liberator", -3, -6, 2, 3, 4),
                        choice("Seal the court away to keep the peace",
                                "Silence returns, but silence is not the same as healing.",
                                "ending_bound", 8, 0, 0, 1, -3))));

        add(nodes, new StoryNode(
                "finale_tyrant_throne",
                "Final Act: The Throne Wakes",
                "The crown leads you to the throne room before dawn. Every chair turns toward you, every banner stiffens, and the empty throne asks what fear should be used for.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Rule by command before anyone can resist",
                                "Your first order lands like a hammer.",
                                "finale_tyrant_revolt", 10, -8, 3, 1, -3),
                        choice("Build a secret court behind the throne",
                                "The crown smiles at indirect power.",
                                "ending_shadow", 8, -4, 0, 4, -2),
                        choice("Invite your enemies to kneel or speak",
                                "Some kneel. Some speak. One draws a knife.",
                                "finale_tyrant_revolt", -8, -5, 2, 2, 0))));

        add(nodes, new StoryNode(
                "finale_tyrant_revolt",
                "Final Act: The First Revolt",
                "Your reign is only one hour old when bells ring across the city. The crowd outside does not know whether it wants freedom, revenge, food, or a better tyrant.",
                SceneType.BATTLEFIELD,
                false,
                Arrays.asList(
                        choice("Crush the revolt in one decisive strike",
                                "The city learns your mercy has a border.",
                                "ending_tyrant", 12, -6, 3, 0, -5),
                        choice("Throw open the gates and hear the crowd",
                                "You risk everything by treating anger as a warning instead of treason.",
                                "ending_liberator", -10, -5, 2, 3, 4),
                        choice("Lead the defense without taking the throne",
                                "People follow the hero they can see, not the crown they fear.",
                                "ending_champion", -12, 0, 4, 1, 2))));

        add(nodes, new StoryNode(
                "finale_shadow_network",
                "Final Act: The City Under The City",
                "Every hidden tunnel, dock cellar, backroom, and attic safehouse opens to you. The underworld crowns no rulers, but tonight every whisper waits for your signal.",
                SceneType.MARKET,
                false,
                Arrays.asList(
                        choice("Unite the guilds under one quiet hand",
                                "Old rivals bow because you know where every knife is hidden.",
                                "finale_shadow_last_job", 6, 0, 0, 5, -2),
                        choice("Burn the records and free your agents",
                                "Nobody can control a network that no longer exists.",
                                "ending_free", -4, -4, 2, 3, 3),
                        choice("Sell one final secret to the palace",
                                "The palace pays in gold and future obedience.",
                                "finale_shadow_last_job", 10, 0, 0, 4, -3))));

        add(nodes, new StoryNode(
                "finale_shadow_last_job",
                "Final Act: The Last Job",
                "Your final target is impossible: steal the crown's true name from a locked vault under the river while the king, rebels, and spirits all hunt the same prize.",
                SceneType.DOCKS,
                false,
                Arrays.asList(
                        choice("Keep the true name for yourself",
                                "Power belongs to whoever can stay unseen long enough to use it.",
                                "ending_shadow", 8, -4, 0, 5, -3),
                        choice("Give the true name to the people",
                                "A secret becomes a tool everyone can hold.",
                                "ending_liberator", -5, -5, 2, 3, 4),
                        choice("Use the true name to bind the throne",
                                "The crown cannot refuse someone who knows what it was called before kings.",
                                "ending_tyrant", 12, -8, 2, 3, -5))));

        add(nodes, new StoryNode(
                "finale_free_unwritten_map",
                "Final Act: The Unwritten Map",
                "A blank map appears in your hands. Roads draw themselves only when you stop expecting them, and behind you the old story sends riders to drag you back.",
                SceneType.ROAD,
                false,
                Arrays.asList(
                        choice("Run toward the road that does not exist yet",
                                "The map laughs in ink and opens a path through the sky.",
                                "finale_free_skydoor", -6, -5, 3, 2, 3),
                        choice("Hide the map in a quiet village",
                                "Freedom does not always need witnesses.",
                                "ending_peace", 6, 0, 0, 2, 2),
                        choice("Tear the map into pieces for other travelers",
                                "Every scrap becomes someone else's escape route.",
                                "finale_free_skydoor", -3, -8, 2, 3, 4))));

        add(nodes, new StoryNode(
                "finale_free_skydoor",
                "Final Act: Door In The New Stars",
                "At the mountain peak, a door hangs open in the night sky. Beyond it are every life you refused, every fate you broke, and every future still dangerous enough to be yours.",
                SceneType.MOUNTAIN,
                false,
                Arrays.asList(
                        choice("Step through and leave fate behind",
                                "The door closes only after you stop looking back.",
                                "ending_free", -4, -10, 3, 2, 4),
                        choice("Hold the door open for everyone else",
                                "Freedom becomes louder, messier, and less lonely.",
                                "ending_liberator", -10, -12, 3, 3, 5),
                        choice("Lock the door so no one can follow",
                                "Safety feels peaceful until you hear the lock answer from your side.",
                                "ending_bound", 8, 0, 0, 2, -3))));

        add(nodes, new StoryNode(
                "finale_liberator_square",
                "Final Act: The Square After Victory",
                "The palace gates fall. The crowd cheers, then turns to you with the harder question: what happens after people win the right to disagree?",
                SceneType.MARKET,
                false,
                Arrays.asList(
                        choice("Let every district choose a speaker",
                                "The first argument begins before the smoke clears, which means it is working.",
                                "finale_liberator_new_law", -5, -4, 2, 3, 4),
                        choice("Keep command until the city is stable",
                                "Temporary power is still power, and it learns your hand quickly.",
                                "finale_liberator_new_law", 6, 0, 3, 1, -1),
                        choice("Hand command to the guard and vanish",
                                "The city must learn to stand without turning you into a statue.",
                                "ending_free", 2, 0, 1, 3, 3))));

        add(nodes, new StoryNode(
                "finale_liberator_new_law",
                "Final Act: The First New Law",
                "By candlelight, farmers, guards, thieves, mages, refugees, and nobles shout over the first law of the new realm. The old crown waits on the table like a threat.",
                SceneType.TEMPLE,
                false,
                Arrays.asList(
                        choice("Write that no ruler stands above judgment",
                                "The room goes quiet because everyone understands the cost.",
                                "ending_liberator", -4, -6, 2, 3, 5),
                        choice("Put yourself above the law to protect it",
                                "The crowd accepts the excuse before you finish saying it.",
                                "ending_tyrant", 10, -4, 2, 2, -5),
                        choice("Refuse to write law and trust free choice",
                                "The room erupts, but nobody can mistake the chaos for chains.",
                                "ending_free", -2, -3, 1, 4, 3))));

        add(nodes, new StoryNode(
                "finale_bound_old_road",
                "Final Act: The Old Road Returns",
                "The same road from the beginning appears under your feet. The same moon rises. The same wind says you can still choose, but it sounds less convinced this time.",
                SceneType.ROAD,
                false,
                Arrays.asList(
                        choice("Walk the road exactly as written",
                                "The old story welcomes obedience like an old friend.",
                                "finale_bound_bargain", 8, 8, 0, 0, -3),
                        choice("Scratch a new direction into the milestone",
                                "Stone bleeds ink. The road hates edits.",
                                "finale_bound_bargain", -8, -8, 2, 2, 2),
                        choice("Sit down and refuse the scene",
                                "For a moment, the story has no idea what to do with you.",
                                "ending_free", -3, -5, 2, 3, 3))));

        add(nodes, new StoryNode(
                "finale_bound_bargain",
                "Final Act: Bargain With The Old Story",
                "The old story arrives wearing the faces of teachers, captains, parents, enemies, and friends. It offers comfort if you stop struggling against the shape it chose.",
                SceneType.GRAVEYARD,
                false,
                Arrays.asList(
                        choice("Accept the comfort and keep your place",
                                "The chains become soft enough to forget.",
                                "ending_bound", 10, 10, 0, 0, -4),
                        choice("Trade one secret for a hidden escape",
                                "The old story keeps the secret. You keep the door.",
                                "ending_shadow", 2, -5, 0, 4, -1),
                        choice("Choose a small life outside the story",
                                "It is not glorious, but it is yours.",
                                "ending_peace", 6, 0, 0, 2, 2))));

        add(nodes, new StoryNode(
                "finale_champion_wall",
                "Final Act: The Last Wall",
                "The final attack reaches Ashwake at sunrise. The wall is cracked, the gate is burning, and everyone looks at you because heroes are where fear goes to ask for orders.",
                SceneType.BATTLEFIELD,
                false,
                Arrays.asList(
                        choice("Stand on the wall where everyone can see you",
                                "Your courage becomes contagious and dangerous.",
                                "finale_champion_after_battle", -14, -4, 4, 0, 2),
                        choice("Slip out to strike the enemy commander",
                                "A hero unseen can still end a war.",
                                "finale_champion_after_battle", -10, -6, 2, 3, 1),
                        choice("Open the gate to evacuate the helpless",
                                "You may lose the castle, but not the people inside it.",
                                "ending_warden", -8, 0, 3, 2, 3))));

        add(nodes, new StoryNode(
                "finale_champion_after_battle",
                "Final Act: After The Songs Begin",
                "The battle ends. Bards already simplify the story while healers count the truth on bloody bandages. Victory asks what you will become when nobody needs saving this minute.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Serve as protector, not ruler",
                                "The banner stays high, but the throne stays empty of your name.",
                                "ending_champion", -3, 0, 3, 1, 3),
                        choice("Take command permanently",
                                "People cheer because they are tired. That makes it easier.",
                                "ending_tyrant", 12, 0, 3, 1, -4),
                        choice("Leave before the songs trap you",
                                "A hero can survive by refusing to become a symbol.",
                                "ending_peace", 8, 0, 0, 2, 2))));

        add(nodes, new StoryNode(
                "finale_peace_crossing",
                "Final Act: The Road Away",
                "You find a quiet road beyond the last checkpoint. No army follows. No spirit calls. Then a child runs from the village behind you, carrying one final request.",
                SceneType.ROAD,
                false,
                Arrays.asList(
                        choice("Stop and hear the request",
                                "Peace pauses, but it does not disappear.",
                                "finale_peace_last_request", 2, 0, 1, 2, 2),
                        choice("Keep walking before duty finds you again",
                                "The road accepts your exhaustion without judgment.",
                                "ending_peace", 10, 0, 0, 1, 2),
                        choice("Return to help one last time",
                                "One last time is how legends trick careful people.",
                                "finale_peace_last_request", -4, 0, 2, 1, 1))));

        add(nodes, new StoryNode(
                "finale_peace_last_request",
                "Final Act: The Small Thing That Matters",
                "The village does not ask you to kill a king or break a curse. They ask you to find three missing children before nightfall.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Find the children and stay for supper",
                                "The world becomes small enough to hold.",
                                "ending_peace", 12, 0, 0, 2, 3),
                        choice("Teach the village how to protect itself",
                                "You leave behind skills instead of a statue.",
                                "ending_warden", 4, 0, 2, 2, 3),
                        choice("Lead the children toward a future no one picked",
                                "They laugh at the road like it owes them adventure.",
                                "ending_free", 0, -4, 1, 3, 3))));

        add(nodes, new StoryNode(
                "finale_fallen_last_breath",
                "Final Act: One Last Breath",
                "Darkness closes in, but the story hesitates. Somewhere nearby, water drips, boots run, and a voice asks whether you are finished.",
                SceneType.GRAVEYARD,
                false,
                Arrays.asList(
                        choice("Force yourself back onto your feet",
                                "Pain answers first, but your body follows.",
                                "finale_fallen_dream", 24, -6, 3, 0, 2),
                        choice("Call out for anyone who can hear",
                                "Someone answers, though you cannot tell if they are living.",
                                "finale_fallen_dream", 16, -4, 1, 2, 1),
                        choice("Let the darkness take the choice",
                                "The road grows quiet around you.",
                                "fallen", -200, 0, 0, 0, -3))));

        add(nodes, new StoryNode(
                "finale_fallen_dream",
                "Final Act: The Dream Between Endings",
                "You dream of a table with three cups: bitter medicine, clear water, and black wine. A stranger says all three are endings, but only one is death.",
                SceneType.TEMPLE,
                false,
                Arrays.asList(
                        choice("Drink the bitter medicine",
                                "You wake weak, alive, and done with glory.",
                                "ending_peace", 18, 0, 0, 1, 2),
                        choice("Drink the clear water",
                                "You wake with no path left except the one you make.",
                                "ending_free", 10, -5, 2, 2, 3),
                        choice("Drink the black wine",
                                "The dream keeps you and sends your legend onward.",
                                "fallen", -200, 0, 0, 0, -4))));

        add(nodes, ending("ending_warden", "Forest Warden",
                "You break the curse and become guardian of the roads. People still argue about destiny, but when they enter the forest, they ask permission first."));
        add(nodes, ending("ending_warden_greenroad", "Forest Warden: The Green Road",
                "The cursed roads bloom under your watch. Merchants, refugees, and lost children begin leaving small carved leaves at your shrine, thanking the guardian they rarely see."));
        add(nodes, ending("ending_warden_spirit_court", "Forest Warden: Court Of Spirits",
                "The forest spirits name you judge of root, river, and road. Every full moon, humans and spirits bring their disputes to your fire and accept your word as law."));
        add(nodes, ending("ending_tyrant", "Crowned Tyrant",
                "You take the crown and bend the kingdom around your will. The world becomes safer, quieter, and afraid to breathe."));
        add(nodes, ending("ending_tyrant_iron_law", "Crowned Tyrant: Iron Law",
                "You build a kingdom where no thief steals, no rebel speaks twice, and no village starves unless you permit it. People survive, but they stop singing your name."));
        add(nodes, ending("ending_tyrant_lonely_crown", "Crowned Tyrant: The Lonely Crown",
                "The crown gives you every throne room, every army, and every secret. Years later, you realize it never promised you a single person who would tell you the truth."));
        add(nodes, ending("ending_shadow", "Master Of Shadows",
                "You never sit on a throne, but every throne learns to listen for your footsteps. Fate did not catch you. You caught it first."));
        add(nodes, ending("ending_shadow_guildmaster", "Master Of Shadows: Guildmaster",
                "The thieves, spies, smugglers, and quiet servants of the realm swear to your hidden banner. Kings still rule in daylight. You own the hours after dark."));
        add(nodes, ending("ending_shadow_vanished", "Master Of Shadows: Vanished",
                "One morning, every debt owed to you is paid and every enemy forgets your face. You vanish so completely that history argues whether you were one person or a warning."));
        add(nodes, ending("ending_free", "Fatebreaker",
                "You reject the path written for you. The road ahead is uncertain, but every step is yours."));
        add(nodes, ending("ending_free_wanderer", "Fatebreaker: The Unmapped Road",
                "No map keeps your path for more than a day. You wander through borders, seasons, and stories, always arriving where fate least expects resistance."));
        add(nodes, ending("ending_free_new_stars", "Fatebreaker: New Stars",
                "The old prophecies fade from the sky. New stars appear, shifting nightly, and every child born under them inherits a future no one has already written."));
        add(nodes, ending("ending_liberator", "Liberator",
                "You free people from a future chosen by rulers, spirits, and old magic. The kingdom becomes messy, loud, and alive."));
        add(nodes, ending("ending_liberator_republic", "Liberator: The First Vote",
                "The kingdom tears down its throne and builds a noisy council hall in its place. The arguments are exhausting, but for the first time they belong to everyone."));
        add(nodes, ending("ending_liberator_restless", "Liberator: Revolution Road",
                "Freeing one kingdom teaches you how many others are still chained. You leave before statues can find you, carrying rebellion from border to border."));
        add(nodes, ending("ending_bound", "Bound To The Old Story",
                "You keep your strength, but the world keeps its chains. Years later, you still dream of the blank space where your ending should have been."));
        add(nodes, ending("ending_bound_crown_dream", "Bound: The Crown Dream",
                "Every night you dream of the crown choosing someone else. Every morning you wake with less memory of the choices you almost made."));
        add(nodes, ending("ending_bound_loop", "Bound: The Looping Road",
                "The same crossroads returns in every town. The same moon rises over every roof. Somewhere, the real ending waits, but this version of you never reaches it."));
        add(nodes, ending("ending_champion", "Champion Of Ashwake",
                "You save the castle and become the hero people needed. Songs simplify what happened, but you remember every cost."));
        add(nodes, ending("ending_champion_general", "Champion: General Of The Dawn",
                "The broken guard becomes your army, and your army becomes the shield of the realm. Children learn your battles by name before they learn why peace matters."));
        add(nodes, ending("ending_champion_scarred", "Champion: The Scarred Banner",
                "You win, but victory leaves marks no bard can rhyme away. Your banner flies over Ashwake, stitched with the names of everyone who paid for the song."));
        add(nodes, ending("ending_peace", "Quiet Road",
                "You walk away before the crown can name you. Some call that cowardice. Others call it wisdom. You call it peace."));
        add(nodes, ending("ending_peace_innkeeper", "Quiet Road: Hearthlight",
                "Years later, travelers find safety at an inn with no sign. The owner listens more than they speak and always knows which roads are dangerous after moonrise."));
        add(nodes, ending("ending_peace_hidden_hero", "Quiet Road: The Hidden Hero",
                "No one writes your name in the royal records, but villages survive because someone taught them when to hide, when to run, and when to stand together."));
        add(nodes, ending("fallen", "Fallen",
                "Your journey ends before the red moon sets. Fate moves on, but another hero may still choose differently."));
        add(nodes, ending("fallen_rescued", "Fallen: One More Breath",
                "You fall before the road is finished, but strangers drag you from the dark. Your legend ends here. Your life, maybe, does not."));
        add(nodes, ending("fallen_legend", "Fallen: Campfire Warning",
                "Your bones are never found, but travelers tell your story beside low fires. They change the details each time, always keeping the same warning: choose faster."));

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
