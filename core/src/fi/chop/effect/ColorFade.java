package fi.chop.effect;

import com.badlogic.gdx.graphics.Color;
import fi.chop.functional.Procedure;
import fi.chop.util.MathUtil;

import java.util.function.Function;

public class ColorFade {

    private float duration;
    private float time;
    private Color start;
    private Color end;
    private Color current;
    private Function<Float, Float> func;
    private Procedure onFinish;

    public ColorFade(Color start, Color end, float duration) {
        this(start, end, duration, (t) -> MathUtil.lerp(0, 1, t));
    }

    public ColorFade(Color start, Color end, float duration, Function<Float, Float> func) {
        this.current = new Color(start);
        this.start = new Color(start);
        this.end = new Color(end);
        this.duration = duration;
        this.func = func;
    }

    public ColorFade onFinish(Procedure onFinish) {
        this.onFinish = onFinish;
        return this;
    }

    public void update(float delta) {
        if (hasFinished())
            return;

        time = Math.min(time + delta / duration, 1);
        float percent = func.apply(time);
        current.set(
                MathUtil.lerp(start.r, end.r, percent),
                MathUtil.lerp(start.g, end.g, percent),
                MathUtil.lerp(start.b, end.b, percent),
                MathUtil.lerp(start.a, end.a, percent)
        );

        if (hasFinished() && onFinish != null)
            onFinish.run();
    }

    public void flip() {
        current.set(end);
        end.set(start);
        start.set(current);
        time = 0;
    }

    public Color getColor() {
        return new Color(current);
    }

    public boolean hasFinished() {
        return time == 1;
    }
}
