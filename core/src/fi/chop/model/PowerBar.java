package fi.chop.model;

import fi.chop.MathUtil;

import java.util.Random;

public class PowerBar {

    private final Random random;
    private float durationSec;
    private float time;
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
        time += multiplier * delta;
        float timePercent = time / durationSec;

        if (Math.floor(timePercent) % 2 == 1) {
            multiplier = -1;
            time = durationSec - (time % durationSec);
            timePercent = 1 - (timePercent % 1.0f);
        } else if (Math.floor(timePercent) % 2 == -1) {
            multiplier = 1;
            time = -time;
            timePercent = -timePercent;
        }
        timePercent %= 1.0f;

        value = MathUtil.smoothStartN(timePercent, 3);
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
