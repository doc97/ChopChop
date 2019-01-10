package fi.chop.model;

import fi.chop.MathUtil;

import java.util.Random;

public class PowerBar {

    private Random random;
    private float durationSec;
    private float value;
    private float multiplier;

    public PowerBar() {
        durationSec = 1;
        multiplier = 1;
        random = new Random();
    }

    public void randomize() {
        value = random.nextFloat();
    }

    public void update(float delta) {
        value = MathUtil.lerp(0, 1, value + multiplier * delta / durationSec);
        double floored = Math.floor(value);
        if (floored % 2 == 1) {
            multiplier *= -1;
            value = 1 - (value % 1.0f);
        } else if (floored % 2 == - 1) {
            multiplier *= -1;
            value = -value;
        }
        value %= 1.0f;
    }

    public float getValue() {
        return value;
    }

    public void setDurationSec(float seconds) {
        if (seconds <= 0)
            throw new IllegalArgumentException("Duration must be positive!");
        durationSec = seconds;
    }

    public float getDurationSec() {
        return durationSec;
    }
}
