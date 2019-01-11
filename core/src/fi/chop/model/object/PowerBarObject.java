package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.model.PowerBar;

public class PowerBarObject extends GameObject {

    private static final int HEIGHT_PX = 200;

    private TextureRegion background;
    private TextureRegion marker;
    private PowerBar bar;

    public PowerBarObject(AssetManager assets) {
        super(assets);
        bar = new PowerBar();
        bar.setDurationSec(2);
        bar.randomize();
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion("badlogic");
        marker = atlas.findRegion("badlogic");
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
        float drawWidth = marker.getRegionWidth() * 0.8f;
        float drawHeight = HEIGHT_PX + 20;
        float drawX = getX() + marker.getRegionWidth() * 0.1f;
        float drawY = getY() - 10;
        batch.draw(background, drawX, drawY, drawWidth, drawHeight);
    }

    private void drawMarker(SpriteBatch batch) {
        float drawWidth = marker.getRegionWidth();
        float drawHeight = 20;
        float drawY = getY() + bar.getValue() * HEIGHT_PX - drawHeight / 2f;
        batch.draw(marker, getX(), drawY, drawWidth, drawHeight);
    }
}
