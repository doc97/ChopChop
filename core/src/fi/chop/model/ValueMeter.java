package fi.chop.model;

public class ValueMeter {

    private float value;

    public void set(float value) {
        if (value < 0 || value > 1)
            throw new IllegalArgumentException("value must be between 0 and 1");
        this.value = value;
    }

    public void add(float delta) {
        this.value = Math.min(Math.max(value + delta, 0), 1);
    }

    public float getFillPercentage() {
        return value;
    }
}
