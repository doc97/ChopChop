package fi.chop.model.world;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestExecution {

    private Victim victim;
    private Execution exec;

    @Before
    public void setUp() {
        victim = new Victim("John Doe", SocialStatus.MIDDLE_CLASS);
        exec = new Execution(victim,
                "The killing of Pierre Ward",
                "The theft of one whole pig");
    }

    @Test
    public void testDefaultValues() {
        assertFalse(exec.isFairPunishment());
        assertEquals(0, exec.getBribe());
        assertEquals(victim.getName(), exec.getVictimName());
        assertEquals(victim.getSocialStatus(), exec.getVictimSocialStatus());
        assertArrayEquals(new String[] {
                "The killing of Pierre Ward",
                "The theft of one whole pig"
                }, exec.getCharges());
    }

    @Test
    public void testSetFairPunishment() {
        exec.setFairPunishment(true);
        assertTrue(exec.isFairPunishment());
        exec.setFairPunishment(false);
        assertFalse(exec.isFairPunishment());
    }

    @Test
    public void testSetBribe() {
        exec.setBribe(10);
        assertEquals(10, exec.getBribe());
        exec.setBribe(20);
        assertEquals(20, exec.getBribe());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetBribeNegative() {
        exec.setBribe(-10);
    }
}
