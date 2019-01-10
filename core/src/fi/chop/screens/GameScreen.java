package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends ChopScreen {

    private Texture logo;

    public GameScreen(SpriteBatch batch, OrthographicCamera camera) {
        super(batch, camera);
    }

    @Override
    public void show() {
        logo = new Texture(Gdx.files.internal("badlogic.jpg"));
    }

    @Override
    protected void update(float delta) {

    }

    @Override
    protected void render(SpriteBatch batch) {
        beginRender();
        batch.begin();
        float centerX = getCamera().viewportWidth / 2f - logo.getWidth() / 2f;
        float centerY = getCamera().viewportHeight / 2f - logo.getHeight() / 2f;
        batch.draw(logo, centerX, centerY);
        batch.end();
    }

    @Override
    public void dispose() {
        logo.dispose();
    }
}
