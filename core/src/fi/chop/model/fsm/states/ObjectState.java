package fi.chop.model.fsm.states;

import fi.chop.model.fsm.machines.StateMachine;

public abstract class ObjectState<S extends StateMachine, T> implements State<S> {

    private S machine;
    private T object;

    public ObjectState(S machine, T object) {
        this.machine = machine;
        this.object = object;
    }

    public S getStateMachine() {
        return machine;
    }

    protected T getObject() {
        return object;
    }
}
