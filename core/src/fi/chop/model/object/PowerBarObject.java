package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.model.PowerBar;
import fi.chop.model.world.Player;

public class PowerBarObject extends GameObject {

    private final PowerBar bar;
    private TextureRegion background;
    private TextureRegion marker;
    private DrawParameters backgroundParams;
    private DrawParameters markerParams;

    public PowerBarObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
        bar = new PowerBar();
        bar.setDurationSec(0.75f);
        bar.randomize();
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion("powerbar-background");
        marker = atlas.findRegion("powerbar-marker");
        backgroundParams = new DrawParameters(background);
        markerParams = new DrawParameters(marker);

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

    @Override
    public void dispose() { }

    private void drawBackground(SpriteBatch batch) {
        draw(batch, background, backgroundParams);
    }

    private void drawMarker(SpriteBatch batch) {
        markerParams.y = (-getOriginY() + (1 - bar.getValue())) * getHeight();
        draw(batch, marker, markerParams);
    }

    public float getValue() {
        return bar.getValue();
    }
}
