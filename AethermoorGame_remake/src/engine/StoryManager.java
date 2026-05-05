package engine;

import characters.Character;
import characters.Mage;
import java.util.HashMap;
import java.util.Map;

public class StoryManager {
    private Character player;
    private int currentAct;
    private Map<String, Boolean> flags;

    public StoryManager(Character player) {
        this.player = player;
        this.currentAct = 1;
        this.flags = new HashMap<>();
    }

    public void setFlag(String flag, boolean value) {
        flags.put(flag, value);
    }

    public boolean getFlag(String flag) {
        return flags.getOrDefault(flag, false);
    }

    public void advanceAct() {
        currentAct++;
    }

    public int getCurrentAct() { return currentAct; }
    public Character getPlayer() { return player; }

    // ─── Ending Determination ─────────────────────────────────────────────────

    public String determineMageEnding() {
        Mage mage = (Mage) player;
        if (mage.getRawPowerCharges() == 0 || getFlag("eryn_used_raw_power_on_dael")) {
            return "BLIND";
        }
        return "REFORM";
    }

    public String determineKnightEnding() {
        if (getFlag("caden_killed_veyran_personally")) {
            return "OUTCAST";
        }
        return "ARBITER";
    }

    public String determinePriestEnding() {
        if (getFlag("solia_destroyed_conduit")) {
            return "ASHES";
        }
        return "REBORN";
    }
}
