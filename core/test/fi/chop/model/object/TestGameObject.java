package fi.chop.model.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGameObject {

    private static final float EPSILON = 0.000001f;
    private GameObject object;

    @Before
    public void setUp() {
        object = new GameObject(null, null, null) {
            @Override
            public void load() {}
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
            @Override
            public void dispose() { }
        };
    }

    @Test
    public void testDefaultValues() {
        assertEquals(-1, object.getID(), EPSILON);

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
    public void testDie() {
        object.die();
        assertTrue(object.isDead());
    }
}
