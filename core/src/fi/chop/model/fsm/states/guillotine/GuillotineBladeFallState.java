package fi.chop.model.fsm.states.guillotine;

import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.object.GuillotineObject;

public class GuillotineBladeFallState extends GuillotineState {

    public GuillotineBladeFallState(GuillotineStateMachine machine, GuillotineObject object) {
        super(machine, object);
    }

    @Override
    public void update(float delta) {

    }
}
