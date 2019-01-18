package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.model.PowerBar;

public class PowerBarObject extends GameObject {

    private final PowerBar bar;
    private TextureRegion background;
    private TextureRegion marker;

    public PowerBarObject(AssetManager assets, OrthographicCamera camera) {
        super(assets, camera);
        bar = new PowerBar();
        bar.setDurationSec(0.75f);
        bar.randomize();
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion("powerbar-background");
        marker = atlas.findRegion("powerbar-marker");
        setSize(background.getRegionWidth(), background.getRegionHeight());
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
        batch.draw(background, getX(), getY());
    }

    private void drawMarker(SpriteBatch batch) {
        float drawWidth = marker.getRegionWidth();
        float drawHeight = marker.getRegionHeight();
        float topBarY = getY() + getHeight();
        float markerOffset = bar.getValue() * getHeight();

        // -drawHeight because origin is at lower left
        float drawY = topBarY - drawHeight - markerOffset;
        batch.draw(marker, getX(), drawY, drawWidth, drawHeight);
    }

    public float getValue() {
        return bar.getValue();
    }
}
