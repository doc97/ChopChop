package fi.chop.model.fsm.machines;

import fi.chop.model.fsm.states.powermeter.*;
import fi.chop.model.object.PowerMeterObject;

public class PowerMeterStateMachine extends StateMachine<PowerMeterStates, PowerMeterState> {

    public PowerMeterStateMachine(PowerMeterObject object) {
        addState(PowerMeterStates.IDLE, new PowerMeterIdleState(this, object));
        addState(PowerMeterStates.POWER_UP, new PowerMeterPowerUpState(this, object));
        addState(PowerMeterStates.POWER_DOWN, new PowerMeterPowerDownState(this, object));
    }
}
