package characters;

import combat.Enemy;
import combat.Skill;

public class Priest extends Character {
    private int faithPoints;
    public static final int MAX_FAITH = 10;

    public Priest() {
        super("Solia Ren", "The Faithless Healer", 100, 15, 10, 80);
        this.faithPoints = MAX_FAITH;

        skills.add(new Skill("Holy Light",   -35, 20, "Channel divine energy to heal yourself."));
        skills.add(new Skill("Smite",         32, 25, "Strike with a burst of sacred flame."));
        skills.add(new Skill("Purify",         0, 30, "Cleanse corruption — weakens undead enemies."));
        skills.add(new Skill("Sacred Shield",  0, 35, "Increase defense by 15 until next turn."));
    }

    @Override
    public void useSpecialAbility(Enemy enemy) {
        if (faithPoints > 0) {
            System.out.println("\n  Solia closes her eyes. She doesn't know if anyone is listening.");
            System.out.println("  She prays anyway.");
            System.out.println("  A warm light surrounds her. Her wounds knit closed.");
            int healAmount = 40;
            this.defense += 8;
            heal(healAmount);
            faithPoints--;
            System.out.println("  [Faith Points remaining: " + faithPoints + "/" + MAX_FAITH + "]");
            if (faithPoints <= 3) {
                System.out.println("  The light feels... thinner than it used to.");
            }
            if (faithPoints == 0) {
                System.out.println("  She prays. Nothing comes. The silence is deafening.");
            }
        } else {
            System.out.println("\n  Solia reaches for her faith. The well is dry.");
            System.out.println("  She fights on alone.");
        }
    }

    // Called by StoryManager when story events shake Solia's faith
    public void loseFaith(int amount) {
        faithPoints = Math.max(0, faithPoints - amount);
        System.out.println("  [Faith -" + amount + " | Remaining: " + faithPoints + "/" + MAX_FAITH + "]");
    }

    @Override
    public String getSpecialAbilityName() {
        return "Divine Prayer [" + faithPoints + "/" + MAX_FAITH + " faith]";
    }

    @Override
    public String getClassDescription() {
        return "Solia Ren — Wandering healer. Balanced stats, strong healing.\n" +
               "Special: Divine Prayer — heals and buffs defense (faith depletes through story).";
    }

    public int getFaithPoints() { return faithPoints; }
}
