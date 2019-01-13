package fi.chop.model.fsm.states.person;

import fi.chop.model.fsm.machines.PersonStateMachine;
import fi.chop.model.fsm.states.State;
import fi.chop.model.object.PersonObject;

public abstract class PersonState implements State {

    private final PersonStateMachine machine;
    private final PersonObject object;

    public PersonState(PersonStateMachine machine, PersonObject object) {
        this.machine = machine;
        this.object = object;
    }

    protected PersonStateMachine getStateMachine() {
        return machine;
    }

    protected PersonObject getObject() {
        return object;
    }
}
