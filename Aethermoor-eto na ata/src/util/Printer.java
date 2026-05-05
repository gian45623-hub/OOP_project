package util;

public class Printer {

    private static final int DEFAULT_DELAY = 25;

    public static void slowPrint(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                int delay = (c == '.' || c == '!' || c == '?') ? 190 : DEFAULT_DELAY;
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    public static void printDivider() {
        System.out.println("\n────────────────────────────────────────────────────────");
    }

    public static void printTitle(String title) {
        printDivider();
        System.out.println("  " + title);
        printDivider();
    }

    public static void printBox(String text) {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│ " + text + 												 "│ ");
        System.out.println("└─────────────────────────────────────────────────────────────┘");
    }

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
