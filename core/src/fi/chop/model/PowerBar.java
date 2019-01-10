package fi.chop.model;

import fi.chop.MathUtil;

import java.util.Random;

public class PowerBar {

    private Random random;
    private float durationSec;
    private float value;
    private float multiplier;

    public PowerBar(float durationSec) {
        this.durationSec = durationSec;
        multiplier = 1;
        random = new Random();
    }

    public void randomize() {
        value = random.nextFloat();
    }

    public void update(float delta) {
        value = MathUtil.lerp(0, 1, value + multiplier * delta);
        if (Math.floor(value) % 2 == 1) {
            multiplier *= -1;
            if (value > 0)
                value = 1 - (value % 1.0f);
            else
                value = -value;
        }
        value %= 1.0f;
    }

    public float getValue() {
        return value;
    }
}
