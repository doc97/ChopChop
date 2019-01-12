package fi.chop.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class DrawUtil {

    public static void drawCenteredText(Batch batch, BitmapFont font, CharSequence text, Color color, float x, float y) {
        GlyphLayout layout = new GlyphLayout(font, text);
        float drawX = x - layout.width / 2;
        float drawY = y - layout.height / 2;
        font.getCache().clear();
        font.getCache().addText(layout, drawX, drawY);
        font.getCache().tint(color);
        font.getCache().draw(batch);
    }

    public static void drawCenteredText(Batch batch, BitmapFont font, CharSequence text, Color color, Camera camera) {
        drawCenteredText(batch, font, text, color, camera.viewportWidth / 2, camera.viewportHeight / 2);
    }
}
