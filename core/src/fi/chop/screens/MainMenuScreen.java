package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.input.MainMenuScreenInput;

public class MainMenuScreen extends ChopScreen {

    public MainMenuScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new MainMenuScreenInput(this, getInputMap()));
    }

    @Override
    protected void update(float delta) { }

    @Override
    protected void render(SpriteBatch batch) { }
}
