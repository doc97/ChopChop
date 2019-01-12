package fi.chop.event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

public class EventSystem {

    private final Map<Events, ArrayList<EventListener>> listeners;

    public EventSystem() {
        listeners = new EnumMap<>(Events.class);
    }

    public void notify(Events event) {
        notify(event, null);
    }

    public void notify(Events event, EventData data) {
        if (!listeners.containsKey(event))
            return;
        for (EventListener listener : listeners.get(event))
            listener.handle(event, data);
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
