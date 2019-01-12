package fi.chop.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import fi.chop.Chop;
import fi.chop.effect.ColorFade;
import fi.chop.util.DrawUtil;

public class LoadingScreen extends ChopScreen {

    private BitmapFont font;
    private GlyphLayout layout;
    private ColorFade fade;
    private float progress;

    public LoadingScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        FreeTypeFontLoaderParameter zcoolParams = new FreeTypeFontLoaderParameter();
        zcoolParams.fontFileName = "fonts/ZCOOL-Regular.ttf";
        zcoolParams.fontParameters.size = 40;

        // Needed by LoadingScreen
        getAssets().load("fonts/ZCOOL-Regular.ttf", BitmapFont.class, zcoolParams);
        getAssets().finishLoading();

        // Queue other assets to be loaded asynchronously
        getAssets().load("textures/packed/Chop.atlas", TextureAtlas.class);

        font = getAssets().get("fonts/ZCOOL-Regular.ttf", BitmapFont.class);
        layout = new GlyphLayout(font, "0.0%");
        fade = new ColorFade(Color.WHITE, Color.BLACK, 2);
    }

    @Override
    protected void update(float delta) {
        if (getAssets().update()) {
            fade.update(delta);
            if (fade.hasFinished())
                setScreen(Screens.MAIN_MENU);
        }

        progress = getAssets().getProgress();
    }

    @Override
    protected void render(SpriteBatch batch) {
        String percent = String.format("%.1f", progress * 100) + "%";
        layout.setText(font, percent);
        beginRender();
        batch.begin();
        font.setColor(fade.getColor());
        DrawUtil.drawCenteredText(batch, font, percent, getCamera());
        batch.end();
    }
}
