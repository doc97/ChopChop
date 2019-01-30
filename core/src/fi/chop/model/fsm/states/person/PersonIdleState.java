package fi.chop.model.fsm.states.person;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.fsm.machines.PersonStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.PersonObject;

public class PersonIdleState extends ObjectState<PersonStateMachine, PersonObject> {

    public PersonIdleState(PersonStateMachine machine, PersonObject object) {
        super(machine, object);
    }

    @Override
    public void enter() { }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) { }
}
