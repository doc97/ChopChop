package fi.chop.event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class EventSystem {

    private Map<Events, ArrayList<EventListener>> listeners;

    public EventSystem() {
        listeners = new EnumMap<>(Events.class);
    }

    public void notify(Events event) {
        if (!listeners.containsKey(event))
            return;
        for (EventListener listener : listeners.get(event))
            listener.handle(event);
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
}
