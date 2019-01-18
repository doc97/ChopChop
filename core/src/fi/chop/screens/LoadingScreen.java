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
        FreeTypeFontLoaderParameter zcool20Params = new FreeTypeFontLoaderParameter();
        zcool20Params.fontFileName = "fonts/ZCOOL-Regular.ttf";
        zcool20Params.fontParameters.size = 20;

        FreeTypeFontLoaderParameter zcool40Params = new FreeTypeFontLoaderParameter();
        zcool40Params.fontFileName = "fonts/ZCOOL-Regular.ttf";
        zcool40Params.fontParameters.size = 40;

        // Needed by LoadingScreen
        getAssets().load("ZCOOL-40.ttf", BitmapFont.class, zcool40Params);
        getAssets().finishLoading();

        // Queue other assets to be loaded asynchronously
        getAssets().load("ZCOOL-20.ttf", BitmapFont.class, zcool20Params);
        getAssets().load("textures/packed/Chop.atlas", TextureAtlas.class);

        font = getAssets().get("ZCOOL-40.ttf", BitmapFont.class);
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
        DrawUtil.drawCenteredText(batch, font, percent, fade.getColor(), getCamera());
        batch.end();
    }
}
