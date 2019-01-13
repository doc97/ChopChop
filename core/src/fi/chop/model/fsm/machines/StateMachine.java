package fi.chop.model.fsm.machines;

import fi.chop.model.fsm.states.State;

import java.util.HashMap;
import java.util.Map;

public class StateMachine<K, V extends State> {

    private final Map<K, V> states;
    private K current;

    public StateMachine() {
        states = new HashMap<>();
    }

    public void addState(K key, V state) {
        states.put(key, state);
        if (states.size() == 1)
            current = key;
    }

    public void update(float delta) {
        if (states.isEmpty())
            return;
        states.get(current).update(delta);
    }

    public void setCurrent(K key) {
        if (!states.containsKey(key))
            throw new IllegalArgumentException("The StateMachine does not have a state bound to '" + key.toString() + "'");
        current = key;
    }

    public K getCurrent() {
        if (states.isEmpty())
            throw new IllegalStateException("StateMachine is empty");
        return current;
    }

    public boolean isEmpty() {
        return states.isEmpty();
    }
}
