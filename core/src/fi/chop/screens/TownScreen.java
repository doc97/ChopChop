package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Scene;
import fi.chop.input.TownScreenInput;
import fi.chop.util.FontRenderer;

public class TownScreen extends ChopScreen {

    private Scene scene;
    private FontRenderer text;

    public TownScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new TownScreenInput(this, getInputMap()));
        scene = new Scene();

        BitmapFont font = getAssets().get("ZCOOL-40.ttf", BitmapFont.class);
        text = new FontRenderer(font).text("TOWN");
    }

    @Override
    protected void update(float delta) {
        scene.update(delta);
    }

    @Override
    protected void render(SpriteBatch batch) {
        beginRender();
        batch.begin();
        scene.render(batch);
        text.center(getCamera(), true, true).draw(batch);
        batch.end();
    }
}
