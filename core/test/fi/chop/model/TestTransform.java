package fi.chop.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTransform {

    private static final float EPSILON = 0.000001f;
    private Transform transform;

    @Before
    public void setUp() {
        transform = new Transform();
    }

    @Test
    public void testDefaultValues() {
        assertEquals(0, transform.getX(), EPSILON);
        assertEquals(0, transform.getY(), EPSILON);
        assertEquals(0, transform.getRotationRad(), EPSILON);
        assertEquals(0, transform.getRotationDeg(), EPSILON);
        assertEquals(0, transform.getWidth(), EPSILON);
        assertEquals(0, transform.getHeight(), EPSILON);
        assertEquals(1, transform.getScaleX(), EPSILON);
        assertEquals(1, transform.getScaleY(), EPSILON);
        assertEquals(0, transform.getOriginX(), EPSILON);
        assertEquals(0, transform.getOriginY(), EPSILON);
    }
    
        @Test
    public void testSetPosition() {
        transform.setPosition(-1, -1);
        assertEquals(-1, transform.getX(), EPSILON);
        assertEquals(-1, transform.getY(), EPSILON);
        transform.setPosition(1, 1);
        assertEquals(1, transform.getX(), EPSILON);
        assertEquals(1, transform.getY(), EPSILON);
    }

    @Test
    public void testSetX() {
        transform.setX(-1);
        assertEquals(-1, transform.getX(), EPSILON);
        transform.setX(1);
        assertEquals(1, transform.getX(), EPSILON);
    }

    @Test
    public void testSetY() {
        transform.setY(-1);
        assertEquals(-1, transform.getY(), EPSILON);
        transform.setY(1);
        assertEquals(1, transform.getY(), EPSILON);
    }

    @Test
    public void testTranslate() {
        transform.translate(-10, 5);
        assertEquals(-10, transform.getX(), EPSILON);
        assertEquals(5, transform.getY(), EPSILON);
        transform.translate(3, -2);
        assertEquals(-7, transform.getX(), EPSILON);
        assertEquals(3, transform.getY(), EPSILON);
    }

    @Test
    public void testSetRotationDeg() {
        transform.setRotationDeg(45);
        assertEquals(45, transform.getRotationDeg(), EPSILON);
        assertEquals(Math.PI / 4, transform.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetRotationDegNegative() {
        transform.setRotationDeg(-90);
        assertEquals(270, transform.getRotationDeg(), EPSILON);
        assertEquals(Math.PI * 3 / 2, transform.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetRotationDegTooBig() {
        transform.setRotationDeg(450);
        assertEquals(90, transform.getRotationDeg(), EPSILON);
        assertEquals(Math.PI / 2, transform.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetRotationRad() {
        transform.setRotationRad(Math.PI / 2);
        assertEquals(90, transform.getRotationDeg(), EPSILON);
        assertEquals(Math.PI / 2, transform.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetRotationRadNegative() {
        transform.setRotationRad(-Math.PI / 2);
        assertEquals(270, transform.getRotationDeg(), EPSILON);
        assertEquals(Math.PI * 3 / 2, transform.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetRotationRadTooBig() {
        transform.setRotationRad(3 * Math.PI);
        assertEquals(180, transform.getRotationDeg(), EPSILON);
        assertEquals(Math.PI, transform.getRotationRad(), EPSILON);
    }

    @Test
    public void testRotateDeg() {
        transform.rotateDeg(45);
        assertEquals(45, transform.getRotationDeg(), EPSILON);
        transform.rotateDeg(34);
        assertEquals(79, transform.getRotationDeg(), EPSILON);
        transform.rotateDeg(-80);
        assertEquals(359, transform.getRotationDeg(), EPSILON);
    }

    @Test
    public void testRotateRad() {
        transform.rotateRad(Math.PI);
        assertEquals(Math.PI, transform.getRotationRad(), EPSILON);
        transform.rotateRad(Math.PI / 2);
        assertEquals(Math.PI * 3 / 2, transform.getRotationRad(), EPSILON);
        transform.rotateRad(-2 * Math.PI);
        assertEquals(Math.PI * 3 / 2, transform.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetWidth() {
        transform.setWidth(10);
        assertEquals(10, transform.getWidth(),EPSILON);
        transform.setWidth(20);
        assertEquals(20, transform.getWidth(),EPSILON);
    }

    @Test
    public void testSetHeight() {
        transform.setHeight(10);
        assertEquals(10, transform.getHeight(), EPSILON);
        transform.setHeight(20);
        assertEquals(20, transform.getHeight(), EPSILON);
    }

    @Test
    public void testSetSize() {
        transform.setSize(10, 20);
        assertEquals(10, transform.getWidth(), EPSILON);
        assertEquals(20, transform.getHeight(), EPSILON);
        transform.setSize(30, 40);
        assertEquals(30, transform.getWidth(), EPSILON);
        assertEquals(40, transform.getHeight(), EPSILON);
    }

    @Test
    public void testSetScaleX() {
        transform.setScaleX(1.1f);
        assertEquals(1.1f, transform.getScaleX(), EPSILON);
        transform.setScaleX(1.2f);
        assertEquals(1.2f, transform.getScaleX(), EPSILON);
    }

    @Test
    public void testScaleY() {
        transform.setScaleY(1.1f);
        assertEquals(1.1f, transform.getScaleY(), EPSILON);
        transform.setScaleY(1.2f);
        assertEquals(1.2f, transform.getScaleY(), EPSILON);
    }

    @Test
    public void testSetScale() {
        transform.setScale(1.2f, 1.3f);
        assertEquals(1.2f, transform.getScaleX(), EPSILON);
        assertEquals(1.3f, transform.getScaleY(), EPSILON);
        transform.setScale(1.4f, 1.5f);
        assertEquals(1.4f, transform.getScaleX(), EPSILON);
        assertEquals(1.5f, transform.getScaleY(), EPSILON);
    }

    @Test
    public void testScale() {
        transform.setScale(2, 2);
        transform.scale(2, 2);
        assertEquals(4, transform.getScaleX(), EPSILON);
        assertEquals(4, transform.getScaleY(), EPSILON);
        transform.scale(0.25f, 0.25f);
        assertEquals(1, transform.getScaleX(), EPSILON);
        assertEquals(1, transform.getScaleY(), EPSILON);
    }

    @Test
    public void testScaleNegative() {
        transform.setScale(2, 2);
        transform.scale(-1, -2);
        assertEquals(-2, transform.getScaleX(), EPSILON);
        assertEquals(-4, transform.getScaleY(), EPSILON);
    }

    @Test
    public void testSetOrigin() {
        transform.setOrigin(0.5f, 0.4f);
        assertEquals(0.5f, transform.getOriginX(), EPSILON);
        assertEquals(0.4f, transform.getOriginY(), EPSILON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeOriginX() {
        transform.setOrigin(-1, 0.5f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeOriginY() {
        transform.setOrigin(0.5f, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetOriginXGreaterThanOne() {
        transform.setOrigin(1.1f, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetOriginYGreaterThanOne() {
        transform.setOrigin(0, 1.1f);
    }

    @Test
    public void testSetOriginPx() {
        transform.setSize(10, 10);
        transform.setOriginPx(2, 3);
        assertEquals(0.2f, transform.getOriginX(), EPSILON);
        assertEquals(0.3f, transform.getOriginY(), EPSILON);
    }

    @Test(expected = IllegalStateException.class)
    public void testSetOriginPxWithoutSize() {
        transform.setOriginPx(10, 10);
    }
}
