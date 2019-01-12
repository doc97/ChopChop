package fi.chop.model;

public class GameStats {

    private int dailyKills;
    private int overallKills;
    private float highestPower;

    public void resetDailyKills() {
        dailyKills = 0;
    }

    public void addDailyKill() {
        dailyKills++;
        overallKills++;
    }

    public void registerPower(float power) {
        if (power > highestPower)
            highestPower = power;
    }

    public int getDailyKills() {
        return dailyKills;
    }

    public int getOverallKills() {
        return overallKills;
    }

    public float getHighestPower() {
        return highestPower;
    }
}
