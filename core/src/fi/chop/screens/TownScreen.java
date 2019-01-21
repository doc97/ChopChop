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

        TextObject dayText = new TextObject(getAssets(), getCamera());
        dayText.setOrigin(0.5f, 1);
        dayText.setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight - 50);
        dayText.create("ZCOOL-60.ttf", () -> "DAY " + getWorld().getDay());
        dayText.load();

        TextObject castleText = new TextObject(getAssets(), getCamera());
        castleText.setOrigin(0.5f, 1);
        castleText.setPosition(getCamera().viewportWidth / 2 - 150, getCamera().viewportHeight - 200);
        castleText.create("ZCOOL-40.ttf", () -> "Castle");
        castleText.load();

        TextObject tavernText = new TextObject(getAssets(), getCamera());
        tavernText.setOrigin(0, 1);
        tavernText.setPosition(200, 300);
        tavernText.create("ZCOOL-40.ttf", () -> "Tavern");
        tavernText.load();

        TextObject guillotineText = new TextObject(getAssets(), getCamera());
        guillotineText.setOrigin(1, 1);
        guillotineText.setPosition(getCamera().viewportWidth - 200, 500);
        guillotineText.create("ZCOOL-40.ttf", () -> "Guillotine");
        guillotineText.load();

        scene.addObjects("Text", dayText, castleText, tavernText, guillotineText);
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
