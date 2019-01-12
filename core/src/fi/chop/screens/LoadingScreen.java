package fi.chop.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import fi.chop.Chop;

public class LoadingScreen extends ChopScreen {

    public LoadingScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        FreeTypeFontLoaderParameter zcoolParams = new FreeTypeFontLoaderParameter();
        zcoolParams.fontFileName = "fonts/ZCOOL-Regular.ttf";
        zcoolParams.fontParameters.size = 40;

        getAssets().load("fonts/ZCOOL-Regular.ttf", BitmapFont.class, zcoolParams);
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
