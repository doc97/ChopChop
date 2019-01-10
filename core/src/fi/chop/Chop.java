package fi.chop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.screens.GameScreen;

public class Chop extends Game {

	private SpriteBatch batch;

	@Override
	public void create() {
	    // Set background color to black
	    Gdx.gl.glClearColor(0, 0, 0, 1);

		batch = new SpriteBatch();
	    setScreen(new GameScreen(batch));
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
