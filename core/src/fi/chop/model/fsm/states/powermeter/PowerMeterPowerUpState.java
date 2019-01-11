package fi.chop.model.fsm.states.powermeter;

import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.object.PowerMeterObject;

public class PowerMeterPowerUpState extends PowerMeterState {

    public PowerMeterPowerUpState(PowerMeterStateMachine machine, PowerMeterObject object) {
        super(machine, object);
    }

    @Override
    public void update(float delta) {
        PowerMeterObject obj = getObject();
        float toAdd = obj.getToAdd();
        float meterDelta = Math.min(delta, toAdd);

        obj.addMeterPower(meterDelta);
        toAdd -= meterDelta;
        obj.setToAdd(toAdd);

        if (toAdd <= 0)
            getStateMachine().setCurrent(PowerMeterStates.IDLE);
    }
}
