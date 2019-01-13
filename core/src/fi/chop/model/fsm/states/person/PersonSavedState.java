package fi.chop.model.fsm.states.person;

import fi.chop.model.fsm.machines.PersonStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.PersonObject;

public class PersonSavedState extends ObjectState<PersonStateMachine, PersonObject> {

    public PersonSavedState(PersonStateMachine machine, PersonObject object) {
        super(machine, object);
    }

    @Override
    public void enter() { }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) { }
}
