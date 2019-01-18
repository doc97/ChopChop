package fi.chop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fi.chop.event.EventSystem;
import fi.chop.model.GameStats;
import fi.chop.engine.InputMap;
import fi.chop.model.world.Player;
import fi.chop.screens.LoadingScreen;
import fi.chop.timer.GameTimer;

public class Chop extends Game {

    public static EventSystem events;
    public static GameTimer timer;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private AssetManager assets;
    private InputMap input;
    private GameStats stats;
    private Player player;

    @Override
    public void create() {

        // Set background color to black
        Gdx.gl.glClearColor(0, 0, 0, 1);

        events = new EventSystem();
        timer = new GameTimer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply(true);

        assets = new AssetManager();
        batch = new SpriteBatch();
        input = new InputMap();
        stats = new GameStats();
        player = new Player(events);

        // Set TTF and bitmap font loader
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        super.render();
        timer.update(Gdx.graphics.getRawDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assets;
    }

    public InputMap getInputMap() {
        return input;
    }

    public GameStats getStats() {
        return stats;
    }

    public Player getPlayer() {
        return player;
    }
}
