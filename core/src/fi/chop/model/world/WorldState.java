package fi.chop.model.world;

public class WorldState {

    private int day;
    private int taxes;
    private Execution execution;

    public void reset() {
        day = 0;
        taxes = 0;
        execution = null;
    }

    public void nextDay() {
        if (day % 7 == 0)
            taxes += 100;
        day++;
        execution = ExecutionFactory.create();
    }

    public int getDay() {
        return day;
    }

    public int getTaxes() {
        return taxes;
    }

    public Execution getExecution() {
        return execution;
    }
}
