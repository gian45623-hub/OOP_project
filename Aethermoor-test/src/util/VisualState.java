package util;

public class VisualState {
    private static String selectedCharacter;

    public static synchronized void setSelectedCharacter(String characterClass) {
        selectedCharacter = characterClass;
    }

    public static synchronized String getSelectedCharacter() {
        return selectedCharacter;
    }
}
