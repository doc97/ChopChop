package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.effect.ColorFade;
import fi.chop.input.MainMenuScreenInput;
import fi.chop.util.FontRenderer;
import fi.chop.util.MathUtil;

public class MainMenuScreen extends ChopScreen {

    private ColorFade fade;
    private FontRenderer instructionText;

    public MainMenuScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new MainMenuScreenInput(this, getInputMap()));

        BitmapFont font = getAssets().get("ZCOOL-40.ttf", BitmapFont.class);
        instructionText = new FontRenderer(font);
        instructionText.text("Press [Space] to start");

        fade = new ColorFade(Color.WHITE, Color.BLACK, 1.5f,
                (t) -> MathUtil.smoothStartN(t, 2));
    }

    @Override
    protected void update(float delta) {
        fade.update(delta);
        if (fade.hasFinished())
            fade.flip();
    }

    @Override
    protected void render(SpriteBatch batch) {
        batch.setColor(fade.getColor());
        instructionText
                .center(getCamera(), true, true)
                .draw(batch);
        batch.setColor(Color.WHITE);
    }
}
