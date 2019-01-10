package fi.chop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fi.chop.model.InputMap;
import fi.chop.screens.MainMenuScreen;

public class Chop extends Game {

	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;
	private AssetManager assets;
	private InputMap input;

	@Override
	public void create() {
	    // Set background color to black
	    Gdx.gl.glClearColor(0, 0, 0, 1);

	    camera = new OrthographicCamera();
	    viewport = new FitViewport(1920, 1080, camera);
	    viewport.apply(true);

	    assets = new AssetManager();
		batch = new SpriteBatch();
		input = new InputMap();

		/* Load texture synchronously
		 * Will be moved to a separate loading screen
		 * for asynchronous loading
		 */
		assets.load("badlogic.jpg", Texture.class);
		assets.finishLoading();

	    setScreen(new MainMenuScreen(this));
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
}
