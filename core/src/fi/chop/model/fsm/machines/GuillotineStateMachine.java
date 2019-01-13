package fi.chop.model.fsm.machines;

import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.fsm.states.guillotine.*;
import fi.chop.model.object.GuillotineObject;

public class GuillotineStateMachine extends StateMachine<GuillotineStates, ObjectState<GuillotineStateMachine, GuillotineObject>> {

    public GuillotineStateMachine(GuillotineObject object) {
        addState(GuillotineStates.IDLE, new GuillotineIdleState(this, object));
        addState(GuillotineStates.BLADE_FALL, new GuillotineBladeFallState(this, object));
        addState(GuillotineStates.BLADE_RAISE, new GuillotineBladeRaiseState(this, object));
        addState(GuillotineStates.RESTORE, new GuillotineRestoreState(this, object));
    }
}
