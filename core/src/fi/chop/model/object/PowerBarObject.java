package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.PowerBar;

public class PowerBarObject extends GameObject {

    private static final int HEIGHT_PX = 200;

    private Texture background;
    private Texture marker;
    private PowerBar bar;

    public PowerBarObject(AssetManager assets) {
        super(assets);
        bar = new PowerBar();
        bar.setDurationSec(2);
        bar.randomize();
    }

    @Override
    public void load() {
        background = getAssets().get("badlogic.jpg", Texture.class);
        marker = getAssets().get("badlogic.jpg", Texture.class);
    }

    @Override
    public void update(float delta) {
        bar.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        drawBackground(batch);
        drawMarker(batch);
    }

    private void drawBackground(SpriteBatch batch) {
        float drawWidth = marker.getWidth() * 0.8f;
        float drawHeight = HEIGHT_PX + 20;
        float drawX = getX() + marker.getWidth() * 0.1f;
        float drawY = getY() - 10;
        batch.draw(background, drawX, drawY, drawWidth, drawHeight);
    }

    private void drawMarker(SpriteBatch batch) {
        float drawWidth = marker.getWidth();
        float drawHeight = 20;
        float drawY = getY() + bar.getValue() * HEIGHT_PX - drawHeight / 2f;
        batch.draw(marker, getX(), drawY, drawWidth, drawHeight);
    }
}
