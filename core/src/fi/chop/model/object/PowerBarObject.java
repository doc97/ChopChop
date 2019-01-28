package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.model.PowerBar;
import fi.chop.model.world.Player;
import fi.chop.model.world.PopularityPerk;
import fi.chop.model.world.WorldState;

public class PowerBarObject extends GameObject {

    private final PowerBar bar;
    private TextureRegion background;
    private TextureRegion marker;
    private DrawParameters backgroundParams;
    private DrawParameters markerParams;

    public PowerBarObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        float duration = 0.75f;
        if (player.hasPerk(PopularityPerk.MEANINGFUL_WORK))
            duration = 1.5f;

        bar = new PowerBar();
        bar.setDurationSec(duration);
        bar.randomize();
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion("powerbar-background");
        marker = atlas.findRegion("powerbar-marker");
        backgroundParams = new DrawParameters(background);
        markerParams = new DrawParameters(marker);

        getTransform().setSize(background.getRegionWidth(), background.getRegionHeight());
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
        markerParams.y = (-getTransform().getOriginY() + (1 - bar.getValue())) * getTransform().getHeight();
        draw(batch, marker, markerParams);
    }

    public float getValue() {
        return bar.getValue();
    }
}
