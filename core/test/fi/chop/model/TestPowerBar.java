package fi.chop.model;

import fi.chop.MathUtil;
import junit.framework.TestCase;

public class TestPowerBar extends TestCase {

    private static final float EPSILON = 0.000001f;
    private PowerBar bar;

    @Override
    public void setUp() {
        bar = new PowerBar(1);
    }

    public void testDefaultValues() {
        assertEquals(0, bar.getValue(), EPSILON);
    }

    public void testRandomize() {
        float value1 = bar.getValue();
        bar.randomize();
        float value2 = bar.getValue();
        bar.randomize();
        float value3 = bar.getValue();
        assertFalse(value1 == value2 && value1 == value3);
    }

    public void testUpdate() {
        float expected1 = MathUtil.lerp(0, 1, 0.1f);
        float expected2 = MathUtil.lerp(0, 1, 0.2f);
        bar.update(0.1f);
        assertEquals(expected1, bar.getValue(), EPSILON);
        bar.update(0.1f);
        assertEquals(expected2, bar.getValue(), EPSILON);
    }

    public void testUpdateOverflow() {
        bar.update(1.1f);
        assertEquals(0.9f, bar.getValue(), EPSILON);
        bar.update(0.1f);
        assertEquals(0.8f, bar.getValue(), EPSILON);
    }

    public void testUpdateUnderflow() {
        bar.update(2.1f);
        assertEquals(0.1, bar.getValue(), EPSILON);
        bar.update(0.1f);
        assertEquals(0.2, bar.getValue(), EPSILON);
    }
}
