package fi.chop.model;

import fi.chop.util.MathUtil;
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
        float expected1 = MathUtil.smoothStartN(0.1f, 3);
        float expected2 = MathUtil.smoothStartN(0.2f, 3);
        bar.update(0.1f);
        assertEquals(expected1, bar.getValue(), EPSILON);
        bar.update(0.1f);
        assertEquals(expected2, bar.getValue(), EPSILON);
    }

    @Test
    public void testUpdateOverflow() {
        float expected1 = MathUtil.smoothStartN(0.9f, 3);
        float expected2 = MathUtil.smoothStartN(0.8f, 3);
        bar.update(1.1f);
        assertEquals(expected1, bar.getValue(), EPSILON);
        bar.update(0.1f);
        assertEquals(expected2, bar.getValue(), EPSILON);
    }

    @Test
    public void testUpdateUnderflow() {
        float expected1 = MathUtil.smoothStartN(0.1f, 3);
        float expected2 = MathUtil.smoothStartN(0.2f, 3);
        bar.update(2.1f);
        assertEquals(expected1, bar.getValue(), EPSILON);
        bar.update(0.1f);
        assertEquals(expected2, bar.getValue(), EPSILON);
    }

    @Test
    public void testUpdateUnderOverflow() {
        float expected1 = MathUtil.smoothStartN(0.9f, 3);
        float expected2 = MathUtil.smoothStartN(0.1f, 3);
        float expected3 = MathUtil.smoothStartN(0.2f, 3);
        bar.update(0.9f);
        bar.update(0.2f);
        assertEquals(expected1, bar.getValue(), EPSILON);
        bar.update(0.8f);
        bar.update(0.2f);
        assertEquals(expected2, bar.getValue(), EPSILON);
        bar.update(0.1f);
        assertEquals(expected3, bar.getValue(), EPSILON);
    }

    @Test
    public void testSetDurationSec() {
        float expected1 = MathUtil.smoothStartN(0.5f, 3);
        float expected2 = MathUtil.smoothStartN(0.75f, 3);
        bar.setDurationSec(2);
        assertEquals(2f, bar.getDurationSec(), EPSILON);
        bar.update(1);
        assertEquals(expected1, bar.getValue(), EPSILON);
        bar.update(0.5f);
        assertEquals(expected2, bar.getValue(), EPSILON);
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
