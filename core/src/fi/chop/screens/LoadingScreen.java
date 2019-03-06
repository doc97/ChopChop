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
        FreeTypeFontLoaderParameter roboto60Params = new FreeTypeFontLoaderParameter();
        roboto60Params.fontFileName = "fonts/Roboto-Regular.ttf";
        roboto60Params.fontParameters.size = 60;
        initFontParams(roboto60Params);

        FreeTypeFontLoaderParameter roboto120Params = new FreeTypeFontLoaderParameter();
        roboto120Params.fontFileName = "fonts/Roboto-Regular.ttf";
        roboto120Params.fontParameters.size = 120;
        initFontParams(roboto120Params);

        FreeTypeFontLoaderParameter roboto180Params = new FreeTypeFontLoaderParameter();
        roboto180Params.fontFileName = "fonts/Roboto-Regular.ttf";
        roboto180Params.fontParameters.size = 180;
        initFontParams(roboto180Params);

        FreeTypeFontLoaderParameter dance30Params = new FreeTypeFontLoaderParameter();
        dance30Params.fontFileName = "fonts/DancingScript-Bold.ttf";
        dance30Params.fontParameters.size = 30;
        initFontParams(dance30Params);

        FreeTypeFontLoaderParameter zcool30Params = new FreeTypeFontLoaderParameter();
        zcool30Params.fontFileName = "fonts/ZCOOL-Regular.ttf";
        zcool30Params.fontParameters.size = 30;
        initFontParams(zcool30Params);

        FreeTypeFontLoaderParameter zcool40Params = new FreeTypeFontLoaderParameter();
        zcool40Params.fontFileName = "fonts/ZCOOL-Regular.ttf";
        zcool40Params.fontParameters.size = 40;
        initFontParams(zcool40Params);

        FreeTypeFontLoaderParameter zcool60Params = new FreeTypeFontLoaderParameter();
        zcool60Params.fontFileName = "fonts/ZCOOL-Regular.ttf";
        zcool60Params.fontParameters.size = 60;
        initFontParams(zcool60Params);

        // Needed by LoadingScreen
        getAssets().load("ZCOOL-40.ttf", BitmapFont.class, zcool40Params);
        getAssets().finishLoading();

        // Default TextureFilter
        TextureLoader.TextureParameter textureParams = new TextureLoader.TextureParameter();
        textureParams.genMipMaps = true;
        textureParams.minFilter = Texture.TextureFilter.MipMapLinearNearest;
        textureParams.magFilter = Texture.TextureFilter.Nearest;

        // Queue other assets to be loaded asynchronously
        getAssets().load("Roboto-60.ttf", BitmapFont.class, roboto60Params);
        getAssets().load("Roboto-120.ttf", BitmapFont.class, roboto120Params);
        getAssets().load("Roboto-180.ttf", BitmapFont.class, roboto180Params);
        getAssets().load("ZCOOL-30.ttf", BitmapFont.class, zcool30Params);
        getAssets().load("ZCOOL-60.ttf", BitmapFont.class, zcool60Params);
        getAssets().load("Dance-30.ttf", BitmapFont.class, dance30Params);
        getAssets().load("textures/packed/Chop.atlas", TextureAtlas.class);
        getAssets().load("textures/town_screen/Background_Neutral.png", Texture.class,textureParams);
        getAssets().load("textures/town_screen/Background_Tavern.png", Texture.class,textureParams);
        getAssets().load("textures/town_screen/Background_Guillotine.png", Texture.class,textureParams);
        getAssets().load("textures/tavern_screen/Background_Neutral.png", Texture.class, textureParams);
        getAssets().load("textures/tavern_screen/Background_Drink.png", Texture.class, textureParams);
        getAssets().load("textures/execution_screen/Background.png", Texture.class, textureParams);
        getAssets().load("textures/execution_screen/Guillotine_Body.png", Texture.class, textureParams);
        getAssets().load("textures/execution_screen/Guillotine_Blade.png", Texture.class, textureParams);
        getSounds().loadSounds();
        

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

    private void initFontParams(FreeTypeFontLoaderParameter param) {
        param.fontParameters.genMipMaps = true;
        param.fontParameters.minFilter = Texture.TextureFilter.MipMapLinearNearest;
        param.fontParameters.gamma = 1;
        param.fontParameters.padBottom = 1;
        param.fontParameters.padRight = 1;
        param.fontParameters.padLeft = 1;
        param.fontParameters.padTop = 1;
    }
}
