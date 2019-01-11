package fi.chop.model.fsm.states.guillotine;

import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.object.GuillotineObject;

public class GuillotineBladeRaiseState extends GuillotineState {

    public GuillotineBladeRaiseState(GuillotineStateMachine machine, GuillotineObject object) {
        super(machine, object);
    }

    @Override
    public void update(float delta) {
        GuillotineObject obj = getObject();
        float toRaise = obj.getToRaise();
        float meterDelta = Math.min(delta * 2 * GuillotineObject.MAX_RAISE_PX, toRaise);

        obj.addBladeYOffset(meterDelta);
        toRaise -= meterDelta;
        obj.setToRaise(toRaise);

        if (toRaise <= 0)
            getStateMachine().setCurrent(GuillotineStates.IDLE);
    }
}
