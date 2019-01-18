package fi.chop.model.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.object.GameObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGameObject {

    private static final float EPSILON = 0.000001f;
    private GameObject object;

    @Before
    public void setUp() {
        object = new GameObject(null, null) {
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
        assertEquals(-1, object.getID(), EPSILON);
        assertEquals(0, object.getX(), EPSILON);
        assertEquals(0, object.getY(), EPSILON);
        assertEquals(0, object.getRotationRad(), EPSILON);
        assertEquals(0, object.getRotationDeg(), EPSILON);
        assertEquals(0, object.getWidth(), EPSILON);
        assertEquals(0, object.getHeight(), EPSILON);
        assertEquals(1, object.getScaleX(), EPSILON);
        assertEquals(1, object.getScaleY(), EPSILON);
        assertEquals(0, object.getOriginX(), EPSILON);
        assertEquals(0, object.getOriginY(), EPSILON);
        assertFalse(object.isDead());
    }

    @Test
    public void testSetID() {
        object.setID(0);
        assertEquals(0, object.getID());
        object.setID(1);
        assertEquals(1, object.getID());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIDNegative() {
        object.setID(-1);
    }

    @Test
    public void testInvalidateID() {
        object.setID(2);
        object.invalidateID();
        assertEquals(-1, object.getID());

    }

    @Test
    public void testGrowValidID() {
        object.setID(2);
        object.growID();
        assertEquals(3, object.getID());
        object.growID();
        assertEquals(4, object.getID());
    }

    @Test
    public void testGrowInvalidID() {
        object.growID();
        assertEquals(-1, object.getID());
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

    @Test
    public void testSetScaleX() {
        object.setScaleX(1.1f);
        assertEquals(1.1f, object.getScaleX(), EPSILON);
        object.setScaleX(1.2f);
        assertEquals(1.2f, object.getScaleX(), EPSILON);
    }

    @Test
    public void testScaleY() {
        object.setScaleY(1.1f);
        assertEquals(1.1f, object.getScaleY(), EPSILON);
        object.setScaleY(1.2f);
        assertEquals(1.2f, object.getScaleY(), EPSILON);
    }

    @Test
    public void testSetScale() {
        object.setScale(1.2f, 1.3f);
        assertEquals(1.2f, object.getScaleX(), EPSILON);
        assertEquals(1.3f, object.getScaleY(), EPSILON);
        object.setScale(1.4f, 1.5f);
        assertEquals(1.4f, object.getScaleX(), EPSILON);
        assertEquals(1.5f, object.getScaleY(), EPSILON);
    }

    @Test
    public void testScale() {
        object.setScale(2, 2);
        object.scale(2, 2);
        assertEquals(4, object.getScaleX(), EPSILON);
        assertEquals(4, object.getScaleY(), EPSILON);
        object.scale(0.25f, 0.25f);
        assertEquals(1, object.getScaleX(), EPSILON);
        assertEquals(1, object.getScaleY(), EPSILON);
    }

    @Test
    public void testScaleNegative() {
        object.setScale(2, 2);
        object.scale(-1, -2);
        assertEquals(-2, object.getScaleX(), EPSILON);
        assertEquals(-4, object.getScaleY(), EPSILON);
    }

    @Test
    public void testSetOrigin() {
        object.setOrigin(0.5f, 0.4f);
        assertEquals(0.5f, object.getOriginX(), EPSILON);
        assertEquals(0.4f, object.getOriginY(), EPSILON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeOriginX() {
        object.setOrigin(-1, 0.5f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeOriginY() {
        object.setOrigin(0.5f, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetOriginXGreaterThanOne() {
        object.setOrigin(1.1f, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetOriginYGreaterThanOne() {
        object.setOrigin(0, 1.1f);
    }

    @Test
    public void testSetOriginPx() {
        object.setSize(10, 10);
        object.setOriginPx(2, 3);
        assertEquals(0.2f, object.getOriginX(), EPSILON);
        assertEquals(0.3f, object.getOriginY(), EPSILON);
    }

    @Test(expected = IllegalStateException.class)
    public void testSetOriginPxWithoutSize() {
        object.setOriginPx(10, 10);
    }

    @Test
    public void testDie() {
        object.die();
        assertTrue(object.isDead());
    }
}
