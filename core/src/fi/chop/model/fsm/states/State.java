package fi.chop.model.fsm.states;

import fi.chop.model.fsm.machines.StateMachine;

public interface State<T extends StateMachine> {
    void enter();
    void exit();
    void update(float delta);
    T getStateMachine();
}
