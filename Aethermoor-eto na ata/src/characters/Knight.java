package characters;

import combat.Enemy;
import combat.Skill;

public class Knight extends Character {
    private boolean isRaging;
    private int rageTurnsLeft;

    public Knight() {
        super("Caden Ashford", "The Oathbreaker", 140, 30, 20, 30);
        this.isRaging = false;
        this.rageTurnsLeft = 0;

        skills.add(new Skill("Shield Bash",     28, 10, "Slam your shield into the enemy, staggering them."));
        skills.add(new Skill("Precise Strike",  45, 15, "A calculated blow aimed at weak points."));
        skills.add(new Skill("Intimidate",       0,  5, "Shout at the enemy — reduces their next attack by 10."));
        skills.add(new Skill("War Cry",          0, 20, "Boost your own attack by 12 for 2 turns."));
    }

    @Override
    public void useSpecialAbility(Enemy enemy) {
        if (!isRaging) {
            isRaging = true;
            rageTurnsLeft = 3;
            int boost = 20;
            this.attackPower += boost;
            System.out.println("\n  Something snaps inside Caden.");
            System.out.println("  He stops thinking. He stops feeling. He just FIGHTS.");
            System.out.println("  [RAGE activated! Attack +" + boost + " for " + rageTurnsLeft + " turns.]");
        } else {
            System.out.println("\n  Caden is already raging. His vision is red.");
        }
    }

    // Called each turn by CombatSystem to tick down rage
    public void tickRage() {
        if (isRaging) {
            rageTurnsLeft--;
            if (rageTurnsLeft <= 0) {
                isRaging = false;
                this.attackPower -= 20;
                System.out.println("  Caden's rage fades. He feels hollow.");
            }
        }
    }

    @Override
    public String getSpecialAbilityName() {
        return isRaging ? "RAGING [" + rageTurnsLeft + " turns left]" : "Rage Mode";
    }

    @Override
    public String getClassDescription() {
        return "Caden Ashford — Deserter knight. High HP and defense, moderate attack.\n" +
               "Special: Rage Mode — doubles attack for 3 turns but cannot defend.";
    }

    public boolean isRaging() { return isRaging; }
}
