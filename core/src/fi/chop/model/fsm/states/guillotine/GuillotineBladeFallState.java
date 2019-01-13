package fi.chop.model.fsm.states.guillotine;

import fi.chop.Chop;
import fi.chop.event.Events;
import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.GuillotineObject;

public class GuillotineBladeFallState extends ObjectState<GuillotineStateMachine, GuillotineObject> {

    private static final float JERK = 2;
    private float velocity;
    private float time;

    public GuillotineBladeFallState(GuillotineStateMachine machine, GuillotineObject object) {
        super(machine, object);
    }

    @Override
    public void enter() {
        velocity = 0;
        time = 0;
    }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) {
        time += delta;
        velocity += JERK * time * time;
        getObject().addBladeYOffset(-velocity);

        if (getObject().getBladeYOffset() == 0) {
            time = 0;
            velocity = 0;
            Chop.events.notify(Events.EVT_HEAD_CHOP);
            getStateMachine().setCurrent(GuillotineStates.RESTORE);
        }
    }
}
