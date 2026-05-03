package characters;

import combat.Enemy;
import combat.Skill;

public class Mage extends Character {
    private int rawPowerCharges;
    public static final int MAX_RAW_CHARGES = 3;

    public Mage() {
        super("Eryn Voss", "The Exiled Scholar", 80, 40, 5, 100);
        this.rawPowerCharges = MAX_RAW_CHARGES;

        skills.add(new Skill("Arcane Bolt",   25, 15, "A focused beam of arcane energy."));
        skills.add(new Skill("Frost Nova",     40, 30, "Unleash a burst of freezing cold around you."));
        skills.add(new Skill("Mana Siphon",   -20, 0,  "Drain your own HP to restore 30 mana."));
        skills.add(new Skill("Void Lance",     55, 45, "A powerful but draining arcane spear."));
    }

    @Override
    public void useSpecialAbility(Enemy enemy) {
        if (rawPowerCharges > 0) {
            int damage = (int)(Math.random() * 61) + 40; // 40–100 uncontrolled
            System.out.println("\n  Eryn squeezes her eyes shut. Something tears open inside her.");
            System.out.println("  Raw, uncontrolled magic ERUPTS outward — (" + damage + " damage!)");
            enemy.takeDamage(damage);
            rawPowerCharges--;
            System.out.println("  [Raw Power charges remaining: " + rawPowerCharges + "/" + MAX_RAW_CHARGES + "]");
            if (rawPowerCharges == 0) {
                System.out.println("  The well goes dry. That power... it scares her.");
            }
        } else {
            System.out.println("\n  Eryn reaches for that dark place inside her. There's nothing left.");
            System.out.println("  [No Raw Power charges remaining.]");
        }
    }

    @Override
    public String getSpecialAbilityName() {
        return "Raw Power [" + rawPowerCharges + "/" + MAX_RAW_CHARGES + " charges]";
    }

    @Override
    public String getClassDescription() {
        return "Eryn Voss — Exiled mage. High magic damage, low defense.\n" +
               "Special: Raw Power — massive uncontrolled burst (3 uses total, affects ending).";
    }

    public int getRawPowerCharges() { return rawPowerCharges; }

    // Used for Mana Siphon skill — called from CombatSystem
    public void useManaSkill() {
        int hpCost = 20;
        int manaGain = 30;
        this.hp = Math.max(1, this.hp - hpCost);
        this.mana = Math.min(this.maxMana, this.mana + manaGain);
        System.out.println("  Eryn sacrifices " + hpCost + " HP to restore " + manaGain + " mana.");
    }
}
