package fi.chop.model.fsm.machines;

import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.fsm.states.guillotine.*;
import fi.chop.model.object.GuillotineObject;

public class GuillotineStateMachine extends ObjectStateMachine<GuillotineStates,
        ObjectState<GuillotineStateMachine, GuillotineObject>> {

    public GuillotineStateMachine(GuillotineObject object) {
        addState(GuillotineStates.IDLE, new GuillotineIdleState(this, object));
        addState(GuillotineStates.BLADE_FALL, new GuillotineBladeFallState(this, object));
        addState(GuillotineStates.BLADE_RAISE, new GuillotineBladeRaiseState(this, object));
        addState(GuillotineStates.RESTORE, new GuillotineRestoreState(this, object));
    }

    @Override
    public void setCurrent(GuillotineStates key) {
        super.setCurrent(key);
        Chop.events.notify(Events.EVT_GUILLOTINE_STATE_CHANGED, new EventData<>(key));
    }
}
