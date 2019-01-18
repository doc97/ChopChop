package fi.chop.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestValueMeter {

    private static final float EPSILON = 0.000001f;

    private ValueMeter meter;

    @Before
    public void setUp() {
        meter = new ValueMeter();
    }

    @Test
    public void testDefaultValues() {
        assertEquals(0, meter.getFillPercentage(), EPSILON);
    }

    @Test
    public void testSet() {
        meter.set(0.2f);
        assertEquals(0.2f, meter.getFillPercentage(), EPSILON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegative() {
        meter.set(-0.1f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetOverOne() {
        meter.set(1.1f);
    }

    @Test
    public void testAddPositive() {
        meter.set(0.2f);
        meter.add(0.3f);
        assertEquals(0.5f, meter.getFillPercentage(), EPSILON);
    }

    @Test
    public void testAddNegative() {
        meter.set(0.5f);
        meter.add(-0.2f);
        assertEquals(0.3f, meter.getFillPercentage(), EPSILON);
    }

    @Test
    public void testAddResultNegative() {
        meter.add(-0.2f);
        assertEquals(0, meter.getFillPercentage(), EPSILON);
        meter.set(0.1f);
        meter.add(-0.2f);
        assertEquals(0, meter.getFillPercentage(), EPSILON);
    }

    @Test
    public void testAddResultOverOne() {
        meter.add(1.1f);
        assertEquals(1, meter.getFillPercentage(), EPSILON);
        meter.set(0.8f);
        meter.add(0.3f);
        assertEquals(1, meter.getFillPercentage(), EPSILON);
    }
}
