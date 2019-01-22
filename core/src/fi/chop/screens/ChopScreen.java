package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Scene;
import fi.chop.model.GameStats;
import fi.chop.engine.InputMap;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public abstract class ChopScreen extends ScreenAdapter {

    private final Chop game;
    private final Scene scene;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final AssetManager manager;
    private final InputMap input;
    private final GameStats stats;
    private final WorldState world;
    private final Player player;

    public ChopScreen(Chop game) {
        this.game = game;
        this.manager = game.getAssetManager();
        this.batch = game.getSpriteBatch();
        this.camera = game.getCamera();
        this.input = game.getInputMap();
        this.stats = game.getStats();
        this.world = game.getWorld();
        this.player = game.getPlayer();

        scene = new Scene();
    }

    protected abstract void update(float delta);
    protected abstract void render(SpriteBatch batch);

    private void beginRender() {
        getCamera().update();
        batch.setProjectionMatrix(getCamera().combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        getScene().clear();
        Chop.events.clear();
        Chop.timer.clear();
    }

    @Override
    public void render(float delta) {
        update(delta);

        beginRender();
        batch.begin();
        render(batch);
        batch.end();
    }

    public void setScreen(Screens screen) {
        switch (screen) {
            case EXECUTION:
                game.setScreen(new ExecutionScreen(game));
                break;
            case TOWN:
                game.setScreen(new TownScreen(game));
                break;
            case MAIN_MENU:
                game.setScreen(new MainMenuScreen(game));
                break;
        }
    }

    public Scene getScene() {
        return scene;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    protected AssetManager getAssets() {
        return manager;
    }

    protected InputMap getInputMap() {
        return input;
    }

    protected GameStats getStats() {
        return stats;
    }

    protected WorldState getWorld() {
        return world;
    }

    protected Player getPlayer() {
        return player;
    }
}
