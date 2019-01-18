package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PopularityMeterObject extends ValueMeterObject {

    private BitmapFont font;

    public PopularityMeterObject(AssetManager assets, OrthographicCamera camera) {
        super(assets, camera, FillDirection.LEFT, "meter-background", "meter-fill");
    }

    @Override
    public void load() {
        super.load();
        font = getAssets().get("ZCOOL-30.ttf", BitmapFont.class);
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        drawText(batch);
    }

    private void drawText(SpriteBatch batch) {
        String type = "Popularity";
        String percentStr = String.format("%.1f", getMeterFillPercentage() * 100);
        GlyphLayout layout = new GlyphLayout(font, type + ": " + percentStr + "%");
        float drawX = getX() - layout.width;
        float drawY = getY() + font.getLineHeight();
        font.draw(batch, type + ": " + percentStr + "%", drawX, drawY);
    }
}
