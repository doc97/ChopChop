package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends ChopScreen {

    public GameScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    protected void update(float delta) {

    }

    @Override
    protected void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
