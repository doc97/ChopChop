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
import fi.chop.model.object.GameObject;
import fi.chop.model.object.TextObject;
import fi.chop.model.object.gui.GameGUIObject;

import java.util.Random;

public class TavernScreen extends ChopScreen implements EventListener {

    private static final int DRINK_PRICE = 10;
    private static final float POPULARITY_DELTA = 0.05f;

    private Random random;

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
        random = new Random();
    }

    private void initializeScene() {
        getScene().addLayer("Background", new Layer());
        getScene().addLayer("Buttons", new Layer());
        getScene().addLayer("GUI", new Layer());

        TextObject drinkText = new TextObject(getAssets(), getCamera(), getPlayer());
        drinkText.setOrigin(0.5f, 1);
        drinkText.setPosition(getCamera().viewportWidth / 2f, getCamera().viewportHeight / 2f);
        drinkText.pad(50, 50);
        drinkText.create("ZCOOL-40.ttf", () -> "Buy a drink");
        drinkText.load();
        drinkText.setTouchable(true);
        drinkText.setTouchHandler(new TextButtonHandler(() -> {
            if (getPlayer().hasEnoughMoney(DRINK_PRICE)) {
                Gdx.app.log("Tavern", "Bought a drink...");
                getPlayer().addMoney(-DRINK_PRICE);

                getWorld().incrementDrinkCount();
                int threshold = Math.max(100 - getWorld().getDrinkCount() * 10, 5);
                Gdx.app.log("Tavern", threshold + "% chance of gaining popularity");
                if (random.nextInt(100) < threshold) {
                    Gdx.app.log("Tavern", "Received " + (POPULARITY_DELTA * 100) + "% popularity!");
                    getPlayer().addPopularity(POPULARITY_DELTA);
                } else {
                    Gdx.app.log("Tavern", "No luck... No one was looking.");
                }
            } else {
                Gdx.app.log("Tavern", "Not enough money!");
            }
        }));

        GameObject gui = new GameGUIObject(getAssets(), getCamera(), getPlayer());
        gui.load();

        getScene().addObjects("Buttons", drinkText);
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
        if (event == Events.ACTION_BACK)
            setScreen(Screens.TOWN);
    }
}
