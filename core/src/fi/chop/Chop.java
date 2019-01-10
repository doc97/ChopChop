package fi.chop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fi.chop.screens.GameScreen;

public class Chop extends Game {

	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;

	@Override
	public void create() {
	    // Set background color to black
	    Gdx.gl.glClearColor(0, 0, 0, 1);

	    camera = new OrthographicCamera();
	    viewport = new FitViewport(1920, 1080, camera);
	    viewport.apply(true);

		batch = new SpriteBatch();
	    setScreen(new GameScreen(batch, camera));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height, true);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
