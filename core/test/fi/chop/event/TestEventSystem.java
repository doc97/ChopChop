package fi.chop.event;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestEventSystem {

    private class TestListener implements  EventListener {
        private EventData data;
        private int count;

        @Override
        public void handle(Events event, EventData data) {
            this.data = data;
            count++;
        }

        private EventData getData() {
            return data;
        }

        private int getCount() {
            return count;
        }

        private boolean hasTriggered() {
            return count > 0;
        }
    }

    private EventSystem system;


    @Before
    public void setUp() {
        system = new EventSystem();
    }

    @Test
    public void testAddListener() {
        TestListener listener = new TestListener();
        system.addListener(Events.ACTION_INTERACT, listener);
        system.notify(Events.ACTION_INTERACT);
        system.update();
        assertTrue(listener.hasTriggered());
    }

    @Test
    public void testAddListenerAnotherEvent() {
        TestListener listener = new TestListener();
        system.addListener(Events.ACTION_BACK, listener);
        system.notify(Events.ACTION_INTERACT);
        system.update();
        assertFalse(listener.hasTriggered());
    }

    @Test
    public void testAddMultipleListeners() {
        TestListener listener1 = new TestListener();
        TestListener listener2 = new TestListener();
        system.addListener(Events.ACTION_BACK, listener1);
        system.addListener(Events.ACTION_BACK, listener2);
        system.notify(Events.ACTION_BACK);
        system.update();
        assertTrue(listener1.hasTriggered());
        assertTrue(listener2.hasTriggered());
    }

    @Test
    public void testAddListenerForMultipleEvents() {
        TestListener listener = new TestListener();
        system.addListener(listener, Events.ACTION_BACK, Events.ACTION_INTERACT);
        system.notify(Events.ACTION_BACK);
        system.notify(Events.ACTION_INTERACT);
        system.update();
        assertEquals(2, listener.getCount());
    }

    @Test
    public void testAddListenerMultipleTimes() {
        TestListener listener = new TestListener();
        system.addListener(Events.ACTION_BACK, listener);
        system.addListener(Events.ACTION_BACK, listener);
        system.notify(Events.ACTION_BACK);
        system.update();
        assertEquals(1, listener.getCount());
    }

    @Test
    public void testRemoveListener() {
        TestListener listener = new TestListener();
        system.addListener(listener, Events.ACTION_BACK, Events.ACTION_INTERACT);
        system.removeListener(listener);
        system.notify(Events.ACTION_BACK);
        system.notify(Events.ACTION_INTERACT);
        system.update();
        assertEquals(0, listener.getCount());
    }

    @Test
    public void testClear() {
        TestListener listener = new TestListener();
        system.addListener(listener, Events.ACTION_BACK, Events.ACTION_INTERACT);
        system.clear();
        system.notify(Events.ACTION_BACK);
        system.update();
        assertFalse(listener.hasTriggered());
    }

    @Test
    public void testClearEvents() {
        TestListener listener = new TestListener();
        system.addListener(listener, Events.ACTION_BACK, Events.ACTION_INTERACT);
        system.clear(Events.ACTION_INTERACT);
        system.notify(Events.ACTION_BACK);
        system.notify(Events.ACTION_INTERACT);
        system.update();
        assertEquals(1, listener.getCount());
    }

    @Test
    public void testNotifyWithData() {
        EventData<Integer> expected = new EventData<>(2);
        TestListener listener = new TestListener();
        system.addListener(Events.ACTION_BACK, listener);
        system.notify(Events.ACTION_BACK, expected);
        system.update();
        EventData data = listener.getData();
        assertSame(data.get().getClass(), Integer.class);
        int dataVal = (Integer) data.get();
        assertEquals(2, dataVal);
    }
}
