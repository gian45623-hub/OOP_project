package rpgsumting;

public class Choice {
    private final String text;
    private final String result;
    private final String nextNodeId;
    private final int healthDelta;
    private final int manaDelta;
    private final int courageDelta;
    private final int cunningDelta;
    private final int fateDelta;

    public Choice(String text, String result, String nextNodeId,
            int healthDelta, int manaDelta, int courageDelta, int cunningDelta, int fateDelta) {
        this.text = text;
        this.result = result;
        this.nextNodeId = nextNodeId;
        this.healthDelta = healthDelta;
        this.manaDelta = manaDelta;
        this.courageDelta = courageDelta;
        this.cunningDelta = cunningDelta;
        this.fateDelta = fateDelta;
    }

    public String getText() {
        return text;
    }

    public String getResult() {
        return result;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public int getHealthDelta() {
        return healthDelta;
    }

    public int getManaDelta() {
        return manaDelta;
    }

    public int getCourageDelta() {
        return courageDelta;
    }

    public int getCunningDelta() {
        return cunningDelta;
    }

    public int getFateDelta() {
        return fateDelta;
    }

    public String getEffectText() {
        StringBuilder builder = new StringBuilder();
        appendEffect(builder, "HP", healthDelta);
        appendEffect(builder, "Mana", manaDelta);
        appendEffect(builder, "Courage", courageDelta);
        appendEffect(builder, "Cunning", cunningDelta);
        appendEffect(builder, "Fate", fateDelta);

        if (builder.length() == 0) {
            return "No stat change";
        }

        return builder.toString();
    }

    private void appendEffect(StringBuilder builder, String label, int value) {
        if (value == 0) {
            return;
        }

        if (builder.length() > 0) {
            builder.append(", ");
        }

        if (value > 0) {
            builder.append("+");
        }
        builder.append(value).append(" ").append(label);
    }
}
