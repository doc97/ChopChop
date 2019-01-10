package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.model.InputMap;

public abstract class ChopScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private AssetManager manager;
    private InputMap input;

    public ChopScreen(Chop game) {
        this.manager = game.getAssetManager();
        this.batch = game.getSpriteBatch();
        this.camera = game.getCamera();
        this.input = game.getInputMap();
    }

    protected abstract void update(float delta);
    protected abstract void render(SpriteBatch batch);

    protected void beginRender() {
        getCamera().update();
        batch.setProjectionMatrix(getCamera().combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    @Override
    public void render(float delta) {
        update(delta);
        render(batch);
    }

    protected OrthographicCamera getCamera() {
        return camera;
    }

    protected AssetManager getAssets() {
        return manager;
    }

    protected InputMap getInputMap() {
        return input;
    }
}
