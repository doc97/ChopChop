package fi.chop.timer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGameTimer {

    private GameTimer timer;
    private boolean flag;

    private void action() {
        flag = !flag;
    }

    @Before
    public void setUp() {
        timer = new GameTimer();
    }

    @Test
    public void testAddActionNoDelay() {
        timer.addAction(0, this::action);
        assertTrue(flag);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddActionNegativeDelay() {
        timer.addAction(-1, this::action);
    }

    @Test
    public void testAddActionWithDelay() {
        timer.addAction(1, this::action);
        assertFalse(flag);
    }

    @Test
    public void testUpdate() {
        timer.addAction(1, this::action);
        timer.update(0.9f);
        assertFalse(flag);
        timer.update(0.1f);
        assertTrue(flag);
    }

    @Test
    public void testUpdateNoDelay() {
        timer.addAction(0, this::action);
        flag = false;
        timer.update(0.1f);
        assertFalse(flag);
    }

    @Test
    public void testUpdateTwoDelays() {
        timer.addAction(1, this::action);
        timer.addAction(2, this::action);
        timer.update(1);
        assertTrue(flag);
        timer.update(0.9f);
        assertTrue(flag);
        timer.update(0.1f);
        assertFalse(flag);
    }
}
