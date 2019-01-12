package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.effect.ColorFade;
import fi.chop.input.MainMenuScreenInput;
import fi.chop.util.DrawUtil;
import fi.chop.util.MathUtil;

public class MainMenuScreen extends ChopScreen {

    private BitmapFont font;
    private String text;
    private ColorFade fade;

    public MainMenuScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new MainMenuScreenInput(this, getInputMap()));

        text = "Press [Space] to start";
        font = getAssets().get("fonts/ZCOOL-Regular.ttf", BitmapFont.class);

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
        beginRender();
        batch.begin();
        DrawUtil.drawCenteredText(batch, font, text, fade.getColor(), getCamera());
        batch.end();
    }
}
