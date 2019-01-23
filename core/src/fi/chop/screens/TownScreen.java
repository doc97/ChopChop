package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.TextButtonHandler;
import fi.chop.input.TownScreenInput;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.gui.GameGUIObject;
import fi.chop.model.object.util.TextButtonObject;
import fi.chop.model.object.util.TextObject;

public class TownScreen extends ChopScreen implements EventListener {

    public TownScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        registerEventListener();
        initializeScreen();
        initializeScene();

        if (getWorld().getDay() % 7 == 0)
            getPlayer().addMoney(-getWorld().getTaxes());
    }

    private void registerEventListener() {
        Chop.events.addListener(this, Events.ACTION_BACK, Events.ACTION_INTERACT);
    }

    private void initializeScreen() {
        Gdx.input.setInputProcessor(new TownScreenInput(this, getInputMap()));
    }

    private void initializeScene() {
        getScene().addLayer("Background", new Layer());
        getScene().addLayer("Text", new Layer());
        getScene().addLayer("GUI", new Layer());

        TextObject dayText = new TextObject(getAssets(), getCamera(), getPlayer());
        dayText.setOrigin(0.5f, 1);
        dayText.setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight - 50);
        dayText.create("ZCOOL-60.ttf", () -> "DAY " + getWorld().getDay());
        dayText.load();

        TextButtonObject castleText = new TextButtonObject(getAssets(), getCamera(), getPlayer());
        castleText.setOrigin(0.5f, 1);
        castleText.setPosition(getCamera().viewportWidth / 2 - 150, getCamera().viewportHeight - 200);
        castleText.create("ZCOOL-40.ttf", () -> "Castle");
        castleText.load();
        castleText.setTouchHandler(new TextButtonHandler(() -> Gdx.app.log("Castle", "Go!")));

        TextButtonObject tavernText = new TextButtonObject(getAssets(), getCamera(), getPlayer());
        tavernText.setOrigin(0, 1);
        tavernText.setPosition(200, 300);
        tavernText.create("ZCOOL-40.ttf", () -> "Tavern");
        tavernText.load();
        tavernText.setTouchHandler(new TextButtonHandler(() -> setScreen(Screens.TAVERN)));

        TextButtonObject guillotineText = new TextButtonObject(getAssets(), getCamera(), getPlayer());
        guillotineText.setOrigin(1, 1);
        guillotineText.setPosition(getCamera().viewportWidth - 200, 500);
        guillotineText.create("ZCOOL-40.ttf", () -> "Guillotine");
        guillotineText.pad(50, 50);
        guillotineText.load();
        guillotineText.setTouchHandler(new TextButtonHandler(() -> setScreen(Screens.EXECUTION)));

        GameObject gui = new GameGUIObject(getAssets(), getCamera(), getPlayer());
        gui.load();

        getScene().addObjects("Text", dayText, castleText, tavernText, guillotineText);
        getScene().addObjects("GUI", gui);
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
        if (event == Events.ACTION_BACK) {
            getWorld().reset();
            setScreen(Screens.MAIN_MENU);
        } else if (event == Events.ACTION_INTERACT) {
            setScreen(Screens.EXECUTION);
        }
    }
}
