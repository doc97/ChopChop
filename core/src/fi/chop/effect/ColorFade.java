package fi.chop.effect;

import com.badlogic.gdx.graphics.Color;
import fi.chop.util.MathUtil;

public class ColorFade {

    private float duration;
    private float time;
    private Color start;
    private Color end;
    private Color current;
    private FadeFunction func;
    private CallbackFunction finish;

    public ColorFade(Color start, Color end, float duration) {
        this(start, end, duration, (t) -> MathUtil.lerp(0, 1, t));
    }

    public ColorFade(Color start, Color end, float duration, FadeFunction func) {
        this.current = new Color(start);
        this.start = new Color(start);
        this.end = new Color(end);
        this.duration = duration;
        this.func = func;
    }

    public ColorFade onFinish(CallbackFunction finish) {
        this.finish = finish;
        return this;
    }

    public void update(float delta) {
        if (hasFinished())
            return;

        time = Math.min(time + delta / duration, 1);
        float percent = func.call(time);
        current.set(
                MathUtil.lerp(start.r, end.r, percent),
                MathUtil.lerp(start.g, end.g, percent),
                MathUtil.lerp(start.b, end.b, percent),
                MathUtil.lerp(start.a, end.a, percent)
        );

        if (hasFinished() && finish != null)
            finish.call();
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
