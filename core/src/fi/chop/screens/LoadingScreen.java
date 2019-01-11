package fi.chop.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import fi.chop.Chop;

public class LoadingScreen extends ChopScreen {

    public LoadingScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        getAssets().load("textures/packed/Chop.atlas", TextureAtlas.class);
    }

    @Override
    protected void update(float delta) {
        if (getAssets().update())
            setScreen(Screens.MAIN_MENU);
    }

    @Override
    protected void render(SpriteBatch batch) {

    }
}
