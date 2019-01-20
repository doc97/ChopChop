package fi.chop.model.world;

public class WorldState {

    private int day;
    private Execution execution;

    public void nextDay() {
        day++;
        execution = ExecutionFactory.create();
    }

    public int getDay() {
        return day;
    }

    public Execution getExecution() {
        return execution;
    }
}
