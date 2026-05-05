package story;

import characters.Knight;
import combat.CombatSystem;
import combat.Enemy;
import engine.StoryManager;
import util.InputHandler;
import util.Printer;

public class CadenAct3 {

    private Knight caden;
    private StoryManager story;

    public CadenAct3(Knight caden, StoryManager story) {
        this.caden = caden;
        this.story = story;
    }

    public void play() {
        sceneIntro();
        sceneBreakout();
        sceneAlliesGathered();
        sceneTheSummitEnds();
        sceneFinalChoice();
        sceneBossFight();
        sceneEnding();
    }

    private void sceneIntro() {
        Printer.printTitle("ACT III — REDEMPTION OR REVENGE  |  Caden Ashford");
        Printer.slowPrint("The holding cells of the Iron Hall.");
        Printer.slowPrint("Scheduled for execution in the morning.");
        Printer.slowPrint("The night crawls by like an animal dying.");
        Printer.pause(500);
        Printer.slowPrint("But young knights still believe in something.");
        Printer.slowPrint("And some of them are about to prove it.");
        InputHandler.waitForEnter();
    }

    private void sceneBreakout() {
        Printer.printDivider();
        Printer.slowPrint("The cell door opens. Not an accident. Not a guard's mistake.");
        Printer.slowPrint("A young knight — Ser Aldis, barely old enough to shave.");
        Printer.slowPrint("\"I was at Millfield,\" she says quietly. \"I was at the village burning.\"");
        Printer.slowPrint("\"I didn't speak up then. I'm speaking now.\"");
        Printer.pause(400);
        Printer.slowPrint("She's not alone. Three more knights follow her into the corridor.");
        Printer.slowPrint("All young. All angry. All ready to mutiny against their own order.");
        System.out.println();
        System.out.println("  Do you trust them?");
        System.out.println("  1. Yes. Fully. Lead them toward Veyran.");
        System.out.println("  2. Cautiously. Use them but don't ask for blood.");
        System.out.println("  3. No. Escape alone. Don't drag them into this.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("\"Veyran is in his private chambers,\" Aldis says.");
                Printer.slowPrint("\"He's expecting you. He wants to finish what the execution started.\"");
                Printer.slowPrint("You nod. \"Then let's finish him.\"");
                story.setFlag("caden_fully_trusted_knights", true);
            }
            case 2 -> {
                Printer.slowPrint("\"Get me to Veyran's chambers and you're done. I'll take it from there.\"");
                Printer.slowPrint("Aldis looks like she wants to argue. She doesn't. \"Understood, ser.\"");
                story.setFlag("caden_used_knights_carefully", true);
            }
            case 3 -> {
                Printer.slowPrint("You knock Aldis unconscious — gently. Painless. She won't be blamed.");
                Printer.slowPrint("\"Sorry,\" you whisper. Then you're gone. Alone. Clean. Honest.");
            }
        }

        InputHandler.waitForEnter();

        // Combat during escape
        Printer.slowPrint("Guards block your path — they're looking for you!");
        boolean won = CombatSystem.startCombat(caden, Enemy.ashenHandBandit());
        if (!won) { handleGameOver(); return; }
        caden.heal(15);
        InputHandler.waitForEnter();
    }

    private void sceneAlliesGathered() {
        Printer.printDivider();
        Printer.slowPrint("You reach Veyran's private chambers in the tower.");
        Printer.slowPrint("He's waiting. Like he knew you'd come.");
        Printer.slowPrint("His personal guards are with him — four knights in full plate.");
        Printer.pause(400);
        System.out.println();
        System.out.println("  What do you do?");
        System.out.println("  1. Demand he stand down. Give him one chance to surrender.");
        System.out.println("  2. Attack immediately. No speeches. Just ending.");
        System.out.println("  3. Accuse him publicly if anyone can hear. Make witnesses.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("\"Veyran. Stand down. You're finished.\"");
                Printer.slowPrint("He smiles. \"I've ended a hundred men, Ashford. You're not special.\"");
                Printer.slowPrint("He draws his sword. His guards follow.");
            }
            case 2 -> {
                Printer.slowPrint("You don't waste breath. You attack.");
                Printer.slowPrint("Veyran is fast for an old man. For a moment, it's a real fight.");
            }
            case 3 -> {
                Printer.slowPrint("\"VEYRAN FUNDED THE ASHEN HAND!\" You shout loud enough for the whole tower to hear.");
                Printer.slowPrint("Footsteps in corridors. Heads appearing at doors.");
                Printer.slowPrint("Witnesses. You've made witnesses.");
                Printer.slowPrint("Veyran's expression goes dark. \"Then let's end this privately.\"");
                story.setFlag("caden_made_public_accusation", true);
            }
        }

        InputHandler.waitForEnter();

        // Preliminary combat with guards
        Printer.slowPrint("Veyran's guards attack — let's thin the odds first!");
        boolean won = CombatSystem.startCombat(caden, Enemy.ashenBrute());
        if (!won) { handleGameOver(); return; }
        caden.heal(10);
        InputHandler.waitForEnter();
    }

    private void sceneTheSummitEnds() {
        Printer.printDivider();
        Printer.slowPrint("The Iron Hall is in chaos. The word is spreading.");
        Printer.slowPrint("\"Veyran funded the cult.\" \"Supply crates.\" \"Proof.\"");
        Printer.slowPrint("The younger knights are looking at each other with new eyes.");
        Printer.slowPrint("The older ones are looking away.");
        Printer.pause(500);
        System.out.println("  Veyran is backed into his chambers — nowhere left to run.");
        System.out.println("  How do you face him?");
        System.out.println("  1. With rage. Fight for the forty-three at Millfield.");
        System.out.println("  2. With cold professionalism. You're a soldier. This is a job.");
        System.out.println("  3. With sorrow. You hate what you have to do.");

        int approach = InputHandler.getInt(1, 3);
        switch (approach) {
            case 1 -> {
                Printer.slowPrint("You think about the village. The smoke. The silence after.");
                Printer.slowPrint("Rage floods through you. Clean. Hot. Necessary.");
                caden.setAttackPower(caden.getAttackPower() + 10);
                story.setFlag("caden_fought_with_rage", true);
            }
            case 2 -> {
                Printer.slowPrint("You center yourself. No anger. No mercy. Just duty.");
                Printer.slowPrint("This is what you've trained for. This is what you are.");
            }
            case 3 -> {
                Printer.slowPrint("You look at Veyran and you see a man who was once a hero.");
                Printer.slowPrint("A man who became a monster defending something he thought was necessary.");
                Printer.slowPrint("You hate him. You also pity him. Both are equally true.");
                story.setFlag("caden_fought_with_sorrow", true);
            }
        }
        InputHandler.waitForEnter();
    }

    private void sceneFinalChoice() {
        Printer.printDivider();
        Printer.slowPrint("Veyran stands alone now. His guards are dead or fled.");
        Printer.slowPrint("He's still holding his sword. Still straight-backed. Still certain.");
        System.out.println();
        System.out.println("  How do you end this?");
        System.out.println("  1. Kill him yourself. Justice by your sword.");
        System.out.println("  2. Disarm him. Bring him to trial alive.");
        System.out.println("  3. Give him a choice. Fight or face public trial.");

        int choice = InputHandler.getInt(1, 3);
        switch (choice) {
            case 1 -> {
                Printer.slowPrint("You move to end this. Veyran sees it coming and accepts it.");
                Printer.slowPrint("\"Good soldier,\" he says. \"At least it ends clean.\"");
                story.setFlag("caden_killed_veyran_personally", true);
            }
            case 2 -> {
                Printer.slowPrint("You're going to disable him. Bind him. Drag him before the council alive.");
                Printer.slowPrint("It's harder. But it's righteous. And that matters.");
            }
            case 3 -> {
                Printer.slowPrint("\"You can fight me. You can die here, alone, in the dark.\"");
                Printer.slowPrint("\"Or you can walk out with your hands bound and face trial.\"");
                Printer.slowPrint("\"The kingdom will hear what you've done. Really hear it.\"");
                Printer.slowPrint("Veyran considers this. His certainty cracks slightly.");
            }
        }
        InputHandler.waitForEnter();
    }

    private void sceneBossFight() {
        Printer.printDivider();
        Printer.slowPrint("⚔️   CADEN ASHFORD  vs  LORD MARSHAL VEYRAN");
        Printer.slowPrint("    The man who ordered the burning. The man who gave you a medal.");
        Printer.printDivider();
        InputHandler.waitForEnter();

        boolean won = CombatSystem.startCombat(caden, Enemy.bossVeyran());
        if (!won) { handleGameOver(); return; }
    }

    private void sceneEnding() {
        Printer.printDivider();
        Printer.slowPrint("Veyran falls. The Ashen Hand's funding network dies with him.");
        Printer.pause(500);

        if (story.getFlag("caden_killed_veyran_personally")) {
            Printer.slowPrint("You stand over his body. Your sword dripping.");
            Printer.slowPrint("The young knights see. The council members see.");
            Printer.slowPrint("They see a killer. Not a justice-seeker. A vigilante.");
            Printer.pause(500);
            Printer.slowPrint("The Iron Vow brands you a criminal. Officially, formally, permanently.");
            Printer.slowPrint("But the younger knights follow you out of the Hall anyway.");
            Printer.slowPrint("You lead them south, across the border, into exile.");
            Printer.pause(400);
            Printer.slowPrint("You're no longer a knight. You're just Caden now. Free, finally free,");
            Printer.slowPrint("but forever outside everything you've known.");
            Printer.printDivider();
            Printer.printBox("THE FREE MAN");
            Printer.printBox("Some debts can only be paid in exile.");
        } else {
            Printer.slowPrint("Veyran is bound. Brought before the full council.");
            Printer.slowPrint("You present the evidence. The crates. The letters. The witnesses.");
            Printer.slowPrint("The court finds him guilty. He's imprisoned. But alive. Allowed to think.");
            Printer.pause(500);
            Printer.slowPrint("You're offered your old rank back. You refuse.");
            Printer.slowPrint("You ask instead for something new: an independent post.");
            Printer.slowPrint("Arbiter. Watchdog. The thing the Iron Vow always needed — accountability.");
            Printer.pause(400);
            Printer.slowPrint("The younger knights respect you for it.");
            Printer.slowPrint("The older ones resent you for it.");
            Printer.slowPrint("You're at peace with both reactions.");
            Printer.printDivider();
            Printer.printBox("THE ARBITER");
            Printer.printBox("Honor isn't a title. It's a choice, made again every day.");
        }

        Printer.printDivider();
        Printer.slowPrint("═══════════════════════════════════════════════════════════════");
        Printer.slowPrint("              CADEN ASHFORD — STORY COMPLETE");
        Printer.slowPrint("═══════════════════════════════════════════════════════════════");
        InputHandler.waitForEnter();
    }

    private void handleGameOver() {
        Printer.slowPrint("Veyran kills Caden Ashford. The corruption spreads. The order crumbles.");
        System.exit(0);
    }
}