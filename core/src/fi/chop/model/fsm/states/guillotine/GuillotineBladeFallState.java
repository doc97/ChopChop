package fi.chop.model.fsm.states.guillotine;

import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.object.GuillotineObject;

public class GuillotineBladeFallState extends GuillotineState {

    private static final float JERK = 2;
    private float velocity;
    private float time;

    public GuillotineBladeFallState(GuillotineStateMachine machine, GuillotineObject object) {
        super(machine, object);
    }

    @Override
    public void update(float delta) {
        time += delta;
        velocity += JERK * time * time;
        getObject().addBladeYOffset(-velocity);

        if (getObject().getBladeYOffset() == 0) {
            time = 0;
            velocity = 0;
            getStateMachine().setCurrent(GuillotineStates.RESTORE);
        }
    }
}
