package fi.chop.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.object.GameObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class TestScene {

    private class TestObject extends GameObject {
        private int updateCalls;
        private int renderCalls;
        private boolean dieFirstUpdate;

        private TestObject() {
            super(null);
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

    private Scene scene;

    @Before
    public void setUp() {
        scene = new Scene();
    }

    @Test
    public void testDefaultValues() {
        assertTrue(scene.isEmpty());
    }

    @Test
    public void testAddWithoutUpdate() {
        scene.add(new TestObject());
        assertTrue(scene.isEmpty());
    }

    @Test
    public void testAddWithUpdate() {
        scene.add(new TestObject());
        scene.update(0);
        assertFalse(scene.isEmpty());
        assertEquals(1, scene.getObjectCount());
    }

    @Test
    public void testAddWithTwoUpdates() {
        scene.add(new TestObject());
        scene.update(0);
        scene.update(0);
        assertEquals(1, scene.getObjectCount());
    }

    @Test
    public void testAddTwoObjects() {
        scene.add(new TestObject(), new TestObject());
        scene.update(0);
        assertEquals(2, scene.getObjectCount());
    }

    @Test
    public void testAddTwoSeparateObjects() {
        scene.add(new TestObject());
        scene.add(new TestObject());
        scene.update(0);
        assertEquals(2, scene.getObjectCount());
    }

    @Test
    public void testUpdateSetsID() {
        TestObject obj = new TestObject();
        scene.add(obj);
        assertEquals(-1, obj.getID());
        scene.update(0);
        assertEquals(0, obj.getID());
    }

    @Test
    public void testUpdateIncrementsID() {
        TestObject obj = new TestObject();
        scene.add(new TestObject(), obj);
        scene.update(0);
        assertEquals(1, obj.getID());
    }

    @Test
    public void testClear() {
        scene.add(new TestObject(), new TestObject());
        scene.update(0);
        scene.clear();
        assertTrue(scene.isEmpty());
        assertEquals(0, scene.getObjectCount());
    }

    @Test
    public void testAddAndClearBeforeUpdate() {
        scene.add(new TestObject());
        scene.clear();
        scene.update(0);
        assertTrue(scene.isEmpty());
    }

    @Test
    public void testClearAndAdd() {
        TestObject obj = new TestObject();
        scene.add(new TestObject(), new TestObject());
        scene.update(0);
        scene.clear();
        scene.add(obj);
        scene.update(0);
        assertEquals(2, obj.getID());
    }

    @Test
    public void testUpdate() {
        TestObject obj = new TestObject();
        scene.add(obj);
        scene.update(0);
        assertEquals(1, obj.updateCalls);
        scene.update(0);
        assertEquals(2, obj.updateCalls);
    }

    @Test
    public void testRender() {
        TestObject obj = new TestObject();
        scene.add(obj);
        scene.update(0);
        scene.render(null);
        assertEquals(1, obj.renderCalls);
        scene.render(null);
        assertEquals(2, obj.renderCalls);
    }

    @Test
    public void testUpdateRemoveDead() {
        scene.add(new TestObject(true));
        scene.update(0);
        assertTrue(scene.isEmpty());
    }

    @Test
    public void testFindOne() {
        TestObject first = new TestObject();
        scene.add(first);
        scene.add(new TestObject());
        scene.update(0);

        TestObject obj = scene.findOne(TestObject.class);
        assertSame(first, obj);
    }

    @Test
    public void testFindOneEmptyScene() {
        assertNull(scene.findOne(TestObject.class));
    }

    @Test
    public void testFindOneOfTypeTestObject() {
        GameObject obj = new GameObject(null) {
            @Override
            public void load() { }
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
        };
        TestObject test = new TestObject();
        scene.add(obj, test);
        scene.update(0);
        TestObject actual = scene.findOne(TestObject.class);
        assertSame(test, actual);
    }

    @Test
    public void testFindOneOfTypeGameObject() {
        GameObject obj = new GameObject(null) {
            @Override
            public void load() { }
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
        };
        scene.add(obj, new TestObject());
        scene.update(0);
        GameObject actual = scene.findOne(GameObject.class);
        assertSame(obj, actual);
    }

    @Test
    public void testFindAll() {
        scene.add(new TestObject(), new TestObject());
        scene.update(0);
        List<TestObject> list = scene.findAll(TestObject.class);
        assertEquals(2, list.size());
    }

    @Test
    public void testFindAllEmptyScene() {
        assertEquals(0, scene.findAll(TestObject.class).size());
    }

    @Test
    public void testFindAllOfTypeTestObject() {
        GameObject obj = new GameObject(null) {
            @Override
            public void load() { }
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
        };
        TestObject test = new TestObject();
        scene.add(obj, test);
        scene.update(0);
        assertEquals(1, scene.findAll(TestObject.class).size());
    }

    @Test
    public void testFindAllOfTypeGameObject() {
        GameObject obj = new GameObject(null) {
            @Override
            public void load() { }
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
        };
        scene.add(obj, new TestObject());
        scene.update(0);
        assertEquals(2, scene.findAll(GameObject.class).size());
    }

    @Test
    public void testFindOneWithID() {
        scene.add(new TestObject(), new TestObject());
        scene.update(0);
        GameObject obj0 = scene.findOne(o -> o.getID() == 0);
        assertEquals(0, obj0.getID());
        GameObject obj1 = scene.findOne(o -> o.getID() == 1);
        assertEquals(1, obj1.getID());
        assertNotSame(obj0, obj1);
    }

    @Test
    public void testFindOnePredicate() {
        TestObject obj1 = new TestObject();
        obj1.setPosition(10, 5);
        TestObject obj2 = new TestObject();
        obj2.setPosition(10, 10);
        scene.add(obj1, obj2);
        scene.update(0);
        GameObject obj = scene.findOne(o -> o.getX() == 10 && o.getY() == 10);
        assertSame(obj2, obj);
        assertNull(scene.findOne(o -> o.getX() == 10 && o.getY() == 10 && o.getID() == 0));
    }

    @Test
    public void testFindOnePredicateChaining() {
        TestObject obj1 = new TestObject();
        obj1.setPosition(10, 5);
        TestObject obj2 = new TestObject();
        obj2.setPosition(10, 10);
        scene.add(obj1, obj2);
        scene.update(0);
        Predicate<GameObject> xPred = o -> o.getX() == 10;
        Predicate<GameObject> yPred = o -> o.getY() == 10;
        GameObject obj = scene.findOne(xPred.and(yPred));
        assertSame(obj2, obj);
    }

    @Test
    public void testFindAllPredicate() {
        TestObject obj1 = new TestObject();
        obj1.setRotationDeg(45);
        TestObject obj2 = new TestObject();
        obj2.setRotationDeg(45);
        GameObject obj3 = new GameObject(null) {
            @Override
            public void load() { }
            @Override
            public void update(float delta) { }
            @Override
            public void render(SpriteBatch batch) { }
        };
        obj3.setRotationDeg(45);

        scene.add(new TestObject(), obj1, new TestObject(), new TestObject(), obj2, new TestObject(), obj3);
        scene.update(0);
        List<GameObject> result = scene.findAll(o -> o.getRotationDeg() == 45 && o instanceof TestObject);
        assertEquals(2, result.size());
        assertSame(obj1, result.get(0));
        assertSame(obj2, result.get(1));
    }
}
