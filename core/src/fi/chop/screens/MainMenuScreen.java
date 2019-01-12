package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.input.MainMenuScreenInput;

public class MainMenuScreen extends ChopScreen {

    private BitmapFont font;
    private GlyphLayout layout;
    private String text;

    public MainMenuScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new MainMenuScreenInput(this, getInputMap()));

        text = "Press [Space] to start";
        font = getAssets().get("fonts/ZCOOL-Regular.ttf", BitmapFont.class);
        layout = new GlyphLayout(font, text);
    }

    @Override
    protected void update(float delta) { }

    @Override
    protected void render(SpriteBatch batch) {
        beginRender();
        batch.begin();
        float drawX = getCamera().viewportWidth / 2 - layout.width / 2;
        float drawY = getCamera().viewportHeight / 2 - layout.height / 2;
        font.draw(batch, text, drawX, drawY);
        batch.end();
    }
}
