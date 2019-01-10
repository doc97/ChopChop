package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.input.GameScreenInput;

public class GameScreen extends ChopScreen {

    private Texture logo;

    public GameScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        logo = getAssets().get("badlogic.jpg", Texture.class);
        Gdx.input.setInputProcessor(new GameScreenInput());
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
