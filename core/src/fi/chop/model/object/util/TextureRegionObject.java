package fi.chop.model.object.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.model.object.GameObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public class TextureRegionObject extends GameObject {

    private String atlasName;
    private String regionName;
    private TextureRegion region;
    private DrawParameters parameters;

    public TextureRegionObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
    }

    public void setRegion(String atlasName, String regionName) {
        this.atlasName = atlasName;
        this.regionName = regionName;
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get(atlasName, TextureAtlas.class);
        region = atlas.findRegion(regionName);
        parameters = new DrawParameters(region)
                .srcPos(getRegionX(), getRegionY())
                .srcSize(getRegionWidth(), getRegionHeight());
        getTransform().setSize(parameters.width, parameters.height);
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) {
        draw(batch, region.getTexture(), parameters);
    }

    @Override
    public void dispose() { }

    public int getRegionX() {
        return region.getRegionX();
    }

    public int getRegionY() {
        return region.getRegionY();
    }

    public int getRegionWidth() {
        return region.getRegionWidth();
    }

    public int getRegionHeight() {
        return region.getRegionHeight();
    }

    public DrawParameters getParameters() {
        return parameters;
    }
}
