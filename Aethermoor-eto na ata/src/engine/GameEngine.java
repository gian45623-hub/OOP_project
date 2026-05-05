package engine;

import characters.*;
import characters.Character;
import story.*;
import util.InputHandler;
import util.Printer;

public class GameEngine {

    private Character player;
    private StoryManager storyManager;

    public GameEngine(Character player, StoryManager storyManager) {
        this.player = player;
        this.storyManager = storyManager;
    }

    public void start() {
        showCharacterIntro();
        runStory();
    }

    private void showCharacterIntro() {
        Printer.printDivider();
        Printer.slowPrint(player.getClassDescription());
        Printer.printDivider();
        Printer.slowPrint("Your journey in Aethermoor begins...");
        InputHandler.waitForEnter();
    }

    private void runStory() {
        if (player instanceof Mage mage) {
            runMageStory(mage);
        } else if (player instanceof Knight knight) {
            runKnightStory(knight);
        } else if (player instanceof Priest priest) {
            runPriestStory(priest);
        }
    }

    private void runMageStory(Mage mage) {
        // Act 1
        ErynAct1 act1 = new ErynAct1(mage, storyManager);
        act1.play();

        // Act 2
        ErynAct2 act2 = new ErynAct2(mage, storyManager);
        act2.play();

        // Act 3
        ErynAct3 act3 = new ErynAct3(mage, storyManager);
         act3.play();

        showMageEnding(mage);
    }

    private void runKnightStory(Knight knight) {
        CadenAct1 act1 = new CadenAct1(knight, storyManager);
        act1.play();

        CadenAct2 act2 = new CadenAct2(knight, storyManager);
        act2.play();

        CadenAct3 act3 = new CadenAct3(knight, storyManager);
        act3.play();
        showKnightEnding(knight);
    }

    private void runPriestStory(Priest priest) {
        SoliaAct1 act1 = new SoliaAct1(priest, storyManager);
        act1.play();

        SoliaAct2 act2 = new SoliaAct2(priest, storyManager);
        act2.play();

        SoliaAct3 act3 = new SoliaAct3(priest, storyManager);
        act3.play();

        showPriestEnding(priest);
    }

    // ─── Ending Scenes ────────────────────────────────────────────────────────

    private void showMageEnding(Mage mage) {
        Printer.printTitle("EPILOGUE — ERYN VOSS");
        String ending = storyManager.determineMageEnding();
        if (ending.equals("BLIND")) {
            Printer.slowPrint("Eryn defeats Dael. The raw power tears through her.");
            Printer.slowPrint("She wakes in darkness. Permanent. She will never cast again.");
            Printer.slowPrint("She writes down everything she knows. Every truth. Every name.");
            Printer.slowPrint("She can't see the pages. She writes anyway.");
            Printer.slowPrint("A new Arcane Circle rises without her. Built on her words.");
            Printer.pause(500);
            Printer.printBox("ENDING: THE BLIND SCHOLAR");
            Printer.printBox("She gave her sight for the truth. It was enough.");
        } else {
            Printer.slowPrint("Eryn defeats Dael — not alone, but with those who believed her.");
            Printer.slowPrint("She stands before the reformed Circle and lays down her case.");
            Printer.slowPrint("They reinstate her. She declines the title of High Arcanist.");
            Printer.slowPrint("She takes a different one: Keeper of Accountability.");
            Printer.pause(500);
            Printer.printBox("ENDING: THE REFORMER");
            Printer.printBox("Power doesn't corrupt those who never wanted it.");
        }
    }

    private void showKnightEnding(Knight knight) {
        Printer.printTitle("EPILOGUE — CADEN ASHFORD");
        String ending = storyManager.determineKnightEnding();
        if (ending.equals("OUTCAST")) {
            Printer.slowPrint("Caden kills Veyran with his own hands in the great hall.");
            Printer.slowPrint("The Iron Vow brands him a criminal. A murderer.");
            Printer.slowPrint("He doesn't argue. He walks out the front door.");
            Printer.slowPrint("He crosses the border at dawn. Free for the first time.");
            Printer.pause(500);
            Printer.printBox("ENDING: THE FREE MAN");
            Printer.printBox("Some debts can only be paid in exile.");
        } else {
            Printer.slowPrint("Veyran is arrested. Tried publicly. Found guilty.");
            Printer.slowPrint("Caden is offered his rank back. He refuses.");
            Printer.slowPrint("He accepts instead a new post — independent, answerable to no marshal.");
            Printer.slowPrint("The Iron Vow's watchdog. Their conscience. The thing they always needed.");
            Printer.pause(500);
            Printer.printBox("ENDING: THE ARBITER");
            Printer.printBox("Honor isn't a title. It's a choice, made again every day.");
        }
    }

    private void showPriestEnding(Priest priest) {
        Printer.printTitle("EPILOGUE — SOLIA REN");
        String ending = storyManager.determinePriestEnding();
        if (ending.equals("ASHES")) {
            Printer.slowPrint("Solia destroys the Pyre Conduit. And the Vault with it.");
            Printer.slowPrint("A thousand years of Sacred Flame history — gone.");
            Printer.slowPrint("Aldran weeps. She lets him.");
            Printer.slowPrint("She walks out into the ash and begins again. From nothing.");
            Printer.slowPrint("Her first convert is a child in a refugee camp. She teaches them kindness.");
            Printer.pause(500);
            Printer.printBox("ENDING: FROM ASHES");
            Printer.printBox("Sometimes you have to burn down what you love to save what matters.");
        } else {
            Printer.slowPrint("Solia purifies the Conduit. It hums — but warm now. Clean.");
            Printer.slowPrint("The Vault stands. Aldran is imprisoned. The order is hers to lead.");
            Printer.slowPrint("She rewrites the doctrine on her first day.");
            Printer.slowPrint("First line: 'Faith is not certainty. Faith is showing up anyway.'");
            Printer.pause(500);
            Printer.printBox("ENDING: THE NEW FLAME");
            Printer.printBox("She didn't find her faith again. She built a better one.");
        }
    }
}
