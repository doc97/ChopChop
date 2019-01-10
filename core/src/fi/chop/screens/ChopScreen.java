package fi.chop.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class ChopScreen extends ScreenAdapter {

    private SpriteBatch batch;

    public ChopScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    protected abstract void update(float delta);
    protected abstract void render(SpriteBatch batch);

    @Override
    public void render(float delta) {
        update(delta);
        render(batch);
    }
}
