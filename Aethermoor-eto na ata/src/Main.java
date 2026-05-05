import characters.*;
import characters.Character;
import engine.*;
import util.*;

public class Main {

    public static void main(String[] args) {
        // Initialize the GUI on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            createGameWindow();
        });

        // Run the game logic on a background thread so it doesn't freeze the UI
        new Thread(() -> {
            // Slight delay to let the UI render first
            try { Thread.sleep(500); } catch (InterruptedException e) {}
            
            printBanner();
            Character player = chooseCharacter();
            StoryManager storyManager = new StoryManager(player);
            GameEngine engine = new GameEngine(player, storyManager);
            engine.start();
        }).start();
    }

    private static void printBanner() {
        System.out.println();
        System.out.println("  +------------------------------------------+");
        System.out.println("  |                                          |");
        System.out.println("  |           A E T H E R M O O R            |");
        System.out.println("  |                                          |");
        System.out.println("  |    A Broken Kingdom . Three Souls Lost.  |");
        System.out.println("  |          One Truth Yet to Burn.          |");
        System.out.println("  |                                          |");
        System.out.println("  +------------------------------------------+");
        System.out.println();
        Printer.pause(900);
    }

    private static void createGameWindow() {
        try {
            javax.swing.JFrame window = (javax.swing.JFrame) Class.forName("ui.GameWindow")
                    .getDeclaredConstructor()
                    .newInstance();
            window.setVisible(true);
        } catch (Exception e) {
            throw new RuntimeException("Could not start game window.", e);
        }
    }

    private static Character chooseCharacter() {
        Printer.slowPrint("The Greying broke Aethermoor ten years ago.");
        Printer.slowPrint("Three strangers carry the weight of what comes next.");
        Printer.slowPrint("Choose your story.");
        System.out.println();
        System.out.println("  1. ERYN VOSS — The Mage");
        System.out.println("     \"I was right. I was always right. Now I'll prove it.\"");
        System.out.println("     [High damage. Low defense. Raw power mechanic.]");
        System.out.println();
        System.out.println("  2. CADEN ASHFORD — The Knight");
        System.out.println("     \"I did something I can never undo. That doesn't mean I stop.\"");
        System.out.println("     [High HP. Strong defense. Rage mechanic.]");
        System.out.println();
        System.out.println("  3. SOLIA REN — The Priest");
        System.out.println("     \"I don't know if anyone is listening. I'll pray anyway.\"");
        System.out.println("     [Balanced. Healer. Faith mechanic.]");
        System.out.println();

        int choice = InputHandler.getInt(1, 3);

        return switch (choice) {
            case 1 -> {
                System.setProperty("aethermoor.selectedCharacter", "Mage");
                Printer.slowPrint("\nYou are Eryn Voss. Exiled. Angry. Dangerous.");
                Printer.pause(400);
                yield new Mage();
            }
            case 2 -> {
                System.setProperty("aethermoor.selectedCharacter", "Knight");
                Printer.slowPrint("\nYou are Caden Ashford. Tired. Guilty. Still standing.");
                Printer.pause(400);
                yield new Knight();
            }
            case 3 -> {
                System.setProperty("aethermoor.selectedCharacter", "Priest");
                Printer.slowPrint("\nYou are Solia Ren. Doubting. Gentle. Unstoppable.");
                Printer.pause(400);
                yield new Priest();
            }
            default -> throw new IllegalStateException("Invalid choice");
        };
    }
}
