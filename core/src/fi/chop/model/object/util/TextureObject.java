package fi.chop.model.object.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.engine.DrawParameters;
import fi.chop.model.object.GameObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public class TextureObject extends GameObject {

    private String assetName;
    private Texture texture;
    private DrawParameters parameters;

    public TextureObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
    }

    public void setTexture(String assetName) {
        this.assetName = assetName;
    }

    @Override
    public void load() {
        texture = getAssets().get(assetName, Texture.class);
        parameters = new DrawParameters(texture);
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) {
        draw(batch, texture, parameters);
    }

    @Override
    public void dispose() { }

    public DrawParameters getParameters() {
        return parameters;
    }
}
