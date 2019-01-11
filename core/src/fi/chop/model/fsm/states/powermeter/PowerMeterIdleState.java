package fi.chop.model.fsm.states.powermeter;

import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.object.PowerMeterObject;

public class PowerMeterIdleState extends PowerMeterState {

    public PowerMeterIdleState(PowerMeterStateMachine machine, PowerMeterObject object) {
        super(machine, object);
    }

    @Override
    public void update(float delta) {

    }
}
