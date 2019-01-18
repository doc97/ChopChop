package fi.chop.model.world;

public class Player {

    public static final int MIN_REPUTATION_LEVEL = 0;
    public static final int MAX_REPUTATION_LEVEL = 5;

    private float popularity;
    private float reputation;
    private int reputationLevel;
    private int money;

    public void addPopularity(float delta) {
        popularity = Math.min(Math.max(popularity + delta, 0), 1);
    }

    public float getPopularity() {
        return popularity;
    }

    public void addReputation(float delta) {
        reputation += delta;
        while (reputation >= 1) {
            increaseReputationLevel();
            reputation -= 1.0f;
            if (reputationLevel == MAX_REPUTATION_LEVEL) {
                reputation = 1;
                break;
            }
        }
        while (reputation < 0) {
            decreaseReputationLevel();
            reputation += 1.0f;
            if (reputationLevel == MIN_REPUTATION_LEVEL)
                reputation = 0;
        }
    }

    public float getReputation() {
        return reputation;
    }

    private void increaseReputationLevel() {
        if (++reputationLevel > MAX_REPUTATION_LEVEL)
            reputationLevel = MAX_REPUTATION_LEVEL;
    }

    private void decreaseReputationLevel() {
        if (--reputationLevel < MIN_REPUTATION_LEVEL)
            reputationLevel = MIN_REPUTATION_LEVEL;
    }

    public int getReputationLevel() {
        return reputationLevel;
    }

    public void addMoney(int delta) {
        money = Math.max(money + delta, 0);
    }

    public int getMoney() {
        return money;
    }

    public boolean hasEnoughMoney(int limit) {
        return money >= limit;
    }
}
