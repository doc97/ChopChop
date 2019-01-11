package fi.chop.model.fsm.states.guillotine;

import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.fsm.states.State;
import fi.chop.model.object.GuillotineObject;

public abstract class GuillotineState implements State {

    private final GuillotineStateMachine machine;
    private final GuillotineObject object;

    public GuillotineState(GuillotineStateMachine machine, GuillotineObject object) {
        this.machine = machine;
        this.object = object;
    }

    protected GuillotineStateMachine getStateMachine() {
        return machine;
    }

    protected GuillotineObject getObject() {
        return object;
    }
}
