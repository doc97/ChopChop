package fi.chop.model;

import com.badlogic.gdx.Input.Keys;
import fi.chop.model.InputMap.Action;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestInputMap {

    private InputMap map;

    @Before
    public void setUp() {
        map = new InputMap();
    }

    @Test
    public void testDefaultValues() {
        assertEquals(map.getKeyCode(Action.INTERACT), Keys.SPACE);
        assertEquals(map.getKeyCode(Action.BACK), Keys.ESCAPE);
        assertEquals(map.getAction(Keys.SPACE), Action.INTERACT);
        assertEquals(map.getAction(Keys.ESCAPE), Action.BACK);
    }

    @Test
    public void testBind() {
        map.bind(Action.INTERACT, Keys.A);
        map.bind(Action.BACK, Keys.B);
        assertEquals(map.getKeyCode(Action.INTERACT), Keys.A);
        assertEquals(map.getKeyCode(Action.BACK), Keys.B);
        assertEquals(map.getAction(Keys.A), Action.INTERACT);
        assertEquals(map.getAction(Keys.B), Action.BACK);
        assertEquals(map.getAction(Keys.SPACE), Action.NONE);
        assertEquals(map.getAction(Keys.ESCAPE), Action.NONE);
    }

    @Test
    public void testDefaults() {
        map.bind(Action.INTERACT, Keys.A);
        map.bind(Action.BACK, Keys.B);
        map.defaults();
        assertEquals(map.getKeyCode(Action.INTERACT), Keys.SPACE);
        assertEquals(map.getKeyCode(Action.BACK), Keys.ESCAPE);
        assertEquals(map.getAction(Keys.SPACE), Action.INTERACT);
        assertEquals(map.getAction(Keys.ESCAPE), Action.BACK);
    }

    @Test
    public void testUnbind() {
        map.unbind(Action.INTERACT);
        assertEquals(map.getKeyCode(Action.INTERACT), -1);
        assertEquals(map.getAction(Keys.SPACE), Action.NONE);
        map.unbind(Action.BACK);
        assertEquals(map.getKeyCode(Action.BACK), -1);
        assertEquals(map.getAction(Keys.ESCAPE), Action.NONE);
    }

    @Test
    public void testGetAction() {
        assertEquals(map.getAction(Keys.SPACE), Action.INTERACT);
        assertEquals(map.getAction(Keys.ESCAPE), Action.BACK);
    }
}
