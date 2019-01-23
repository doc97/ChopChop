package fi.chop.model.world;

public class WorldState {

    private int day;
    private int taxes;
    private int drinkCount;
    private Execution execution;

    public void reset() {
        day = 0;
        taxes = 0;
        drinkCount = 0;
        execution = null;
    }

    public void nextDay() {
        if (day % 7 == 0)
            taxes += 100;
        day++;
        drinkCount = 0;
        execution = ExecutionFactory.create();
    }


    public int getDay() {
        return day;
    }

    public int getTaxes() {
        return taxes;
    }

    public void incrementDrinkCount() {
        drinkCount++;
    }

    public int getDrinkCount() {
        return drinkCount;
    }

    public Execution getExecution() {
        return execution;
    }
}
