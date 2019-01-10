package fi.chop.model;

import fi.chop.MathUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestPowerBar {

    private static final float EPSILON = 0.000001f;
    private PowerBar bar;

    @Before
    public void setUp() {
        bar = new PowerBar();
    }

    @Test
    public void testDefaultValues() {
        assertEquals(0, bar.getValue(), EPSILON);
    }

    @Test
    public void testRandomize() {
        float value1 = bar.getValue();
        bar.randomize();
        float value2 = bar.getValue();
        bar.randomize();
        float value3 = bar.getValue();
        assertFalse(value1 == value2 && value1 == value3);
    }

    @Test
    public void testUpdate() {
        float expected1 = MathUtil.lerp(0, 1, 0.1f);
        float expected2 = MathUtil.lerp(0, 1, 0.2f);
        bar.update(0.1f);
        assertEquals(expected1, bar.getValue(), EPSILON);
        bar.update(0.1f);
        assertEquals(expected2, bar.getValue(), EPSILON);
    }

    @Test
    public void testUpdateOverflow() {
        bar.update(1.1f);
        assertEquals(0.9f, bar.getValue(), EPSILON);
        bar.update(0.1f);
        assertEquals(0.8f, bar.getValue(), EPSILON);
    }

    @Test
    public void testUpdateUnderflow() {
        bar.update(2.1f);
        assertEquals(0.1, bar.getValue(), EPSILON);
        bar.update(0.1f);
        assertEquals(0.2, bar.getValue(), EPSILON);
    }

    @Test
    public void testSetDurationSec() {
        bar.setDurationSec(2);
        bar.update(1);
        assertEquals(0.5f, bar.getValue(), EPSILON);
        bar.update(0.5f);
        assertEquals(0.75f, bar.getValue(), EPSILON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDurationSecZero() {
        bar.setDurationSec(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDurationSecNegative() {
        bar.setDurationSec(-1);
    }
}
