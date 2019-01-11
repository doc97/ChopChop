package fi.chop.model.fsm.states.powermeter;

import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.fsm.states.State;
import fi.chop.model.object.PowerMeterObject;

public abstract class PowerMeterState implements State {

    private final PowerMeterStateMachine machine;
    private final PowerMeterObject object;

    public PowerMeterState(PowerMeterStateMachine machine, PowerMeterObject object) {
        this.machine = machine;
        this.object = object;
    }

    protected PowerMeterStateMachine getStateMachine() {
        return machine;
    }

    protected PowerMeterObject getObject() {
        return object;
    }
}
