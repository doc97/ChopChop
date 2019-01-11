package fi.chop.model.fsm;

import fi.chop.model.fsm.machines.StateMachine;
import fi.chop.model.fsm.states.State;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestStateMachine {

    private class TestState implements State {
        private int counter;

        @Override
        public void update(float delta) {
            counter++;
        }
    }

    private StateMachine<String, TestState> machine;

    @Before
    public void setUp() {
        machine = new StateMachine<>();
    }

    @Test
    public void testDefaultValues() {
        assertTrue(machine.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetCurrent() {
        machine.getCurrent();
    }

    @Test
    public void testAddState() {
        TestState state = new TestState();
        machine.addState("a", state);
        assertEquals("a", machine.getCurrent());
    }

    @Test
    public void testUpdateOneState() {
        TestState state = new TestState();
        machine.addState("a", state);
        machine.update(0.1f);
        assertEquals(1, state.counter);
    }

    @Test
    public void testUpdateTwoStates() {
        TestState state1 = new TestState();
        TestState state2 = new TestState();
        machine.addState("a", state1);
        machine.addState("b", state2);
        machine.update(0.1f);
        assertEquals(1, state1.counter);
        assertEquals(0, state2.counter);
    }

    @Test
    public void testSetCurrent() {
        TestState state1 = new TestState();
        TestState state2 = new TestState();
        machine.addState("a", state1);
        machine.addState("b", state2);
        machine.setCurrent("b");
        machine.update(0.1f);
        assertEquals(0, state1.counter);
        assertEquals(1, state2.counter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCurrentInvalidArgument() {
        TestState state1 = new TestState();
        TestState state2 = new TestState();
        machine.addState("a", state1);
        machine.addState("b", state2);
        machine.setCurrent("c");
    }
}
