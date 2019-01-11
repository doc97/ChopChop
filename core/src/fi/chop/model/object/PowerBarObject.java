package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.model.PowerBar;

public class PowerBarObject extends GameObject {

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
        background = atlas.findRegion("powerbar-background");
        marker = atlas.findRegion("powerbar-marker");
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
        float drawWidth = background.getRegionWidth();
        float drawHeight = background.getRegionHeight();
        float drawX = getX();
        float drawY = getY();
        batch.draw(background, drawX, drawY, drawWidth, drawHeight);
    }

    private void drawMarker(SpriteBatch batch) {
        float drawWidth = marker.getRegionWidth();
        float drawHeight = marker.getRegionHeight();
        float drawY = getY() + bar.getValue() * (background.getRegionHeight() - drawHeight);
        batch.draw(marker, getX(), drawY, drawWidth, drawHeight);
    }
}
