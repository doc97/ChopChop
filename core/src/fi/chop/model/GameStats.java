package fi.chop.model;

public class GameStats {

    private int dailyKills;
    private int overallKills;

    public void resetDailyKills() {
        dailyKills = 0;
    }

    public void addDailyKill() {
        dailyKills++;
        overallKills++;
    }

    public int getDailyKills() {
        return dailyKills;
    }

    public int getOverallKills() {
        return overallKills;
    }
}
