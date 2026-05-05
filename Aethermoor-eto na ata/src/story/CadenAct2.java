package story;

import characters.Knight;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class CadenAct2 {

    private Knight caden;
    private StoryManager story;

    public CadenAct2(Knight caden, StoryManager story) {
        this.caden = caden;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneValdenmereArrival();
        sceneCampfire();
        sceneIronVowSummit();
        sceneConfrontation();
        sceneArrest();
        sceneActEnd();
    }

    // ─── SCENE 1: Arriving with Purpose ───────────────────────────────────────
    private void sceneIntro() {
        Printer.printTitle("ACT II — THE RECKONING  |  Caden Ashford");
        Printer.slowPrint("You've had a lot of time to think on the road to Valdenmere.");
        Printer.slowPrint("You've been running for two years. From the order. From the medal.");
        Printer.slowPrint("From the memory of smoke rising over a village that didn't deserve it.");
        Printer.pause(500);
        Printer.slowPrint("Running ends today.");
        Printer.slowPrint("The Iron Vow Summit starts in three days.");
        Printer.slowPrint("Every Marshal in the kingdom under one roof.");
        Printer.slowPrint("Including Veyran.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 2: Arriving in Valdenmere ──────────────────────────────────────
    private void sceneValdenmereArrival() {
        Printer.printDivider();
        Printer.slowPrint("Valdenmere is louder than you expected.");
        Printer.slowPrint("Iron Vow banners everywhere — black and silver, snapping in the grey wind.");
        Printer.slowPrint("Soldiers on every corner. The Summit has the city locked down.");
        Printer.pause(400);
        Printer.slowPrint("Your wanted poster is on three separate walls before you reach the market.");
        Printer.slowPrint("You pull your hood lower.");
        System.out.println();
        System.out.println("  You need a place to lie low and gather information. Where do you go?");
        System.out.println("  1. A rundown tavern in the outer district — soldiers rarely drink cheap.");
        System.out.println("  2. The refugee camp outside the walls — less scrutiny there.");
        System.out.println("  3. An old contact — a former knight who owes you a favor.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("The Ashen Tap. Appropriate name for these times.");
                Printer.slowPrint("The barkeep doesn't look at faces. You order the cheapest thing on offer.");
                Printer.slowPrint("Two hours of listening gives you the Summit's schedule.");
                Printer.slowPrint("Veyran speaks on the second day. A ceremony. A commendation.");
                Printer.slowPrint("Of course he does.");
                story.setFlag("caden_knows_summit_schedule", true);
            }
            case 2 -> {
                Printer.slowPrint("The camp is crowded, desperate, and completely off the Iron Vow's radar.");
                Printer.slowPrint("A healer there — a woman in plain robes — patches a cut on your arm.");
                Printer.slowPrint("She doesn't ask questions. You don't offer answers. Mutual respect.");
                Printer.slowPrint("You get a cot. A hot meal. A day to plan.");
                story.setFlag("caden_stayed_at_camp", true);
            }
            case 3 -> {
                Printer.slowPrint("Ser Dovin Marsh. Retired. Living quietly in the artisan district.");
                Printer.slowPrint("He opens the door, sees your face, and sighs.");
                Printer.slowPrint("\"I figured you'd show up eventually,\" he says. \"Come in.\"");
                Printer.slowPrint("He tells you everything he knows about the Summit's inner workings.");
                Printer.slowPrint("He also tells you there are five other deserter warrants going out this week.");
                Printer.slowPrint("\"Veyran's cleaning house,\" Dovin says grimly. \"Before someone talks.\"");
                story.setFlag("caden_knows_purge_incoming", true);
            }
        }
        InputHandler.waitForEnter();

        // Street combat — patrol recognizes him
        Printer.slowPrint("An Iron Vow patrol spots your face against the wanted board. They move toward you!");
        boolean won = CombatSystem.startCombat(caden, Enemy.ashenHandBandit());
        if (!won) { handleGameOver(); return; }
        caden.restoreMana(10);
        Printer.slowPrint("You slip away before the shout brings more. That was too close.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 3: The Campfire — Meeting Eryn ─────────────────────────────────
    private void sceneCampfire() {
        Printer.printDivider();
        Printer.slowPrint("The night before the Summit, you camp at a waystation on the city's edge.");
        Printer.slowPrint("A woman is already there. Thin. Sharp eyes. Hands that have the calluses of someone");
        Printer.slowPrint("who used to work with something delicate — instruments or spells, you can't tell.");
        Printer.pause(300);
        System.out.println("  Do you acknowledge her?");
        System.out.println("  1. Sit down. Neutral. See what happens.");
        System.out.println("  2. Nod and leave her alone — you've got enough to think about.");
        System.out.println("  3. Strike up conversation directly.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You sit on the opposite side. The fire crackles between you.");
                Printer.slowPrint("Eventually: \"Heading to Valdenmere?\" you ask.");
                Printer.slowPrint("\"Aren't you?\" she says. Fair point.");
            }
            case 2 -> {
                Printer.slowPrint("You both sit in comfortable silence for a while.");
                Printer.slowPrint("Eventually she speaks first. \"You look like someone with a plan they're not sure about.\"");
                Printer.slowPrint("You almost laugh. \"That obvious?\"  \"That obvious,\" she confirms.");
            }
            case 3 -> {
                Printer.slowPrint("\"You look like you're heading toward something dangerous,\" you say.");
                Printer.slowPrint("\"I am,\" she replies. \"You?\" \"Same,\" you say.");
                Printer.slowPrint("A pause. \"At least we're consistent,\" she offers.");
            }
        }

        Printer.pause(400);
        Printer.slowPrint("Her name is Eryn. She doesn't say what she used to do.");
        Printer.slowPrint("You don't say what you used to do. Some things are obvious enough without words.");
        Printer.pause(300);
        System.out.println();
        System.out.println("  She mentions she's tracking someone powerful in the city. Do you share your situation?");
        System.out.println("  1. Yes — tell her about Veyran and the cult connection.");
        System.out.println("  2. Keep it vague. Sharing information is a risk.");
        System.out.println("  3. Ask about her target first.");

        int share = InputHandler.getInt(1, 3);
        switch (share) {
            case 1 -> {
                Printer.slowPrint("You tell her about Veyran. The supply crates. The Ashen Hand.");
                Printer.slowPrint("She goes very still when you mention the name Dael.");
                Printer.slowPrint("\"Your Veyran and my Dael,\" she says slowly. \"They're working together.\"");
                Printer.slowPrint("\"The Ashen Hand is the muscle. The rogue Circle is the power.\"");
                Printer.slowPrint("You sit with that for a long moment.");
                story.setFlag("caden_knows_about_dael", true);
            }
            case 2 -> {
                Printer.slowPrint("\"Iron Vow business,\" you say. She accepts that without pushing.");
                Printer.slowPrint("You're both quiet for a while. It's a comfortable quiet, oddly enough.");
            }
            case 3 -> {
                Printer.slowPrint("She tells you about the Spire. A man called Dael. Stage Two.");
                Printer.slowPrint("You tell her about Veyran and the supply crates.");
                Printer.slowPrint("The pieces click into place between you like a door swinging open.");
                Printer.slowPrint("\"They're working together,\" you both say at the same time.");
                story.setFlag("caden_knows_about_dael", true);
            }
        }

        Printer.pause(400);
        Printer.slowPrint("You both head for Valdenmere at dawn. Not together. Just the same direction.");
        story.setFlag("caden_met_eryn", true);
        InputHandler.waitForEnter();
    }

    // ─── SCENE 4: The Iron Vow Summit ─────────────────────────────────────────
    private void sceneIronVowSummit() {
        Printer.printDivider();
        Printer.slowPrint("The Summit is held in Valdenmere's Iron Hall — the largest building in the city.");
        Printer.slowPrint("Banners, ceremony, polished armor. Everything the Iron Vow does best.");
        Printer.slowPrint("And somewhere in there, Veyran is being celebrated.");
        System.out.println();
        System.out.println("  How do you get inside?");
        System.out.println("  1. Steal a uniform from a guard and walk in.");
        System.out.println("  2. Use the servant's entrance — deliveries are still running.");
        System.out.println("  3. Climb to the second floor balcony — you know this building's exterior.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You find a guard alone near the stables. A quick disarm and he's zip-tied to a post.");
                Printer.slowPrint("His uniform is tight in the shoulders but it works.");
                Printer.slowPrint("You walk through the front door. Two soldiers salute you. You salute back.");
                Printer.slowPrint("Old habits.");
                story.setFlag("caden_used_uniform", true);
            }
            case 2 -> {
                Printer.slowPrint("Bread delivery. You lift a crate, keep your head down, walk in.");
                Printer.slowPrint("The kitchen staff don't even look up.");
                Printer.slowPrint("You leave the bread. Take a servant's tunic. Disappear into the crowd.");
            }
            case 3 -> {
                Printer.slowPrint("The Iron Hall's exterior stonework is old and grippy.");
                Printer.slowPrint("You're up and over the balcony railing in ninety seconds.");
                Printer.slowPrint("You are absolutely too old for this. It works perfectly.");
                story.setFlag("caden_climbed_balcony", true);
            }
        }

        Printer.pause(400);
        Printer.slowPrint("The ceremony hall is packed. Every Marshal. Every senior knight.");
        Printer.slowPrint("And at the front, gleaming in polished black plate:");
        Printer.slowPrint("Lord Marshal Veyran.");
        Printer.slowPrint("Older than you remember. More decorated. Just as certain of himself.");
        Printer.pause(500);
        Printer.slowPrint("A speaker reads his commendations. You listen to each one.");
        Printer.slowPrint("Campaigns he won. Villages he 'pacified'. Sacrifices he made for the kingdom.");
        Printer.pause(400);
        Printer.slowPrint("You know what 'pacified' means when Veyran says it.");
        Printer.slowPrint("You know exactly what was sacrificed.");
        InputHandler.waitForEnter();

        // Combat: guards spot him
        Printer.slowPrint("A guard recognizes you beneath the disguise. He shouts. Two more converge!");
        boolean won = CombatSystem.startCombat(caden, Enemy.ashenHandBandit());
        if (!won) { handleGameOver(); return; }
        caden.heal(10);
        Printer.slowPrint("You take them down quietly. The ceremony continues. No one noticed. Yet.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 5: The Private Confrontation ───────────────────────────────────
    private void sceneConfrontation() {
        Printer.printDivider();
        Printer.slowPrint("You intercept Veyran in the corridor between the ceremony hall and his private chambers.");
        Printer.slowPrint("He's alone. His guards are still at the reception.");
        Printer.slowPrint("He sees your face and stops walking.");
        Printer.pause(500);
        Printer.slowPrint("He doesn't reach for a weapon.");
        Printer.slowPrint("He doesn't call for help.");
        Printer.slowPrint("He just looks at you the way you look at something you knew would eventually happen.");
        Printer.pause(400);
        Printer.slowPrint("\"Ashford,\" he says. \"I wondered when.\"");
        System.out.println();
        System.out.println("  How do you open?");
        System.out.println("  1. \"You knew the village was innocent. You ordered it anyway.\"");
        System.out.println("  2. \"You're funding the Ashen Hand. I have proof.\"");
        System.out.println("  3. Say nothing. Let him fill the silence.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("Veyran doesn't even flinch. \"Forty-three people,\" he says.");
                Printer.slowPrint("\"Weighed against the three thousand in the city those cultists would have reached.");
                Printer.slowPrint("Mathematics, Ashford. Not malice.\"");
                Printer.slowPrint("\"They were innocent,\" you say.");
                Printer.slowPrint("\"They were acceptable,\" he replies.");
            }
            case 2 -> {
                Printer.slowPrint("Now he reacts — a flicker. Quickly controlled.");
                Printer.slowPrint("\"Proof is a generous word for whatever a deserter scavenged from a cult camp.\"");
                Printer.slowPrint("\"The Iron Vow crest on those supply crates isn't generous. It's specific.\"");
                Printer.slowPrint("His jaw tightens. Just slightly. That's enough.");
                story.setFlag("caden_confronted_with_crates", true);
            }
            case 3 -> {
                Printer.slowPrint("The silence stretches. Veyran fills it.");
                Printer.slowPrint("\"You think I don't know what I've done? I know exactly what I've done.\"");
                Printer.slowPrint("\"Every single thing. And I'd do it again.\"");
                Printer.slowPrint("Something about hearing it said plainly is worse than any denial could be.");
            }
        }

        Printer.pause(500);
        Printer.slowPrint("\"The Greying was a wound,\" Veyran says, finding his footing. \"A wound needs cauterizing.\"");
        Printer.slowPrint("\"The Ashen Hand understands that. They're rough, yes. Cruel, sometimes.\"");
        Printer.slowPrint("\"But they're willing to do what soft men won't. What the kingdom needs.\"");
        Printer.pause(400);
        Printer.slowPrint("\"The Iron Vow swore to protect Aethermoor,\" you say.");
        Printer.slowPrint("\"And I am protecting it,\" he replies. \"From itself.\"");
        Printer.pause(300);
        System.out.println();
        System.out.println("  He's made his position clear. What do you do?");
        System.out.println("  1. Demand he stand down publicly — walk out of the Summit and confess.");
        System.out.println("  2. Tell him you have enough to bring him down with or without him.");
        System.out.println("  3. Try to appeal to the man he used to be.");

        int action = InputHandler.getInt(1, 3);
        switch (action) {
            case 1 -> {
                Printer.slowPrint("\"Walk back in there and tell them what you've done. Every word.\"");
                Printer.slowPrint("Veyran looks at you for a long moment.");
                Printer.slowPrint("Then he smiles and calls for his guards.");
            }
            case 2 -> {
                Printer.slowPrint("\"I have the crates, the letters, and a lieutenant who talks.\"");
                Printer.slowPrint("\"You can come quietly or the whole Summit hears it from me personally.\"");
                Printer.slowPrint("Veyran's expression doesn't change. He calls for his guards anyway.");
            }
            case 3 -> {
                Printer.slowPrint("\"You taught me that the Vow's strength was its honor,\" you say.");
                Printer.slowPrint("\"What happened to that?\"");
                Printer.slowPrint("Something crosses his face — old and tired and buried.");
                Printer.slowPrint("Then his guards arrive and it's gone.");
                story.setFlag("caden_appealed_to_veyran", true);
            }
        }
        InputHandler.waitForEnter();
    }

    // ─── SCENE 6: The Arrest ──────────────────────────────────────────────────
    private void sceneArrest() {
        Printer.printDivider();
        Printer.slowPrint("Six guards. Fully armored. Between you and every exit.");
        Printer.slowPrint("Veyran stands behind them, hands clasped, composed.");
        Printer.slowPrint("\"Caden Ashford. Wanted deserter. Caught infiltrating a protected military summit.\"");
        Printer.slowPrint("\"The execution will be public. Tomorrow morning. Pour encourager les autres.\"");
        Printer.pause(500);

        System.out.println("  Six guards. You can fight — but not all six.");
        System.out.println("  1. Fight your way out. Take the damage, get free.");
        System.out.println("  2. Surrender. Bide your time. Look for another angle.");
        System.out.println("  3. Make a scene — shout everything you know so the whole Summit hears.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You take two guards down before the numbers overwhelm you.");
                boolean won = CombatSystem.startCombat(caden, Enemy.ashenBrute());
                if (!won) { handleGameOver(); return; }
                Printer.slowPrint("You break through — barely. You run. The city behind you erupts in pursuit.");
                story.setFlag("caden_fought_free", true);
            }
            case 2 -> {
                Printer.slowPrint("You lower your sword. Let them take it.");
                Printer.slowPrint("Veyran looks almost disappointed. He wanted you to fight.");
                Printer.slowPrint("You're put in the Summit's holding cells. Scheduled for morning.");
                Printer.slowPrint("You spend the night thinking. There are younger knights here.");
                Printer.slowPrint("Some of them still believe in something. You saw their faces during the ceremony.");
                story.setFlag("caden_was_arrested", true);
            }
            case 3 -> {
                Printer.slowPrint("\"VEYRAN IS FUNDING THE ASHEN HAND!\" Your voice fills the corridor.");
                Printer.slowPrint("Doors open. Heads appear. The ceremony hall goes quiet.");
                Printer.slowPrint("Veyran's composure cracks — just for a second — and everyone sees it.");
                Printer.slowPrint("Guards tackle you. But fifty people heard. The seed is planted.");
                story.setFlag("caden_made_public_accusation", true);
                story.setFlag("caden_was_arrested", true);
            }
        }
        InputHandler.waitForEnter();
    }

    // ─── SCENE 7: Act End ─────────────────────────────────────────────────────
    private void sceneActEnd() {
        Printer.printDivider();

        if (story.getFlag("caden_was_arrested")) {
            Printer.slowPrint("The holding cell is cold. The walls are iron Vow stone — familiar.");
            Printer.slowPrint("You've slept in worse places.");
            Printer.pause(400);
            Printer.slowPrint("Late that night, a slot in the door opens. A pair of eyes. Young.");
            Printer.slowPrint("\"Ser Ashford.\" Not Deserter. Not prisoner. Ser.");
            Printer.slowPrint("\"I was at Millfield,\" the voice says quietly.");
            Printer.slowPrint("\"I know what Veyran ordered. I know what happened.\"");
            Printer.slowPrint("\"We're not all like him.\"");
            Printer.pause(400);
            Printer.slowPrint("The cell door opens.");
        } else {
            Printer.slowPrint("You run until the pursuit thins. An alley. A locked door. Darkness.");
            Printer.pause(400);
            Printer.slowPrint("Someone behind you in the dark: a young knight's voice.");
            Printer.slowPrint("\"Don't turn around. I heard what you said in the corridor.\"");
            Printer.slowPrint("\"There are others who want to see this done right.\"");
            Printer.slowPrint("\"But we need proof that holds in front of the full council. Not just you saying it.\"");
        }

        Printer.pause(500);
        Printer.slowPrint("The Summit ends in two days.");
        Printer.slowPrint("Veyran will present his final proposal then — whatever it is.");
        Printer.slowPrint("You need to be in that room.");

        Printer.printDivider();
        Printer.printBox("ACT II COMPLETE — THE RECKONING");
        Printer.printBox("Caden Ashford prepares for a final confrontation.");
        if (story.getFlag("caden_made_public_accusation"))
            Printer.printBox("★ Your accusation was heard. The seed of doubt is planted.");
        if (story.getFlag("caden_knows_about_dael"))
            Printer.printBox("★ You know Veyran and Dael are connected. The conspiracy is bigger than one man.");
        if (story.getFlag("caden_appealed_to_veyran"))
            Printer.printBox("★ Something flickered in Veyran when you spoke of honor. Remember that.");
        Printer.printDivider();
        InputHandler.waitForEnter();
        story.advanceAct();
    }

    private void handleGameOver() {
        Printer.slowPrint("The reckoning finds Caden Ashford wanting. The road ends here.");
        System.exit(0);
    }
}
