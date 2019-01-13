package fi.chop.model.fsm.machines;

import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.fsm.states.person.*;
import fi.chop.model.object.PersonObject;

public class PersonStateMachine extends ObjectStateMachine<PersonStates,
        ObjectState<PersonStateMachine, PersonObject>> {

    public PersonStateMachine(PersonObject object) {
        addState(PersonStates.IDLE, new PersonIdleState(this, object));
        addState(PersonStates.DEAD, new PersonDeadState(this, object));
        addState(PersonStates.SAVED, new PersonSavedState(this, object));
    }
}
