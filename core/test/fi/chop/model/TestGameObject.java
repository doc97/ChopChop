package fi.chop.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.object.GameObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGameObject {

    private static final float EPSILON = 0.000001f;
    private GameObject object;

    @Before
    public void setUp() {
        object = new GameObject(null) {
            @Override
            public void load() {}

            @Override
            public void update(float delta) { }

            @Override
            public void render(SpriteBatch batch) { }
        };
    }

    @Test
    public void testDefaultValues() {
        assertEquals(0, object.getX(), EPSILON);
        assertEquals(0, object.getY(), EPSILON);
        assertEquals(0, object.getRotationRad(), EPSILON);
        assertEquals(0, object.getRotationDeg(), EPSILON);
        assertEquals(0, object.getWidth(), EPSILON);
        assertEquals(0, object.getHeight(), EPSILON);
    }

    @Test
    public void testSetPosition() {
        object.setPosition(-1, -1);
        assertEquals(-1, object.getX(), EPSILON);
        assertEquals(-1, object.getY(), EPSILON);
        object.setPosition(1, 1);
        assertEquals(1, object.getX(), EPSILON);
        assertEquals(1, object.getY(), EPSILON);
    }

    @Test
    public void testSetX() {
        object.setX(-1);
        assertEquals(-1, object.getX(), EPSILON);
        object.setX(1);
        assertEquals(1, object.getX(), EPSILON);
    }

    @Test
    public void testSetY() {
        object.setY(-1);
        assertEquals(-1, object.getY(), EPSILON);
        object.setY(1);
        assertEquals(1, object.getY(), EPSILON);
    }

    @Test
    public void testTranslate() {
        object.translate(-10, 5);
        assertEquals(-10, object.getX(), EPSILON);
        assertEquals(5, object.getY(), EPSILON);
        object.translate(3, -2);
        assertEquals(-7, object.getX(), EPSILON);
        assertEquals(3, object.getY(), EPSILON);
    }

    @Test
    public void testSetRotationDeg() {
        object.setRotationDeg(45);
        assertEquals(45, object.getRotationDeg(), EPSILON);
        assertEquals(Math.PI / 4, object.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetRotationDegNegative() {
        object.setRotationDeg(-90);
        assertEquals(270, object.getRotationDeg(), EPSILON);
        assertEquals(Math.PI * 3 / 2, object.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetRotationDegTooBig() {
        object.setRotationDeg(450);
        assertEquals(90, object.getRotationDeg(), EPSILON);
        assertEquals(Math.PI / 2, object.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetRotationRad() {
        object.setRotationRad(Math.PI / 2);
        assertEquals(90, object.getRotationDeg(), EPSILON);
        assertEquals(Math.PI / 2, object.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetRotationRadNegative() {
        object.setRotationRad(-Math.PI / 2);
        assertEquals(270, object.getRotationDeg(), EPSILON);
        assertEquals(Math.PI * 3 / 2, object.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetRotationRadTooBig() {
        object.setRotationRad(3 * Math.PI);
        assertEquals(180, object.getRotationDeg(), EPSILON);
        assertEquals(Math.PI, object.getRotationRad(), EPSILON);
    }

    @Test
    public void testRotateDeg() {
        object.rotateDeg(45);
        assertEquals(45, object.getRotationDeg(), EPSILON);
        object.rotateDeg(34);
        assertEquals(79, object.getRotationDeg(), EPSILON);
        object.rotateDeg(-80);
        assertEquals(359, object.getRotationDeg(), EPSILON);
    }

    @Test
    public void testRotateRad() {
        object.rotateRad(Math.PI);
        assertEquals(Math.PI, object.getRotationRad(), EPSILON);
        object.rotateRad(Math.PI / 2);
        assertEquals(Math.PI * 3 / 2, object.getRotationRad(), EPSILON);
        object.rotateRad(-2 * Math.PI);
        assertEquals(Math.PI * 3 / 2, object.getRotationRad(), EPSILON);
    }

    @Test
    public void testSetWidth() {
        object.setWidth(10);
        assertEquals(10, object.getWidth(),EPSILON);
        object.setWidth(20);
        assertEquals(20, object.getWidth(),EPSILON);
    }

    @Test
    public void testSetHeight() {
        object.setHeight(10);
        assertEquals(10, object.getHeight(), EPSILON);
        object.setHeight(20);
        assertEquals(20, object.getHeight(), EPSILON);
    }

    @Test
    public void testSetSize() {
        object.setSize(10, 20);
        assertEquals(10, object.getWidth(), EPSILON);
        assertEquals(20, object.getHeight(), EPSILON);
        object.setSize(30, 40);
        assertEquals(30, object.getWidth(), EPSILON);
        assertEquals(40, object.getHeight(), EPSILON);
    }
}
