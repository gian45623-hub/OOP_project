package rpgsumting;

import java.awt.Color;

public enum Job {
    MAGE("Mage", "Controls spells and ancient knowledge.", 80, 120, 4, 7,
            new Color(96, 70, 190), new Color(235, 215, 110)),
    KNIGHT("Knight", "Survives danger with armor and courage.", 130, 40, 9, 3,
            new Color(95, 110, 125), new Color(215, 225, 235)),
    ROGUE("Rogue", "Moves quietly and wins through precision.", 95, 70, 6, 8,
            new Color(55, 65, 80), new Color(130, 220, 185)),
    THIEF("Thief", "Uses tricks, speed, and risky opportunities.", 90, 60, 5, 10,
            new Color(80, 65, 45), new Color(238, 185, 75)),
    ARCHER("Archer", "Reads the battlefield and strikes from range.", 100, 55, 7, 6,
            new Color(70, 125, 70), new Color(210, 160, 95));

    private final String displayName;
    private final String description;
    private final int startingHealth;
    private final int startingMana;
    private final int startingCourage;
    private final int startingCunning;
    private final Color primaryColor;
    private final Color accentColor;

    Job(String displayName, String description, int startingHealth, int startingMana,
            int startingCourage, int startingCunning, Color primaryColor, Color accentColor) {
        this.displayName = displayName;
        this.description = description;
        this.startingHealth = startingHealth;
        this.startingMana = startingMana;
        this.startingCourage = startingCourage;
        this.startingCunning = startingCunning;
        this.primaryColor = primaryColor;
        this.accentColor = accentColor;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public int getStartingHealth() {
        return startingHealth;
    }

    public int getStartingMana() {
        return startingMana;
    }

    public int getStartingCourage() {
        return startingCourage;
    }

    public int getStartingCunning() {
        return startingCunning;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getAccentColor() {
        return accentColor;
    }
}
