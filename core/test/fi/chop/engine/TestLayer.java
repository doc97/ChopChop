package fi.chop.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.EventSystem;
import fi.chop.model.object.GameObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class TestLayer {

    private class TestObject extends GameObject {
        private int updateCalls;
        private int renderCalls;
        private boolean dieFirstUpdate;

        private TestObject() {
            super(null, null);
        }

        private TestObject(boolean dieFirstUpdate) {
            this();
            this.dieFirstUpdate = dieFirstUpdate;
        }

        @Override
        public void load() { }

        @Override
        public void update(float delta) {
            updateCalls++;
            if (dieFirstUpdate)
                die();
        }

        @Override
        public void render(SpriteBatch batch) {
            renderCalls++;
        }
    }

    private Layer layer;

    @Before
    public void setUp() {
        Chop.events = new EventSystem();
        layer = new Layer();
    }

    @Test
    public void testDefaultValues() {
        assertTrue(layer.isEmpty());
    }

    @Test
    public void testAddWithoutUpdate() {
        layer.add(new TestObject());
        assertTrue(layer.isEmpty());
    }

    @Test
    public void testAddWithUpdate() {
        layer.add(new TestObject());
        layer.update(0);
        assertFalse(layer.isEmpty());
        assertEquals(1, layer.getObjectCount());
    }

    @Test
    public void testAddWithAddQueued() {
        layer.add(new TestObject());
        layer.addQueued();
        assertFalse(layer.isEmpty());
        assertEquals(1, layer.getObjectCount());
    }

    @Test
    public void testAddWithTwoUpdates() {
        layer.add(new TestObject());
        layer.update(0);
        layer.update(0);
        assertEquals(1, layer.getObjectCount());
    }

    @Test
    public void testAddWithTwoAddQueued() {
        layer.add(new TestObject());
        layer.addQueued();
        layer.addQueued();
        assertEquals(1, layer.getObjectCount());
    }

    @Test
    public void testAddTwoObjects() {
        layer.add(new TestObject(), new TestObject());
        layer.addQueued();
        assertEquals(2, layer.getObjectCount());
    }

    @Test
    public void testAddTwoSeparateObjects() {
        layer.add(new TestObject());
        layer.add(new TestObject());
        layer.addQueued();
        assertEquals(2, layer.getObjectCount());
    }

    @Test
    public void testUpdateSetsID() {
        TestObject obj = new TestObject();
        layer.add(obj);
        assertEquals(-1, obj.getID());
        layer.addQueued();
        assertEquals(0, obj.getID());
    }

    @Test
    public void testUpdateIncrementsID() {
        TestObject obj = new TestObject();
        layer.add(new TestObject(), obj);
        layer.addQueued();
        assertEquals(1, obj.getID());
    }

    @Test
    public void testClear() {
        layer.add(new TestObject(), new TestObject());
        layer.addQueued();
        layer.clear();
        assertTrue(layer.isEmpty());
        assertEquals(0, layer.getObjectCount());
    }

    @Test
    public void testAddAndClearBeforeUpdate() {
        layer.add(new TestObject());
        layer.clear();
        layer.addQueued();
        assertTrue(layer.isEmpty());
    }

    @Test
    public void testClearAndAdd() {
        TestObject obj = new TestObject();
        layer.add(new TestObject(), new TestObject());
        layer.addQueued();
        layer.clear();
        layer.add(obj);
        layer.addQueued();
        assertEquals(2, obj.getID());
    }

    @Test
    public void testKillAll() {
        TestObject obj1 = new TestObject();
        TestObject obj2 = new TestObject();
        obj2.setRotationDeg(45);
        layer.add(obj1, obj2);
        layer.addQueued();
        int count = layer.killAll(o -> o.getRotationDeg() == 45);
        assertEquals(1, count);
        assertFalse(obj1.isDead());
        assertTrue(obj2.isDead());
    }

    @Test
    public void testKillAllTrue() {
        layer.add(new TestObject(), new TestObject(), new TestObject());
        layer.addQueued();
        int count = layer.killAll(o -> true);
        assertEquals(3, count);
        assertEquals(3, layer.getObjectCount());
        layer.update(0);
        assertTrue(layer.isEmpty());
    }

    @Test
    public void testUpdate() {
        TestObject obj = new TestObject();
        layer.add(obj);
        layer.update(0);
        assertEquals(1, obj.updateCalls);
        layer.update(0);
        assertEquals(2, obj.updateCalls);
    }

    @Test
    public void testRender() {
        TestObject obj = new TestObject();
        layer.add(obj);
        layer.addQueued();
        layer.render(null);
        assertEquals(1, obj.renderCalls);
        layer.render(null);
        assertEquals(2, obj.renderCalls);
    }

    @Test
    public void testUpdateRemoveDead() {
        layer.add(new TestObject(true));
        layer.update(0);
        assertTrue(layer.isEmpty());
    }

    @Test
    public void testFindOne() {
        TestObject first = new TestObject();
        layer.add(first);
        layer.add(new TestObject());
        layer.addQueued();

        TestObject obj = layer.findOne(TestObject.class);
        assertSame(first, obj);
    }

    @Test
    public void testFindOneEmptyScene() {
        assertNull(layer.findOne(TestObject.class));
    }

    @Test
    public void testFindOneOfTypeTestObject() {
        GameObject obj = new GameObject(null, null) {
            @Override
            public void load() { }
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
        };
        TestObject test = new TestObject();
        layer.add(obj, test);
        layer.addQueued();
        TestObject actual = layer.findOne(TestObject.class);
        assertSame(test, actual);
    }

    @Test
    public void testFindOneOfTypeGameObject() {
        GameObject obj = new GameObject(null, null) {
            @Override
            public void load() { }
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
        };
        layer.add(obj, new TestObject());
        layer.addQueued();
        GameObject actual = layer.findOne(GameObject.class);
        assertSame(obj, actual);
    }

    @Test
    public void testFindAll() {
        layer.add(new TestObject(), new TestObject());
        layer.addQueued();
        List<TestObject> list = layer.findAll(TestObject.class);
        assertEquals(2, list.size());
    }

    @Test
    public void testFindAllEmptyScene() {
        assertEquals(0, layer.findAll(TestObject.class).size());
    }

    @Test
    public void testFindAllOfTypeTestObject() {
        GameObject obj = new GameObject(null, null) {
            @Override
            public void load() { }
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
        };
        TestObject test = new TestObject();
        layer.add(obj, test);
        layer.addQueued();
        assertEquals(1, layer.findAll(TestObject.class).size());
    }

    @Test
    public void testFindAllOfTypeGameObject() {
        GameObject obj = new GameObject(null, null) {
            @Override
            public void load() { }
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
        };
        layer.add(obj, new TestObject());
        layer.addQueued();
        assertEquals(2, layer.findAll(GameObject.class).size());
    }

    @Test
    public void testFindOneWithID() {
        layer.add(new TestObject(), new TestObject());
        layer.addQueued();
        GameObject obj0 = layer.findOne(o -> o.getID() == 0);
        assertEquals(0, obj0.getID());
        GameObject obj1 = layer.findOne(o -> o.getID() == 1);
        assertEquals(1, obj1.getID());
        assertNotSame(obj0, obj1);
    }

    @Test
    public void testFindOnePredicate() {
        TestObject obj1 = new TestObject();
        obj1.setPosition(10, 5);
        TestObject obj2 = new TestObject();
        obj2.setPosition(10, 10);
        layer.add(obj1, obj2);
        layer.addQueued();
        GameObject obj = layer.findOne(o -> o.getX() == 10 && o.getY() == 10);
        assertSame(obj2, obj);
        assertNull(layer.findOne(o -> o.getX() == 10 && o.getY() == 10 && o.getID() == 0));
    }

    @Test
    public void testFindOnePredicateChaining() {
        TestObject obj1 = new TestObject();
        obj1.setPosition(10, 5);
        TestObject obj2 = new TestObject();
        obj2.setPosition(10, 10);
        layer.add(obj1, obj2);
        layer.addQueued();
        Predicate<GameObject> xPred = o -> o.getX() == 10;
        Predicate<GameObject> yPred = o -> o.getY() == 10;
        GameObject obj = layer.findOne(xPred.and(yPred));
        assertSame(obj2, obj);
    }

    @Test
    public void testFindAllPredicate() {
        TestObject obj1 = new TestObject();
        obj1.setRotationDeg(45);
        TestObject obj2 = new TestObject();
        obj2.setRotationDeg(45);
        GameObject obj3 = new GameObject(null, null) {
            @Override
            public void load() { }
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
        };
        obj3.setRotationDeg(45);

        layer.add(new TestObject(), obj1, new TestObject(), new TestObject(), obj2, new TestObject(), obj3);
        layer.addQueued();
        List<GameObject> result = layer.findAll(o -> o.getRotationDeg() == 45 && o instanceof TestObject);
        assertEquals(2, result.size());
        assertSame(obj1, result.get(0));
        assertSame(obj2, result.get(1));
    }
}
