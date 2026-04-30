package rpgsumting;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class StoryGraph {
    public static final String RESUME_NODE_ID = "__resume_story__";

    public static String[] getStartNodeIds(Job job) {
        if (job == Job.MAGE) {
            return new String[] { "mage_start" };
        }
        if (job == Job.KNIGHT) {
            return new String[] { "knight_start" };
        }
        if (job == Job.ROGUE) {
            return new String[] { "rogue_start" };
        }
        if (job == Job.THIEF) {
            return new String[] { "thief_start" };
        }
        if (job == Job.ARCHER) {
            return new String[] { "archer_start" };
        }

        return new String[] { "tavern_call" };
    }

    public static String[] getRandomScenarioNodeIds() {
        return new String[0];
    }

    public static String[] getEndingVariantNodeIds(String endingNodeId) {
        if ("ending_warden".equals(endingNodeId)
                || "ending_tyrant".equals(endingNodeId)
                || "ending_shadow".equals(endingNodeId)
                || "ending_free".equals(endingNodeId)
                || "ending_liberator".equals(endingNodeId)
                || "ending_bound".equals(endingNodeId)
                || "ending_champion".equals(endingNodeId)
                || "ending_peace".equals(endingNodeId)
                || "fallen".equals(endingNodeId)) {
            return new String[] { endingNodeId };
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
                "Mage Prologue: The Black-Wax Summons",
                "At Starfall Academy, Archmage Veyra interrupts your lesson with a royal summons sealed in black wax. The seal matches a forbidden chart of Emberdeep, the dungeon beneath Dragonspine Mountain. The chart warns that the Crown of Ash can wake Kaelthar, the oath-bound dragon below the realm.",
                SceneType.ACADEMY,
                false,
                Arrays.asList(
                        choice("Decode the dungeon chart before leaving",
                                "You learn the crown cannot be destroyed by force; it must be judged by an oath.",
                                "hidden_library", 0, -12, 0, 3, 2),
                        choice("Answer the summons at once",
                                "You arrive with spellbook ready and your name already written on the quest contract.",
                                "tavern_call", 0, -4, 1, 1, 1),
                        choice("Trace the black wax with a warding spell",
                                "The wax hisses the name of the Black-Wax Covenant before burning away.",
                                "road_watch", -4, -10, 1, 2, 1))));

        add(nodes, new StoryNode(
                "knight_start",
                "Knight Prologue: Oath At Ashwake",
                "Castle Ashwake calls every sworn blade to the war room. Warden Elowen explains that the Black-Wax Covenant has opened old roads to Emberdeep and stolen the first key to the Crown of Ash. Your oath is no longer to a wall, but to every village the wall protects.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Swear to protect Greenhollow first",
                                "The captain gives you a shield marked with the roadwarden's lantern.",
                                "greenhollow_square", 0, 0, 3, 0, 2),
                        choice("Demand a direct march on the dungeon",
                                "The veterans respect your courage, though the scouts warn that courage does not disarm traps.",
                                "barrow_door", -3, 0, 3, 0, 0),
                        choice("Question how the key was stolen",
                                "The witness describes a courtier with black wax under his fingernails.",
                                "village_clues", 0, 0, 1, 2, 1))));

        add(nodes, new StoryNode(
                "rogue_start",
                "Rogue Prologue: Ledger Of The Covenant",
                "In the lantern market, a contact slides you a stolen ledger. It lists bribes, cult safehouses, and a meeting at the Lantern & Lyre inn. The final page names Emberdeep as the dungeon where the realm's next ruler will be chosen.",
                SceneType.MARKET,
                false,
                Arrays.asList(
                        choice("Copy the safehouse list",
                                "The list marks a smuggler route under Greenhollow.",
                                "market_underbelly", -2, 0, 0, 4, 1),
                        choice("Attend the inn meeting in disguise",
                                "No one at the table trusts easily, which means you are among professionals.",
                                "tavern_call", 0, 0, 0, 3, 1),
                        choice("Follow the courier carrying the ledger seal",
                                "The courier leads you toward a cult procession on the old road.",
                                "cult_disguise", -4, 0, 0, 4, 0))));

        add(nodes, new StoryNode(
                "thief_start",
                "Thief Prologue: The Wrong Treasure",
                "You break into a noble vault for coin and find a map instead. The map marks Emberdeep, the Lantern & Lyre, and three locks around the Crown of Ash. When the vault alarm rings, you realize the Black-Wax Covenant wanted you blamed for stealing it.",
                SceneType.MARKET,
                false,
                Arrays.asList(
                        choice("Keep the map and run for the inn",
                                "The map folds itself around your wrist like a contract.",
                                "tavern_call", -3, 0, 0, 4, 1),
                        choice("Sell a fake copy to the cult",
                                "The fake buys you time and marks the cult's buyer for later.",
                                "market_underbelly", 4, 0, 0, 4, -1),
                        choice("Break into the vault records",
                                "The records prove the crown key was moved through Greenhollow.",
                                "village_clues", -5, 0, 0, 3, 2))));

        add(nodes, new StoryNode(
                "archer_start",
                "Archer Prologue: Smoke On The Old Road",
                "From the Greenhollow watchtower, you see smoke rising from the road to Dragonspine Mountain. Black-cloaked scouts drive villagers toward the forest while a horn sounds from Castle Ashwake. The trail points to Emberdeep.",
                SceneType.FOREST,
                false,
                Arrays.asList(
                        choice("Guide the villagers to safety",
                                "The rescued villagers tell you the cult took miners for a ritual below the mountain.",
                                "greenhollow_square", -4, 0, 2, 2, 2),
                        choice("Track the black-cloaked scouts",
                                "Their trail cuts toward a hidden barrow door in the mountain roots.",
                                "barrow_door", -5, 0, 3, 2, 1),
                        choice("Send a warning arrow to Ashwake",
                                "The castle answers with a lantern signal: meet the party at the inn.",
                                "tavern_call", 0, 0, 2, 1, 1))));

        add(nodes, new StoryNode(
                "tavern_call",
                "Chapter One: The Lantern & Lyre",
                "The Lantern & Lyre is packed with refugees, scouts, shrine-keepers, and sellswords. Warden Elowen lays a quest contract on the table: enter Emberdeep, recover the stolen key, stop the Black-Wax Covenant, and decide what must be done with the Crown of Ash.",
                SceneType.INN,
                false,
                Arrays.asList(
                        choice("Accept the contract before witnesses",
                                "The room steadies when someone finally says yes.",
                                "greenhollow_square", 0, 0, 2, 0, 2),
                        choice("Question the refugees for details",
                                "Their stories form one clear pattern: the cult is collecting keys, miners, and old oaths.",
                                "village_clues", 0, 0, 0, 3, 1),
                        choice("Study the dungeon map with the party",
                                "The safest route begins on the attacked road and ends at a sealed barrow.",
                                "road_watch", 0, -3, 0, 2, 1))));

        add(nodes, new StoryNode(
                "road_watch",
                "Chapter One: Ambush On The Old Road",
                "The party reaches a broken waystone as black-wax scouts surround a refugee cart. Their leader carries a bronze key that glows whenever someone speaks the dragon's name.",
                SceneType.ROAD,
                false,
                Arrays.asList(
                        choice("Hold the road and draw the scouts' attention",
                                "The refugees escape while the scouts learn this party will not scatter.",
                                "greenhollow_square", -10, 0, 3, 0, 2),
                        choice("Cut the scouts off from the trees",
                                "You recover a torn order naming the Greenhollow shrine as the next target.",
                                "old_shrine", -5, 0, 2, 2, 1),
                        choice("Let one scout flee and follow quietly",
                                "The trail leads through a smuggler tunnel beneath the market.",
                                "market_underbelly", -3, 0, 0, 4, 0))));

        add(nodes, new StoryNode(
                "greenhollow_square",
                "Chapter One: Greenhollow Needs Heroes",
                "Greenhollow's square has become a field hospital and quest board at once. The shrine bell is cracked, the mine road is empty, and every family knows someone taken toward Emberdeep.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Organize the village watch",
                                "Farmers, bakers, and guards form a rough militia around the square.",
                                "old_shrine", 0, 0, 3, 1, 2),
                        choice("Search for tracks near the mine road",
                                "Bootprints, drag marks, and candle wax point to the barrow door.",
                                "barrow_door", -2, 0, 1, 3, 1),
                        choice("Ask who profited from the attack",
                                "A nervous merchant admits the cult bought maps through the black market.",
                                "market_underbelly", 0, 0, 0, 4, 0))));

        add(nodes, new StoryNode(
                "village_clues",
                "Chapter One: Clues Around The Hearth",
                "The clues fit together like pieces on a campaign map: a stolen bronze key, miners taken alive, an old shrine oath, and a dungeon door that opens only for a party carrying both courage and guilt.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Bring the clues to the old shrine",
                                "The shrine-keeper recognizes the oath mark carved into the key.",
                                "old_shrine", 0, 0, 1, 2, 2),
                        choice("Pressure the merchant who bought cult maps",
                                "He gives up the safehouse rather than face the village.",
                                "market_underbelly", 0, 0, 0, 4, 0),
                        choice("Take the forest path before the cult moves",
                                "The woods are dangerous, but the cult does not expect a fast route.",
                                "forest_trail", -4, 0, 2, 2, 1))));

        add(nodes, new StoryNode(
                "forest_trail",
                "Chapter Two: The Thornroad",
                "The Thornroad winds through trees carved with old adventurers' names. The forest is not evil, but it remembers every party that entered Emberdeep and failed to return.",
                SceneType.FOREST,
                false,
                Arrays.asList(
                        choice("Leave offerings at the carved trees",
                                "The forest opens a quiet path to the shrine ruins.",
                                "old_shrine", 0, -4, 0, 2, 3),
                        choice("Move quickly before the trail changes",
                                "Speed gets you to the mountain, but thorns mark the cost.",
                                "barrow_door", -8, 0, 2, 1, 0),
                        choice("Read the names for a pattern",
                                "The missing names match the first oathkeepers of the dragon seal.",
                                "hidden_library", 0, -4, 0, 3, 2))));

        add(nodes, new StoryNode(
                "market_underbelly",
                "Chapter Two: Deals Below The Market",
                "Under Greenhollow's market, thieves, smugglers, and frightened informants trade in candles and secrets. The Black-Wax Covenant bought pickaxes, silence, and a false cleric's robe.",
                SceneType.MARKET,
                false,
                Arrays.asList(
                        choice("Buy the smuggler route to Emberdeep",
                                "The route reaches the dungeon, but every shortcut has a blade behind it.",
                                "sewer_gate", -2, 0, 0, 4, 0),
                        choice("Expose the cult buyer to the crowd",
                                "The market turns against the covenant for the first time.",
                                "greenhollow_square", -3, 0, 2, 2, 2),
                        choice("Wear the false robe into the cult line",
                                "The disguise works because everyone in a cult fears asking questions.",
                                "cult_disguise", 0, 0, 0, 5, -1))));

        add(nodes, new StoryNode(
                "old_shrine",
                "Chapter Two: Shrine Of The First Oath",
                "The ruined shrine holds a stone relief of Kaelthar the dragon bowing to three mortal oathkeepers. Sister Maela explains the old bargain: the crown may command the dragon, but only an oath can keep command from becoming tyranny.",
                SceneType.TEMPLE,
                false,
                Arrays.asList(
                        choice("Restore the shrine's broken ward",
                                "Warm light fills the room, and the bronze key stops screaming.",
                                "oath_trial", 0, -12, 1, 1, 4),
                        choice("Ask Maela for the shortest path below",
                                "She shows you a pilgrim mark hidden on the barrow map.",
                                "barrow_door", 0, 0, 1, 2, 2),
                        choice("Search the shrine library",
                                "You find a brittle bestiary and a warning about the crown chamber.",
                                "hidden_library", 0, -5, 0, 3, 2))));

        add(nodes, new StoryNode(
                "hidden_library",
                "Chapter Two: Records Of Lost Parties",
                "A hidden library beneath the shrine keeps the journals of failed expeditions. Their maps disagree on traps, monsters, and treasures, but every journal ends with the same line: the crown listens for the weakest promise.",
                SceneType.ACADEMY,
                false,
                Arrays.asList(
                        choice("Memorize the trap notes",
                                "You learn where the floor lies and where the ceiling listens.",
                                "trapped_gallery", 0, -6, 0, 4, 2),
                        choice("Study the oathkeeper journals",
                                "The journals prepare you for a trial that tests the party's reasons for fighting.",
                                "oath_trial", 0, -8, 1, 3, 3),
                        choice("Take the crown-chamber map",
                                "The map is incomplete, but it knows the dragon gate.",
                                "dungeon_threshold", 0, -4, 0, 3, 1))));

        add(nodes, new StoryNode(
                "sewer_gate",
                "Chapter Two: The Smuggler Gate",
                "The tunnel under the market slopes into older stone. Fresh black wax marks the walls, and stolen mining tools are stacked beside a gate built for people who never wanted to be seen.",
                SceneType.SWAMP,
                false,
                Arrays.asList(
                        choice("Disarm the gate mechanism",
                                "The gate opens without warning the cult patrols.",
                                "dungeon_threshold", -3, 0, 0, 4, 1),
                        choice("Ambush the patrol at the turn",
                                "The patrol falls, but one horn blast echoes deeper below.",
                                "trapped_gallery", -8, 0, 3, 1, 0),
                        choice("Mark the tunnel for villagers to escape",
                                "You turn a smugglers' route into a rescue path.",
                                "dungeon_ally", 0, 0, 2, 2, 3))));

        add(nodes, new StoryNode(
                "cult_disguise",
                "Chapter Two: Among The Black Wax",
                "You walk beside masked cultists carrying candles, chains, and stolen village tools. They chant that the Crown of Ash will choose a ruler strong enough to silence the realm.",
                SceneType.ROAD,
                false,
                Arrays.asList(
                        choice("Keep the disguise until the dungeon door",
                                "You learn the passphrase and the cult's marching order.",
                                "dungeon_threshold", 0, 0, 0, 5, -1),
                        choice("Free two prisoners from the line",
                                "The prisoners reveal where the miners are held.",
                                "dungeon_ally", -5, 0, 2, 3, 2),
                        choice("Steal the ritual candle from the priest",
                                "Without the candle, the ritual will be weaker when the final fight begins.",
                                "cult_watch", -4, 0, 0, 5, 1))));

        add(nodes, new StoryNode(
                "oath_trial",
                "Chapter Three: Trial Of The First Door",
                "The first door to Emberdeep asks each party member why they came. The stone does not care about titles. It listens for fear, greed, duty, mercy, and the promises people make when no bard is taking notes.",
                SceneType.RUINS,
                false,
                Arrays.asList(
                        choice("Answer with duty to the helpless",
                                "The door opens on a clean path and leaves a lantern mark on your hand.",
                                "dungeon_threshold", 0, -5, 3, 0, 4),
                        choice("Answer with hunger for the truth",
                                "The door opens sideways into a chamber of mirrors.",
                                "mirror_sarcophagus", 0, -8, 0, 4, 2),
                        choice("Lie and claim you want nothing",
                                "The door opens, but the crown hears the lie and remembers your voice.",
                                "trapped_gallery", -6, -4, 0, 3, -3))));

        add(nodes, new StoryNode(
                "barrow_door",
                "Chapter Three: The Barrow Door",
                "Dragonspine Mountain rises above a half-buried door carved with dice, lanterns, and dragonfire. The bronze key fits the lock, but the lock turns only when the party chooses a marching order.",
                SceneType.MOUNTAIN,
                false,
                Arrays.asList(
                        choice("Put the shield and scout in front",
                                "Careful formation gets everyone inside alive.",
                                "dungeon_threshold", -2, 0, 2, 2, 2),
                        choice("Force the lock before the cult arrives",
                                "The door opens with a crack like thunder.",
                                "trapped_gallery", -10, 0, 3, 0, -1),
                        choice("Search the carvings before using the key",
                                "The carvings reveal a hidden route to the oath trial.",
                                "oath_trial", 0, -4, 0, 3, 2))));

        add(nodes, new StoryNode(
                "dungeon_threshold",
                "Chapter Three: Descent Into Emberdeep",
                "Emberdeep smells of wet stone, old smoke, and treasure nobody should touch. Somewhere below, prisoners hammer at the crown chamber while the dragon's chained breath warms the walls.",
                SceneType.CAVE,
                false,
                Arrays.asList(
                        choice("Advance by torchlight and map every turn",
                                "The party avoids the first pit trap and marks a safe return path.",
                                "trapped_gallery", 0, -3, 0, 3, 2),
                        choice("Follow the sound of prisoners working",
                                "The hammering leads toward a guarded mine shaft.",
                                "dungeon_ally", -4, 0, 2, 2, 2),
                        choice("Follow the heat in the stone",
                                "The warmth leads toward the dragon gate.",
                                "dragon_gate", -5, -5, 2, 1, 1))));

        add(nodes, new StoryNode(
                "trapped_gallery",
                "Chapter Three: The Trapped Gallery",
                "A gallery of old heroes stretches ahead, each statue holding a different weapon. The floor tiles show battles that change whenever someone looks away.",
                SceneType.RUINS,
                false,
                Arrays.asList(
                        choice("Solve the tile pattern",
                                "The statues lower their weapons and reveal a safe stair.",
                                "mirror_sarcophagus", 0, -5, 0, 4, 2),
                        choice("Cross fast before the room resets",
                                "You beat the trap by a breath and leave blood on the stone.",
                                "dragon_gate", -12, 0, 3, 1, 0),
                        choice("Use a statue's shield to block the darts",
                                "The shield bears Ashwake's oldest crest.",
                                "dungeon_ally", -6, 0, 2, 2, 1))));

        add(nodes, new StoryNode(
                "mirror_sarcophagus",
                "Chapter Three: The Mirror Sarcophagus",
                "A silver sarcophagus reflects each party member as a ruler, a traitor, a martyr, and a stranger. The oathkeeper inside asks which reflection deserves to reach the crown.",
                SceneType.GRAVEYARD,
                false,
                Arrays.asList(
                        choice("Choose the reflection that protects others",
                                "The oathkeeper gives you a blessing against the crown's command.",
                                "dragon_gate", 0, -8, 2, 1, 4),
                        choice("Choose the reflection no one sees coming",
                                "The oathkeeper gives you a secret route into the ritual balcony.",
                                "cult_watch", 0, -4, 0, 5, 0),
                        choice("Break the mirror and reject the test",
                                "The oathkeeper lets you pass, but the dungeon grows colder.",
                                "crown_chamber", -8, -5, 2, 2, -2))));

        add(nodes, new StoryNode(
                "dungeon_ally",
                "Chapter Four: Prisoners In The Mine",
                "The captured miners and villagers are chained beside a half-open vault. They have heard the cult leader promise that anyone who survives the ritual will kneel to the crown by dawn.",
                SceneType.CAVE,
                false,
                Arrays.asList(
                        choice("Break the chains and arm the prisoners",
                                "The rescued villagers become the loudest part of your army.",
                                "cult_watch", -6, 0, 3, 2, 4),
                        choice("Lead the prisoners out through the marked tunnel",
                                "Saving them now may leave fewer allies for the final battle, but more families survive.",
                                "finale_peace_crossing", -3, 0, 1, 3, 3),
                        choice("Ask what they heard near the vault",
                                "They repeat the exact phrase needed to open the dragon gate.",
                                "dragon_gate", 0, 0, 0, 4, 2))));

        add(nodes, new StoryNode(
                "cult_watch",
                "Chapter Four: Above The Ritual",
                "From a broken balcony, you see the Black-Wax Covenant gathered around the crown chamber. Their leader, Lord Vael, wears a courtier's ring and carries the stolen key like a holy symbol.",
                SceneType.TOWER,
                false,
                Arrays.asList(
                        choice("Strike before Vael finishes the chant",
                                "The ritual breaks into a hard, honest fight.",
                                "cult_ritual", -8, -6, 3, 1, 2),
                        choice("Listen for the missing part of the oath",
                                "Vael cannot command the dragon unless someone willingly gives the crown a name.",
                                "dragon_pact", 0, -6, 0, 4, 3),
                        choice("Steal the key from above",
                                "The key vanishes from Vael's hand, and every cultist looks up.",
                                "crown_chamber", -5, 0, 0, 5, 1))));

        add(nodes, new StoryNode(
                "dragon_gate",
                "Chapter Four: Gate Of Kaelthar",
                "The dragon gate is not locked. It is waiting. Behind it, Kaelthar opens one ember-bright eye and asks whether the party came to free the realm, rule it, or survive it.",
                SceneType.CAVE,
                false,
                Arrays.asList(
                        choice("Speak honestly with the dragon",
                                "Kaelthar respects honesty more than bravery and offers one warning about the crown.",
                                "dragon_pact", 0, -8, 2, 1, 4),
                        choice("Rush past the gate toward the ritual",
                                "The dragon lets you pass, amused by mortal urgency.",
                                "cult_ritual", -6, 0, 3, 1, 0),
                        choice("Search the gate for the crown's weakness",
                                "The runes reveal the crown must be answered, not merely taken.",
                                "crown_chamber", 0, -5, 0, 4, 2))));

        add(nodes, new StoryNode(
                "dragon_pact",
                "Chapter Four: The Dragon's Bargain",
                "Kaelthar was bound centuries ago to stop a tyrant from burning the realm. The dragon offers a bargain: carry the old oath into the crown chamber, but decide afterward whether the dragon remains chained.",
                SceneType.CAVE,
                false,
                Arrays.asList(
                        choice("Promise to judge the crown before using it",
                                "The dragon breathes a coal-bright sigil onto your weapon, spellbook, or bow.",
                                "crown_chamber", 0, -10, 2, 1, 4),
                        choice("Promise to free Kaelthar after Vael falls",
                                "The chains groan, and the dungeon trembles with dangerous hope.",
                                "cult_ritual", -4, -6, 3, 2, 2),
                        choice("Offer yourself as the new keeper of the oath",
                                "The dragon goes silent because some promises are heavier than victory.",
                                "ending_bound", -8, -12, 3, 1, 5))));

        add(nodes, new StoryNode(
                "crown_chamber",
                "Chapter Five: The Crown Of Ash",
                "The Crown of Ash floats above a stone table carved like a battle map. It promises order, victory, revenge, and peace if someone strong enough will only put it on.",
                SceneType.TEMPLE,
                false,
                Arrays.asList(
                        choice("Carry the crown into the open and face Vael",
                                "The crown burns your hands but cannot hide from witnesses.",
                                "cult_ritual", -10, -8, 3, 1, 3),
                        choice("Put the crown on before Vael can take it",
                                "Every voice in the chamber goes quiet because the crown has found a ruler.",
                                "ending_tyrant", 12, -15, 2, 2, -5),
                        choice("Break the battle map under the crown",
                                "The crown loses its script for the future, and all plans become mortal again.",
                                "ending_free", -8, -12, 2, 4, 3))));

        add(nodes, new StoryNode(
                "cult_ritual",
                "Chapter Five: Battle Below Dragonspine",
                "Lord Vael raises the stolen key, the crown answers, and the dungeon becomes a battlefield. Villagers, scouts, oathkeepers, and cultists clash while Kaelthar's chained fire turns every shadow red.",
                SceneType.BATTLEFIELD,
                false,
                Arrays.asList(
                        choice("Defeat Vael in front of both armies",
                                "The covenant breaks when everyone sees its chosen ruler fall.",
                                "ending_champion", -14, -8, 4, 1, 3),
                        choice("Give the freed prisoners the final word",
                                "The people Vael tried to use decide the crown's fate together.",
                                "ending_liberator", -8, -8, 2, 4, 5),
                        choice("Vanish with the key and crown records",
                                "No throne gets the whole truth, and no cult gets the whole map.",
                                "ending_shadow", -5, -6, 0, 5, -1))));

        add(nodes, new StoryNode(
                "finale_warden_roads",
                "Final Act: Lanterns On The Roads",
                "With Vael defeated and the crown contained, the roads around Greenhollow still need a guardian. The party can seal Emberdeep, open it under watch, or teach the realm to guard itself.",
                SceneType.ROAD,
                false,
                Arrays.asList(
                        choice("Swear the roadwarden oath",
                                "Lanterns burn along every road that once led only to fear.",
                                "ending_warden", -4, -6, 3, 2, 4),
                        choice("Train every village to hold its own gate",
                                "Protection becomes a shared duty instead of a lonely title.",
                                "ending_liberator", -3, -4, 2, 3, 4),
                        choice("Close Emberdeep and walk away",
                                "The dungeon sleeps, and so does the part of you that needed applause.",
                                "ending_peace", 6, 0, 0, 2, 2))));

        add(nodes, new StoryNode(
                "finale_tyrant_throne",
                "Final Act: The Crown Answers",
                "The crown fits as if it expected you. Armies wait outside Emberdeep for a ruler strong enough to make the realm safe, even if safety has to be enforced.",
                SceneType.CASTLE,
                false,
                Arrays.asList(
                        choice("Rule Ashwake by iron oath",
                                "The realm becomes orderly, watchful, and afraid to disappoint you.",
                                "ending_tyrant", 14, -6, 3, 1, -5),
                        choice("Rule from the shadows instead",
                                "You let another wear the symbol while you keep the decisions.",
                                "ending_shadow", 8, -4, 0, 5, -3),
                        choice("Take the crown off before it names you",
                                "Power releases your hands reluctantly.",
                                "ending_free", -4, -8, 2, 3, 3))));

        add(nodes, new StoryNode(
                "finale_shadow_network",
                "Final Act: Secrets After Midnight",
                "The covenant is broken, but its tunnels, ledgers, and informants remain. Whoever controls them can prevent the next Vael or become something quieter and worse.",
                SceneType.MARKET,
                false,
                Arrays.asList(
                        choice("Turn the network into a hidden shield",
                                "Dangerous people learn they are being watched by someone better at vanishing.",
                                "ending_shadow", 6, -4, 0, 5, 1),
                        choice("Give the ledgers to the village councils",
                                "Secrets become evidence, and fear becomes testimony.",
                                "ending_liberator", -3, -4, 1, 4, 4),
                        choice("Burn every ledger and disappear",
                                "No one owns the old lies anymore, including you.",
                                "ending_free", 0, -4, 1, 4, 3))));

        add(nodes, new StoryNode(
                "finale_free_unwritten_map",
                "Final Act: The Unwritten Map",
                "The battle map beneath the crown is broken. For the first time, the realm has no prophecy telling it where to go next, and no crown deciding who deserves to lead.",
                SceneType.MOUNTAIN,
                false,
                Arrays.asList(
                        choice("Leave before anyone writes you into law",
                                "The road ahead is dangerous because it is yours.",
                                "ending_free", 0, -4, 2, 3, 4),
                        choice("Stay long enough to help write fair laws",
                                "Freedom starts with arguments that no one is allowed to silence.",
                                "ending_liberator", -4, -4, 1, 4, 5),
                        choice("Choose a quiet life beyond the map",
                                "You trade prophecy for a door, a hearth, and mornings no bard can edit.",
                                "ending_peace", 8, 0, 0, 2, 2))));

        add(nodes, new StoryNode(
                "finale_liberator_square",
                "Final Act: The First Council",
                "In Greenhollow's square, villagers, guards, mages, scouts, and former thieves argue over the crown's prison. It is loud, imperfect, and alive.",
                SceneType.VILLAGE,
                false,
                Arrays.asList(
                        choice("Let every district choose a speaker",
                                "The first vote is messy enough to be real.",
                                "ending_liberator", -3, -3, 2, 4, 5),
                        choice("Keep emergency command for yourself",
                                "Everyone is tired enough to accept a strong hand.",
                                "ending_tyrant", 10, -4, 2, 2, -4),
                        choice("Refuse office and keep adventuring",
                                "Some heroes serve best by not becoming rulers.",
                                "ending_free", 2, 0, 1, 3, 3))));

        add(nodes, new StoryNode(
                "finale_bound_old_road",
                "Final Act: Keeper Of The Oath",
                "The dragon chains accept your name. Emberdeep will hold because you hold it, but the road above continues without waiting for you.",
                SceneType.TEMPLE,
                false,
                Arrays.asList(
                        choice("Bind yourself fully to the seal",
                                "The dungeon sleeps under your heartbeat.",
                                "ending_bound", 6, -10, 3, 0, 5),
                        choice("Share the oath with trusted wardens",
                                "The burden becomes heavy, but no longer lonely.",
                                "ending_warden", 0, -8, 2, 2, 4),
                        choice("Ask Kaelthar to release you from glory",
                                "The dragon grants one small mercy: an ordinary dawn.",
                                "ending_peace", 8, 0, 0, 2, 2))));

        add(nodes, new StoryNode(
                "finale_champion_wall",
                "Final Act: Siege At Ashwake",
                "Vael falls, but the last cult force attacks Castle Ashwake before sunrise. The party reaches the wall as villagers and soldiers look for someone to make the first brave choice.",
                SceneType.BATTLEFIELD,
                false,
                Arrays.asList(
                        choice("Stand on the wall with the banner",
                                "Courage spreads faster than fear.",
                                "ending_champion", -10, -4, 4, 1, 3),
                        choice("Lead a flanking strike through the old road",
                                "The siege breaks when the enemy realizes the dungeon did not break you.",
                                "ending_warden", -8, -4, 3, 3, 3),
                        choice("Save the wounded instead of chasing glory",
                                "Songs miss the point, but families remember.",
                                "ending_peace", 6, 0, 1, 2, 3))));

        add(nodes, new StoryNode(
                "finale_peace_crossing",
                "Final Act: The Road Home",
                "The rescued prisoners reach Greenhollow as dawn turns the mountains gold. The crown is still a problem, but the living are hungry, hurt, and right in front of you.",
                SceneType.ROAD,
                false,
                Arrays.asList(
                        choice("Stay and rebuild Greenhollow",
                                "Peace begins as work no bard can rhyme.",
                                "ending_peace", 12, 0, 0, 2, 3),
                        choice("Teach the village to defend the roads",
                                "The next party will find allies waiting.",
                                "ending_warden", 4, 0, 2, 3, 3),
                        choice("Carry the warning to other realms",
                                "One dungeon is closed. The world is larger than one map.",
                                "ending_free", 0, -3, 2, 3, 3))));

        add(nodes, new StoryNode(
                "finale_fallen_last_breath",
                "Final Act: One Last Save",
                "Darkness closes in under Emberdeep. A healer's voice, a dragon's breath, and the scrape of your party dragging you over stone all arrive at once.",
                SceneType.GRAVEYARD,
                false,
                Arrays.asList(
                        choice("Force yourself back to your feet",
                                "Pain answers first, but your party answers louder.",
                                "ending_champion", 24, -8, 3, 0, 2),
                        choice("Tell the others to finish the quest",
                                "They carry your warning into the final fight.",
                                "fallen", -200, 0, 0, 0, -3),
                        choice("Accept rescue and leave the dungeon alive",
                                "Not every survival becomes a song. Some become a second chance.",
                                "ending_peace", 18, 0, 0, 1, 2))));

        add(nodes, ending("ending_warden", "Roadwarden Of Emberdeep",
                "You become guardian of the roads, shrine keys, and dungeon doors around Dragonspine Mountain. Adventuring parties still come seeking treasure, but now they find warnings, maps, and a lantern burning before the dark."));
        add(nodes, ending("ending_tyrant", "Bearer Of The Crown",
                "You claim the Crown of Ash and force the realm into order. Bandits vanish, cults burn, and villages survive, but every peaceful street knows exactly whom it must fear."));
        add(nodes, ending("ending_shadow", "Master Of The Hidden Ledger",
                "You never sit on a throne. Instead, you inherit the covenant's tunnels, spies, and debts, turning them into a hidden hand that moves before tyrants can rise."));
        add(nodes, ending("ending_free", "The Unwritten Road",
                "You reject the crown, the prophecy, and every easy title. The party's map ends at the mountain pass, but your road continues into blank parchment and honest danger."));
        add(nodes, ending("ending_liberator", "Breaker Of The Crown",
                "You put the crown's fate into the hands of the people it was meant to rule. The realm becomes louder, slower, and far harder to command, which is exactly why it is free."));
        add(nodes, ending("ending_bound", "Keeper Below The Mountain",
                "You bind your name to Emberdeep so the crown and dragon cannot be misused. Above you, seasons turn and roads reopen. Below, your oath holds the dark in place."));
        add(nodes, ending("ending_champion", "Champion Of Ashwake",
                "You defeat Vael, break the siege, and become the hero Ashwake needed. The songs are simpler than the truth, but the people they comfort are real."));
        add(nodes, ending("ending_peace", "Hearth After The Quest",
                "You step away from crowns and commands to help ordinary people rebuild. Some call it a small ending. Anyone who has survived a dungeon knows better."));
        add(nodes, ending("fallen", "Fallen In Emberdeep",
                "Your journey ends beneath Dragonspine Mountain. The party carries your warning forward, and the next lantern lit at the dungeon door bears your name."));

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
