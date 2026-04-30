package rpgsumting;

public class Player {
    private String name;
    private Job job;
    private int health;
    private int mana;
    private int courage;
    private int cunning;
    private int fate;

    public void reset(String name, Job job) {
        this.name = name;
        this.job = job;
        this.health = job.getStartingHealth();
        this.mana = job.getStartingMana();
        this.courage = job.getStartingCourage();
        this.cunning = job.getStartingCunning();
        this.fate = 0;
    }

    public void apply(Choice choice) {
        health = clamp(health + choice.getHealthDelta(), 0, 160);
        mana = clamp(mana + choice.getManaDelta(), 0, 150);
        courage = clamp(courage + choice.getCourageDelta(), 0, 15);
        cunning = clamp(cunning + choice.getCunningDelta(), 0, 15);
        fate = clamp(fate + choice.getFateDelta(), -10, 10);
    }

    private int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public String getName() {
        return name;
    }

    public Job getJob() {
        return job;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getCourage() {
        return courage;
    }

    public int getCunning() {
        return cunning;
    }

    public int getFate() {
        return fate;
    }

    public String getStatusLine() {
        if (job == null) {
            return "Choose a job to begin.";
        }

        return name + " the " + job.getDisplayName()
                + "   HP " + health
                + "   Mana " + mana
                + "   Courage " + courage
                + "   Cunning " + cunning
                + "   Fate " + fate;
    }
}
