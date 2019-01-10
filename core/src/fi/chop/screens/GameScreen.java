package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.input.GameScreenInput;
import fi.chop.model.object.PowerBarObject;

public class GameScreen extends ChopScreen {

    private PowerBarObject powerBar;

    public GameScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GameScreenInput(this, getInputMap()));
        powerBar = new PowerBarObject(getAssets());
        powerBar.setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight / 2);
        powerBar.load();
    }

    @Override
    protected void update(float delta) {
        powerBar.update(delta);
    }

    @Override
    protected void render(SpriteBatch batch) {
        beginRender();
        batch.begin();
        powerBar.render(batch);
        batch.end();
    }
}
