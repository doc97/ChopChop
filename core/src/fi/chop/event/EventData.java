package fi.chop.event;

public class EventData<T> {

    private T data;

    public EventData(T data) {
        this.data = data;
    }

    public T get() {
        return data;
    }
}
