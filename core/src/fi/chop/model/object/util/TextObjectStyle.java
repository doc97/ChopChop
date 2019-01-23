package fi.chop.model.object.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextObjectStyle {

    private TextureRegion bgTexture;
    private Color bgColor;
    private Color tint;

    public TextObjectStyle() {
        bgColor = new Color(Color.CLEAR);
        tint = new Color(Color.WHITE);
    }

    public void set(TextObjectStyle style) {
        bgTexture = style.bgTexture;
        bgColor = style.bgColor;
        tint = style.tint;
    }

    public TextObjectStyle tint(Color tint) {
        this.tint = tint == null ? new Color(Color.WHITE) : new Color(tint);
        return this;
    }

    public TextObjectStyle bgColor(Color bgColor) {
        this.bgColor = bgColor == null ? new Color(Color.CLEAR) : new Color(bgColor);
        return this;
    }

    public TextObjectStyle bgTexture(TextureRegion bgTexture) {
        this.bgTexture = bgTexture;
        return this;
    }

    public Color getTint() {
        return tint;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public TextureRegion getBgTexture() {
        return bgTexture;
    }
}
