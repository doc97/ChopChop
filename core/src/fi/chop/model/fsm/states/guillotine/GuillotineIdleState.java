package fi.chop.model.fsm.states.guillotine;

import fi.chop.Chop;
import fi.chop.event.Events;
import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.GuillotineObject;

public class GuillotineIdleState extends ObjectState<GuillotineStateMachine, GuillotineObject> {

    public GuillotineIdleState(GuillotineStateMachine machine, GuillotineObject object) {
        super(machine, object);
    }

    @Override
    public void enter() { }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) {
        if (getObject().isReady()) {
            Chop.events.notify(Events.EVT_GUILLOTINE_RAISED);
            getStateMachine().setCurrent(GuillotineStates.BLADE_FALL);
        }
    }
}
