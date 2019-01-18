package fi.chop.model.fsm.states.powermeter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.PowerMeterObject;

public class PowerMeterPowerDownState extends ObjectState<PowerMeterStateMachine, PowerMeterObject> {

    public PowerMeterPowerDownState(PowerMeterStateMachine machine, PowerMeterObject object) {
        super(machine, object);
    }

    @Override
    public void enter() { }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) {
        float meterDelta = -Math.max(2 * getObject().getMeterFillPercentage() * delta, 0.01f);
        getObject().addMeterValue(meterDelta);

        if (getObject().getMeterFillPercentage() == 0)
            getStateMachine().setCurrent(PowerMeterStates.IDLE);
    }

    @Override
    public void render(SpriteBatch batch) { }
}
