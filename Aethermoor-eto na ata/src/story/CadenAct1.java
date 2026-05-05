package story;

import characters.Knight;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class CadenAct1 {

    private Knight caden;
    private StoryManager story;

    public CadenAct1(Knight caden, StoryManager story) {
        this.caden = caden;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneVillage();
        sceneBanditCamp();
        sceneWantedPoster();
        sceneOnTheRoad();
        sceneActEnd();
    }

    // ─── SCENE 1: The Wanderer Arrives ────────────────────────────────────────
    private void sceneIntro() {
        Printer.printTitle("ACT I — THE WANDERER  |  Caden Ashford");
        Printer.slowPrint("You've been walking for six days.");
        Printer.slowPrint("No destination. That's the point.");
        Printer.slowPrint("The Iron Vow's deserter bounty sits around your neck like a stone,");
        Printer.slowPrint("but bounty hunters need to know your face first.");
        Printer.slowPrint("You keep your hood up. You keep moving.");
        Printer.pause(500);
        Printer.slowPrint("The village of Millhaven appears through the treeline.");
        Printer.slowPrint("Small. Quiet. The kind of place that should be safe.");
        Printer.slowPrint("You hear screaming before you see the smoke.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 2: Village Under Attack ────────────────────────────────────────
    private void sceneVillage() {
        Printer.printDivider();
        Printer.slowPrint("Three men in dark robes are dragging villagers into the square.");
        Printer.slowPrint("Ashen Hand cultists. You recognize the symbol — a grey hand on black cloth.");
        Printer.slowPrint("They're demanding the village's stored grain. \"A tithe to the Greying,\" one shouts.");
        Printer.pause(300);
        Printer.slowPrint("An old farmer spits at their feet. One of the cultists raises a blade.");
        System.out.println();
        System.out.println("  What do you do?");
        System.out.println("  1. Step in immediately — draw your sword.");
        System.out.println("  2. Circle around and flank them tactically.");
        System.out.println("  3. Call out from the treeline to distract them.");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("Your sword is out before the thought finishes forming.");
                Printer.slowPrint("Some things the body remembers even when the mind wants to forget.");
                story.setFlag("caden_acts_on_instinct", true);
            }
            case 2 -> {
                Printer.slowPrint("Old habits. You sweep wide, emerge behind them.");
                Printer.slowPrint("The farmer sees you coming. He's smart enough to keep them talking.");
                story.setFlag("caden_uses_tactics", true);
            }
            case 3 -> {
                Printer.slowPrint("\"HEY!\" Your voice carries. They spin toward you.");
                Printer.slowPrint("The old man uses the moment to shove the nearest cultist into the well.");
                Printer.slowPrint("Good man.");
            }
        }

        Printer.slowPrint("\nCOMBAT: Ashen Hand Cultists attack!");
        InputHandler.waitForEnter();

        boolean won1 = CombatSystem.startCombat(caden, Enemy.ashenHandBandit());
        if (!won1) { handleGameOver(); return; }
        caden.restoreMana(10);
        boolean won2 = CombatSystem.startCombat(caden, Enemy.ashenHandBandit());
        if (!won2) { handleGameOver(); return; }

        Printer.slowPrint("The last cultist drops his blade and runs. You let him go.");
        Printer.slowPrint("You're not in the business of executions. Not anymore.");
        InputHandler.waitForEnter();
    }



    // ─── SCENE 4: Investigating the Cultists ──────────────────────────────────
    private void sceneBanditCamp() {
        Printer.printDivider();
        Printer.slowPrint("The villagers offer coin. Food. A warm bed.");
        Printer.slowPrint("You refuse all of it.");
        System.out.println();
        System.out.println("  The village elder asks if you'll stay to protect them.");
        System.out.println("  1. \"I'll stay the night.\" (Rest and recover HP)");
        System.out.println("  2. \"I'm just passing through.\" Search the cultists first.");
        System.out.println("  3. Ask about the Ashen Hand — who are they, where do they camp?");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You sleep on a real bed for the first time in weeks.");
                Printer.slowPrint("You dream of a burning village. A medal in your hand.");
                Printer.slowPrint("You wake before dawn, already moving.");
                caden.heal(30);
            }
            case 2 -> {
                Printer.slowPrint("You crouch over the fallen cultists. Robes. A symbol. And a map.");
                Printer.slowPrint("A location marked: Fort Greyveil. Two days north.");
                story.setFlag("caden_found_map", true);
            }
            case 3 -> {
                Printer.slowPrint("The elder's face goes pale. \"They've been taxing villages for months.\"");
                Printer.slowPrint("\"They call the Greying a gift. They want more of it.\"");
                Printer.slowPrint("\"They camp at the old fort — Greyveil — but no one who goes there comes back.\"");
                story.setFlag("caden_knows_about_greyveil", true);
            }
        }

        Printer.pause(400);
        Printer.slowPrint("Among the cultists' belongings: a crude iron symbol. A grey hand.");
        Printer.slowPrint("And beneath it — folded tight — a letter bearing the Iron Vow's seal.");
        Printer.pause(500);
        Printer.slowPrint("Your old seal. Your old order.");
        Printer.slowPrint("The letter reads: \"SUPPLIES DELIVERED AS ARRANGED. — V\"");
        Printer.pause(600);
        Printer.slowPrint("V.");
        Printer.slowPrint("You know that initial. You've known it your whole career.");
        Printer.slowPrint("Veyran.");
        InputHandler.waitForEnter();
    }

    // ─── SCENE 5: The Wanted Poster ───────────────────────────────────────────
    private void sceneWantedPoster() {
        Printer.printDivider();
        Printer.slowPrint("On the road north to Greyveil, you pass a post board.");
        Printer.slowPrint("Nailed at the top: a charcoal sketch. Familiar jaw. Familiar scar.");
        Printer.pause(400);
        Printer.slowPrint("Your face.");
        Printer.slowPrint("WANTED — CADEN ASHFORD. DESERTER. REWARD: 500 GOLD.");
        Printer.pause(300);
        System.out.println();
        System.out.println("  A merchant nearby squints at the poster, then at you.");
        System.out.println("  1. Pull your hood lower and keep walking.");
        System.out.println("  2. Tear the poster down casually.");
        System.out.println("  3. Talk to the merchant — feel him out.");

        int choice = InputHandler.getInt(1, 3);

        switch (choice) {
            case 1 -> {
                Printer.slowPrint("The merchant says nothing. Either he doesn't recognize you, or he doesn't care.");
                Printer.slowPrint("You don't look back to find out which.");
            }
            case 2 -> {
                Printer.slowPrint("You rip it down in one smooth motion. The merchant watches.");
                Printer.slowPrint("\"Lot of those going up lately,\" he says. \"Iron Vow's hunting someone hard.\"");
                Printer.slowPrint("\"Must've done something really bad. Or really good.\"");
            }
            case 3 -> {
                Printer.slowPrint("\"What do you know about this Ashford fellow?\" you ask.");
                Printer.slowPrint("The merchant shrugs. \"Decorated knight. Walked off three years ago.\"");
                Printer.slowPrint("\"Some say he's a coward. Some say he saw something he shouldn't have.\"");
                Printer.slowPrint("He looks at you steadily. \"Some say the Vow did something to deserve losing him.\"");
                story.setFlag("caden_people_suspect_vow", true);
            }
        }
        InputHandler.waitForEnter();
    }

    // ─── SCENE 6: Road to Greyveil Combat ─────────────────────────────────────
    private void sceneOnTheRoad() {
        Printer.printDivider();
        Printer.slowPrint("The road north narrows into a trail, then into mud.");
        Printer.slowPrint("Fort Greyveil looms ahead — a broken silhouette against the grey sky.");
        Printer.slowPrint("It was a proud fortress once. Now its walls are covered in that grey hand symbol.");
        Printer.pause(300);
        Printer.slowPrint("Two Ashen Brutes guard the gate. Behind them, you can hear chanting.");
        InputHandler.waitForEnter();

        boolean won = CombatSystem.startCombat(caden, Enemy.ashenBrute());
        if (!won) { handleGameOver(); return; }
        caden.restoreMana(10);
        caden.heal(15);

        Printer.slowPrint("You push through the gate. Inside: crates. Iron Vow crates.");
        Printer.slowPrint("Stamped with the official seal. Full of weapons, food, and Greying-corrupted relics.");
        Printer.pause(400);
        Printer.slowPrint("Veyran isn't just ignoring the Ashen Hand.");
        Printer.slowPrint("He's funding them.");
        InputHandler.waitForEnter();

        // Final combat of the act
        Printer.slowPrint("A cultist lieutenant confronts you inside the fort!");
        boolean won2 = CombatSystem.startCombat(caden, Enemy.ashenHandBandit());
        if (!won2) { handleGameOver(); return; }

        Printer.slowPrint("The lieutenant, wounded on the ground, laughs through bloody teeth.");
        Printer.slowPrint("\"You think exposing Veyran will matter? He IS the Vow now.\"");
        Printer.slowPrint("\"No one will believe a deserter over a Lord Marshal.\"");
        Printer.pause(300);
        System.out.println();
        System.out.println("  What do you do?");
        System.out.println("  1. Leave him alive. He might be useful as a witness.");
        System.out.println("  2. Leave him. He's not worth it.");

        int choice = InputHandler.getInt(1, 2);
        if (choice == 1) {
            Printer.slowPrint("You bind his hands with rope and drag him to the nearest town.");
            Printer.slowPrint("He'll live. Someone might actually listen to what he has to say.");
            story.setFlag("caden_kept_witness", true);
        } else {
            Printer.slowPrint("You walk away. His laughter follows you out the gate.");
        }

        InputHandler.waitForEnter();
    }

    // ─── SCENE 7: Act End ─────────────────────────────────────────────────────
    private void sceneActEnd() {
        Printer.printDivider();
        Printer.slowPrint("You stand outside Fort Greyveil in the fading light.");
        Printer.slowPrint("You have evidence. You have a name. You have nothing to lose.");
        Printer.pause(400);
        Printer.slowPrint("The Iron Vow Summit is in Valdenmere. Every Marshal in the kingdom.");
        Printer.slowPrint("Veyran will be there — being celebrated, probably.");
        Printer.slowPrint("Getting a medal, like he gave you.");
        Printer.pause(500);
        Printer.slowPrint("You think about the forty-three people in that village.");
        Printer.slowPrint("You think about the medal you threw in a river.");
        Printer.slowPrint("You start walking toward Valdenmere.");
        Printer.printDivider();
        Printer.printBox("ACT I COMPLETE — THE WANDERER");
        Printer.printBox("Caden Ashford marches toward a reckoning.");

        if (story.getFlag("caden_kept_witness")) {
            Printer.printBox("★ You kept the witness. This will matter.");
        }
        Printer.printDivider();
        InputHandler.waitForEnter();
        story.advanceAct();
    }

    private void handleGameOver() {
        Printer.slowPrint("The road ends here for Caden Ashford.");
        System.exit(0);
    }
}
