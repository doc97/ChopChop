package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.engine.Scene;
import fi.chop.input.TownScreenInput;
import fi.chop.model.object.TextObject;

public class TownScreen extends ChopScreen {

    private Scene scene;

    public TownScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        initializeScreen();
        initializeScene();
    }

    private void initializeScreen() {
        Gdx.input.setInputProcessor(new TownScreenInput(this, getInputMap()));
        getWorld().nextDay();
    }

    private void initializeScene() {
        scene = new Scene();
        scene.addLayer("Background", new Layer());
        scene.addLayer("Text", new Layer());

        TextObject text = new TextObject(getAssets(), getCamera());
        text.setOrigin(0.5f, 0);
        text.setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight / 2);
        text.create("ZCOOL-40.ttf", () -> "DAY " + getWorld().getDay());
        text.load();

        scene.addObjects("Text", text);
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
        batch.end();
    }
}
