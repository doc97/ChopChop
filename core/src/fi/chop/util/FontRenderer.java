package fi.chop.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.utils.Align;

public class FontRenderer {

    private boolean wrap;
    private float widthPx;
    private int hAlign;
    private String text;
    private Color tint;
    private BitmapFontCache cache;

    public FontRenderer(BitmapFont font) {
        text = "";
        cache = new BitmapFontCache(font);
        tint = new Color(Color.WHITE);
    }

    public FontRenderer text(String text, float widthPx, int hAlign, boolean wrap) {
        if (!this.text.equals(text) || this.widthPx != widthPx || this.hAlign != hAlign || this.wrap != wrap) {
            this.text = text == null ? "" : text;
            this.widthPx = widthPx;
            this.hAlign = hAlign;
            this.wrap = wrap;
            cache.setText(this.text, 0, 0, widthPx, hAlign, wrap);
        }
        return this;
    }

    public FontRenderer text(String text) {
        return text(text, 0, Align.left, false);
    }

    public FontRenderer edit(String text) {
        BitmapFontCache oldCache = cache;
        text(text, widthPx, hAlign, wrap).pos(oldCache.getX(), oldCache.getY()).tint(oldCache.getColor());
        return this;
    }

    public FontRenderer pos(float x, float y) {
        cache.setPosition(x, y);
        return this;
    }

    public FontRenderer x(float x) {
        cache.setPosition(x, cache.getY());
        return this;
    }

    public FontRenderer y(float y) {
        cache.setPosition(cache.getX(), y);
        return this;
    }

    public FontRenderer translate(float dx, float dy) {
        cache.translate(dx, dy);
        return this;
    }

    public FontRenderer center(Camera cam, boolean centerX, boolean centerY) {
        float x = cache.getX();
        float y = cache.getY();
        if (centerX)
            x = cam.viewportWidth / 2 - width() / 2;
        if (centerY)
            y = cam.viewportHeight / 2 - height() / 2;
        cache.setPosition(x, y);
        return this;
    }

    public FontRenderer tint(Color tint) {
        if (tint != null)
            this.tint = tint;
        return this;
    }

    public void draw(Batch batch) {
        cache.tint(tint.cpy().mul(batch.getColor()));
        cache.draw(batch);
    }

    public float getLineHeight() {
        return cache.getFont().getLineHeight();
    }

    public float width() {
        if (isEmpty())
            return 0;
        return cache.getLayouts().get(0).width;
    }

    public float height() {
        if (isEmpty())
            return 0;
        return cache.getLayouts().get(0).height;
    }

    public String str() {
        return text;
    }

    private boolean isEmpty() {
        return cache.getLayouts().isEmpty();
    }
}
