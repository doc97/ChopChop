package fi.chop.model.fsm.states.person;

import fi.chop.model.fsm.machines.PersonStateMachine;
import fi.chop.model.object.PersonObject;

public class PersonSavedState extends PersonState {

    public PersonSavedState(PersonStateMachine machine, PersonObject object) {
        super(machine, object);
    }

    @Override
    public void update(float delta) {

    }
}
