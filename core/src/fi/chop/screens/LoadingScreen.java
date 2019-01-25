package fi.chop.screens;

import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import fi.chop.Chop;
import fi.chop.effect.ColorFade;
import fi.chop.util.FontRenderer;

public class LoadingScreen extends ChopScreen {

    private FontRenderer progressText;
    private ColorFade fade;
    private float progress;

    public LoadingScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        FreeTypeFontLoaderParameter dance30Params = new FreeTypeFontLoaderParameter();
        dance30Params.fontFileName = "fonts/DancingScript-Bold.ttf";
        dance30Params.fontParameters.size = 30;

        FreeTypeFontLoaderParameter zcool30Params = new FreeTypeFontLoaderParameter();
        zcool30Params.fontFileName = "fonts/ZCOOL-Regular.ttf";
        zcool30Params.fontParameters.size = 30;

        FreeTypeFontLoaderParameter zcool40Params = new FreeTypeFontLoaderParameter();
        zcool40Params.fontFileName = "fonts/ZCOOL-Regular.ttf";
        zcool40Params.fontParameters.size = 40;

        FreeTypeFontLoaderParameter zcool60Params = new FreeTypeFontLoaderParameter();
        zcool60Params.fontFileName = "fonts/ZCOOL-Regular.ttf";
        zcool60Params.fontParameters.size = 60;

        // Needed by LoadingScreen
        getAssets().load("ZCOOL-40.ttf", BitmapFont.class, zcool40Params);
        getAssets().finishLoading();

        // Default TextureFilter
        TextureLoader.TextureParameter textureParams = new TextureLoader.TextureParameter();
        textureParams.genMipMaps = true;
        textureParams.minFilter = Texture.TextureFilter.MipMapLinearNearest;
        textureParams.magFilter = Texture.TextureFilter.Nearest;

        // Queue other assets to be loaded asynchronously
        getAssets().load("ZCOOL-30.ttf", BitmapFont.class, zcool30Params);
        getAssets().load("ZCOOL-60.ttf", BitmapFont.class, zcool60Params);
        getAssets().load("Dance-30.ttf", BitmapFont.class, dance30Params);
        getAssets().load("textures/packed/Chop.atlas", TextureAtlas.class);
        getAssets().load("textures/execution_screen/Background.png", Texture.class, textureParams);
        getAssets().load("textures/execution_screen/Guillotine_Body.png", Texture.class, textureParams);
        getAssets().load("textures/execution_screen/Guillotine_Blade.png", Texture.class, textureParams);

        BitmapFont font = getAssets().get("ZCOOL-40.ttf", BitmapFont.class);
        progressText = new FontRenderer(font);
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
        batch.setColor(fade.getColor());
        progressText
                .text(String.format("%.1f", progress * 100) + "%")
                .center(getCamera(), true, true)
                .draw(batch);
        batch.setColor(Color.WHITE);
    }
}
