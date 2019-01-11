package fi.chop.model.fsm.states.powermeter;

import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.object.PowerMeterObject;

public class PowerMeterPowerDownState extends PowerMeterState {

    public PowerMeterPowerDownState(PowerMeterStateMachine machine, PowerMeterObject object) {
        super(machine, object);
    }

    @Override
    public void update(float delta) {
        float meterDelta = -Math.max(2 * getObject().getMeterFillPercentage() * delta, 0.01f);
        getObject().addMeterPower(meterDelta);

        if (getObject().getMeterFillPercentage() == 0)
            getStateMachine().setCurrent(PowerMeterStates.IDLE);
    }
}
