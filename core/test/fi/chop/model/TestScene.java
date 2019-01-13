package fi.chop.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestScene {

    private class TestLayer extends Layer {
        private int updateOrder;
        private int updateCalls;
        private int renderOrder;
        private int renderCalls;

        @Override
        public void update(float delta) {
            updateOrder = controlUpdate;
            controlUpdate++;
            updateCalls++;
        }

        @Override
        public void render(SpriteBatch batch) {
            renderOrder = controlRender;
            controlRender++;
            renderCalls++;
        }
    }

    private Scene scene;
    private int controlUpdate;
    private int controlRender;

    @Before
    public void setUp() {
        scene = new Scene();
        controlUpdate = 0;
    }

    @Test
    public void testDefaultValues() {
        assertEquals(0, scene.getLayerCount());
        assertEquals(0, scene.getLayerNames().length);
        assertEquals(-1, scene.getLayerOrder("layer 1"));
    }

    @Test
    public void testAddLayer() {
        scene.addLayer("layer 1", new Layer());
        assertEquals(1, scene.getLayerCount());
        assertEquals(0, scene.getLayerOrder("layer 1"));
        assertEquals("layer 1", scene.getLayerNames()[0]);
    }

    @Test
    public void testAddTwoLayers() {
        scene.addLayer("layer 1", new Layer());
        scene.addLayer("layer 2", new Layer());
        assertEquals(2, scene.getLayerCount());
        assertEquals(0, scene.getLayerOrder("layer 1"));
        assertEquals(1, scene.getLayerOrder("layer 2"));
        assertEquals("layer 1", scene.getLayerNames()[0]);
        assertEquals("layer 2", scene.getLayerNames()[1]);
    }

    @Test
    public void testAddTwoLayersWithSameName() {
        scene.addLayer("layer 1", new Layer());
        scene.addLayer("layer 1", new Layer());
        assertEquals(1, scene.getLayerCount());
        scene.addLayer("layer 2", new Layer());
        scene.addLayer("layer 2", new Layer());
        assertEquals(2, scene.getLayerCount());
        assertEquals(0, scene.getLayerOrder("layer 1"));
        assertEquals(1, scene.getLayerOrder("layer 2"));
        assertArrayEquals(new String[] { "layer 1", "layer 2" }, scene.getLayerNames());
    }

    @Test
    public void testUpdateOneLayer() {
        TestLayer layer = new TestLayer();
        scene.addLayer("layer 1", layer);
        scene.update(0);
        assertEquals(1, layer.updateCalls);
    }

    @Test
    public void testUpdateTwoLayers() {
        TestLayer layer1 = new TestLayer();
        TestLayer layer2 = new TestLayer();
        scene.addLayer("layer 1", layer1);
        scene.addLayer("layer 2", layer2);
        scene.update(0);
        assertEquals(1, layer1.updateCalls);
        assertEquals(1, layer2.updateCalls);
    }

    @Test
    public void testUpdateTwoLayersWithSameName() {
        TestLayer layer1 = new TestLayer();
        TestLayer layer2 = new TestLayer();
        scene.addLayer("layer 1", layer1);
        scene.addLayer("layer 1", layer2);
        scene.update(0);
        assertEquals(0, layer1.updateCalls);
        assertEquals(1, layer2.updateCalls);
    }

    @Test
    public void testUpdateOrder() {
        TestLayer layer1 = new TestLayer();
        TestLayer layer2 = new TestLayer();
        scene.addLayer("layer 1", layer1);
        scene.addLayer("layer 2", layer2);
        scene.update(0);
        assertEquals(0, layer1.updateOrder);
        assertEquals(1, layer2.updateOrder);
    }

        @Test
    public void testRenderOneLayer() {
        TestLayer layer = new TestLayer();
        scene.addLayer("layer 1", layer);
        scene.render(null);
        assertEquals(1, layer.renderCalls);
    }

    @Test
    public void testRenderTwoLayers() {
        TestLayer layer1 = new TestLayer();
        TestLayer layer2 = new TestLayer();
        scene.addLayer("layer 1", layer1);
        scene.addLayer("layer 2", layer2);
        scene.render(null);
        assertEquals(1, layer1.renderCalls);
        assertEquals(1, layer2.renderCalls);
    }

    @Test
    public void testRenderTwoLayersWithSameName() {
        TestLayer layer1 = new TestLayer();
        TestLayer layer2 = new TestLayer();
        scene.addLayer("layer 1", layer1);
        scene.addLayer("layer 1", layer2);
        scene.render(null);
        assertEquals(0, layer1.renderCalls);
        assertEquals(1, layer2.renderCalls);
    }

    @Test
    public void testRenderOrder() {
        TestLayer layer1 = new TestLayer();
        TestLayer layer2 = new TestLayer();
        scene.addLayer("layer 1", layer1);
        scene.addLayer("layer 2", layer2);
        scene.render(null);
        assertEquals(0, layer1.renderOrder);
        assertEquals(1, layer2.renderOrder);
    }

    @Test
    public void testMoveUp() {
        scene.addLayer("layer 1", new Layer());
        scene.addLayer("layer 2", new Layer());
        scene.moveUp("layer 1");
        assertEquals(0, scene.getLayerOrder("layer 2"));
        assertEquals(1, scene.getLayerOrder("layer 1"));
        assertArrayEquals(new String[] { "layer 2", "layer 1" }, scene.getLayerNames());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveUpInvalidName() {
        scene.moveUp("layer 1");
    }

    @Test
    public void testMoveDown() {
        scene.addLayer("layer 1", new Layer());
        scene.addLayer("layer 2", new Layer());
        scene.moveDown("layer 2");
        assertEquals(0, scene.getLayerOrder("layer 2"));
        assertEquals(1, scene.getLayerOrder("layer 1"));
        assertArrayEquals(new String[] { "layer 2", "layer 1" }, scene.getLayerNames());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveDownInvalidName() {
        scene.moveDown("layer 1");
    }

    @Test
    public void testMoveTop() {
        scene.addLayer("layer 1", new Layer());
        scene.addLayer("layer 2", new Layer());
        scene.addLayer("layer 3", new Layer());
        scene.moveTop("layer 1");
        assertEquals(0, scene.getLayerOrder("layer 2"));
        assertEquals(1, scene.getLayerOrder("layer 3"));
        assertEquals(2, scene.getLayerOrder("layer 1"));
        assertArrayEquals(new String[] { "layer 2", "layer 3", "layer 1" }, scene.getLayerNames());
        scene.moveTop("layer 2");
        assertEquals(0, scene.getLayerOrder("layer 3"));
        assertEquals(1, scene.getLayerOrder("layer 1"));
        assertEquals(2, scene.getLayerOrder("layer 2"));
        assertArrayEquals(new String[] { "layer 3", "layer 1", "layer 2" }, scene.getLayerNames());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveTopInvalidName() {
        scene.moveTop("layer 1");
    }

        @Test
    public void testMoveBottom() {
        scene.addLayer("layer 1", new Layer());
        scene.addLayer("layer 2", new Layer());
        scene.addLayer("layer 3", new Layer());
        scene.moveBottom("layer 3");
        assertEquals(0, scene.getLayerOrder("layer 3"));
        assertEquals(1, scene.getLayerOrder("layer 1"));
        assertEquals(2, scene.getLayerOrder("layer 2"));
        assertArrayEquals(new String[] { "layer 3", "layer 1", "layer 2" }, scene.getLayerNames());
        scene.moveBottom("layer 2");
        assertEquals(0, scene.getLayerOrder("layer 2"));
        assertEquals(1, scene.getLayerOrder("layer 3"));
        assertEquals(2, scene.getLayerOrder("layer 1"));
        assertArrayEquals(new String[] { "layer 2", "layer 3", "layer 1" }, scene.getLayerNames());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveBottomInvalidName() {
        scene.moveBottom("layer 1");
    }
}
