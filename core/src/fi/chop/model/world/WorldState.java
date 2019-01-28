package fi.chop.model.world;

public class WorldState {

    private int day;
    private int taxes;
    private int drinkCount;
    private Execution execution;

    public void reset() {
        day = 0;
        taxes = 100;
        drinkCount = 0;
        execution = null;
    }

    public void nextDay() {
        day++;
        drinkCount = 0;
        execution = ExecutionFactory.create();
    }

    public int getDay() {
        return day;
    }

    public void increaseTaxes() {
        taxes += 100;
    }

    public int getTaxes() {
        return taxes;
    }

    public int getDaysUntilTaxation() {
        return 7 - day % 7;
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
