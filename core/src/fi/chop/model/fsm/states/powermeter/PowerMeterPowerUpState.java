package fi.chop.model.fsm.states.powermeter;

import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.PowerMeterObject;

public class PowerMeterPowerUpState extends ObjectState<PowerMeterStateMachine, PowerMeterObject> {

    public PowerMeterPowerUpState(PowerMeterStateMachine machine, PowerMeterObject object) {
        super(machine, object);
    }

    @Override
    public void enter() { }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) {
        float toAdd = getObject().getToAdd();
        float meterDelta = Math.min(delta, toAdd);

        getObject().addMeterPower(meterDelta);
        toAdd -= meterDelta;
        getObject().setToAdd(toAdd);

        if (toAdd <= 0)
            getStateMachine().setCurrent(PowerMeterStates.IDLE);
    }
}
