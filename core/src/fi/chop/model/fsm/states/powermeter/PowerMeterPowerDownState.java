package fi.chop.model.fsm.states.powermeter;

import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.object.PowerMeterObject;

public class PowerMeterPowerDownState extends PowerMeterState {

    public PowerMeterPowerDownState(PowerMeterStateMachine machine, PowerMeterObject object) {
        super(machine, object);
    }

    @Override
    public void update(float delta) {
        PowerMeterObject obj = getObject();
        float meterDelta = -Math.max(2 * obj.getMeterFillPercentage() * delta, 0.01f);
        obj.addMeterPower(meterDelta);

        if (obj.getMeterFillPercentage() == 0) {
            getStateMachine().setCurrent(PowerMeterStates.IDLE);
            obj.setRound(0);
        }
    }
}
