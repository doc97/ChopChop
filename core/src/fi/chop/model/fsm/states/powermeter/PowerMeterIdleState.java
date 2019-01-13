package fi.chop.model.fsm.states.powermeter;

import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.PowerMeterObject;

public class PowerMeterIdleState extends ObjectState<PowerMeterStateMachine, PowerMeterObject> {

    public PowerMeterIdleState(PowerMeterStateMachine machine, PowerMeterObject object) {
        super(machine, object);
    }

    @Override
    public void enter() { }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) {
        if (getObject().isReady()) {
            getObject().resetReady();
            getStateMachine().setCurrent(PowerMeterStates.POWER_DOWN);
        }
    }
}
