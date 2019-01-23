package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.TavernScreenInput;
import fi.chop.input.TextButtonHandler;
import fi.chop.model.object.TextObject;

public class TavernScreen extends ChopScreen implements EventListener {

    public TavernScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        registerEventListener();
        initializeScreen();
        initializeScene();
    }

    private void registerEventListener() {
        Chop.events.addListener(this, Events.ACTION_BACK);
    }

    private void initializeScreen() {
        Gdx.input.setInputProcessor(new TavernScreenInput(this, getInputMap()));
    }

    private void initializeScene() {
        getScene().addLayer("Background", new Layer());
        getScene().addLayer("Buttons", new Layer());

        TextObject drinkText = new TextObject(getAssets(), getCamera(), getPlayer());
        drinkText.setOrigin(0.5f, 1);
        drinkText.setPosition(getCamera().viewportWidth / 2f, getCamera().viewportHeight / 2f);
        drinkText.pad(50, 50);
        drinkText.create("ZCOOL-40.ttf", () -> "Buy a drink");
        drinkText.load();
        drinkText.setTouchable(true);
        drinkText.setTouchHandler(new TextButtonHandler(() -> Gdx.app.log("Tavern", "Drink!")));

        getScene().addObjects("Buttons", drinkText);
        getScene().addQueued();
    }

    @Override
    protected void update(float delta) {
        getScene().update(delta);
    }

    @Override
    protected void render(SpriteBatch batch) {
        getScene().render(batch);
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.ACTION_BACK)
            setScreen(Screens.TOWN);
    }
}
