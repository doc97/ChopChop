package fi.chop.model.fsm.states.guillotine;

import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.GuillotineObject;

public class GuillotineBladeRaiseState extends ObjectState<GuillotineStateMachine, GuillotineObject> {

    public GuillotineBladeRaiseState(GuillotineStateMachine machine, GuillotineObject object) {
        super(machine, object);
    }

    @Override
    public void enter() { }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) {
        float toRaise = getObject().getToRaise();
        float meterDelta = Math.min(delta * 2 * GuillotineObject.MAX_RAISE_AMOUNT_PX, toRaise);

        getObject().addBladeYOffset(meterDelta);
        toRaise -= meterDelta;
        getObject().setToRaise(toRaise);

        if (toRaise <= 0)
            getStateMachine().setCurrent(GuillotineStates.IDLE);
    }
}
