package fi.chop.event;

import java.util.*;

public class EventSystem {

    private class Event {
        private Events type;
        private EventData data;

        public Event(Events type, EventData data) {
            this.type = type;
            this.data = data;
        }
    }

    private final Map<Events, ArrayList<EventListener>> listeners;
    private final Queue<Event> queue;

    public EventSystem() {
        listeners = new EnumMap<>(Events.class);
        queue = new ArrayDeque<>();
    }

    public void notify(Events event) {
        notify(event, null);
    }

    public void notify(Events event, EventData data) {
        queue.add(new Event(event, data));
    }

    public void update() {
        Event evt;
        while ((evt = queue.poll()) != null) {
            if (!listeners.containsKey(evt.type))
                continue;
            for (EventListener listener : listeners.get(evt.type))
                listener.handle(evt.type, evt.data);
        }
    }

    public void clear() {
        listeners.clear();
    }

    public void clear(Events... events) {
        for (Events event : events) {
            if (listeners.containsKey(event))
                listeners.get(event).clear();
            listeners.remove(event);
        }
    }

    public void addListener(EventListener listener, Events... events) {
        for (Events event : events)
            addListener(event, listener);
    }

    public void addListener(Events event, EventListener listener) {
        if (!listeners.containsKey(event))
            listeners.put(event, new ArrayList<>());
        if (!listeners.get(event).contains(listener))
            listeners.get(event).add(listener);
    }

    public void removeListener(EventListener listener) {
        Iterator<Map.Entry<Events, ArrayList<EventListener>>> it;
        for (it = listeners.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Events, ArrayList<EventListener>> entry = it.next();
            entry.getValue().removeIf(elem -> elem.equals(listener));
            if (entry.getValue().isEmpty())
                it.remove();
        }
    }
}
