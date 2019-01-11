package fi.chop.event;

public interface EventListener {
    void handle(Events event, EventData data);
}