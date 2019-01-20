package fi.chop.model.fsm.machines;

import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.fsm.states.powermeter.*;
import fi.chop.model.object.PowerMeterObject;

public class PowerMeterStateMachine extends ObjectStateMachine<PowerMeterStates,
        ObjectState<PowerMeterStateMachine, PowerMeterObject>> {

    public PowerMeterStateMachine(PowerMeterObject object) {
        addState(PowerMeterStates.IDLE, new PowerMeterIdleState(this, object));
        addState(PowerMeterStates.POWER_UP, new PowerMeterPowerUpState(this, object));
        addState(PowerMeterStates.POWER_DOWN, new PowerMeterPowerDownState(this, object));
    }

    @Override
    public void setCurrent(PowerMeterStates key) {
        super.setCurrent(key);
        Chop.events.notify(Events.EVT_POWERMETER_STATE_CHANGED, new EventData<>(key));
    }
}
