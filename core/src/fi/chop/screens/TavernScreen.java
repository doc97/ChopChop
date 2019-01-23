package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.TavernScreenInput;
import fi.chop.input.TextButtonHandler;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.util.TextObject;
import fi.chop.model.object.gui.GameGUIObject;
import fi.chop.model.object.util.TextObjectStyle;

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

        TextObject buyText = new TextObject(getAssets(), getCamera(), getPlayer());
        buyText.setOrigin(0.5f, 1);
        buyText.setPosition(getCamera().viewportWidth / 2f, getCamera().viewportHeight / 2f);
        buyText.pad(50, 50);
        buyText.create("ZCOOL-40.ttf", () -> "Buy a drink");
        buyText.load();
        buyText.setStyle(TextObject.StyleType.NORMAL,
                new TextObjectStyle().bgColor(Color.GOLDENROD).tint(Color.BROWN));
        buyText.setTouchable(true);
        buyText.setTouchHandler(new TextButtonHandler(() -> {
            if (getPlayer().hasEnoughMoney(DRINK_PRICE)) {
                Gdx.app.log("Tavern", "Bought a drink...");
                getPlayer().addMoney(-DRINK_PRICE);

                getWorld().incrementDrinkCount();
                int threshold = Math.max(100 - getWorld().getDrinkCount() * 10, 5);
                if (random.nextInt(100) < threshold) {
                    getPlayer().addPopularity(POPULARITY_DELTA);
                } else {
                    Gdx.app.log("Tavern", "No luck... No one was looking.");
                }
            } else {
                buyText.disable();
                Gdx.app.log("Tavern", "Not enough money!");
            }
        }));
        if (!getPlayer().hasEnoughMoney(DRINK_PRICE)) {
            buyText.useStyle(TextObject.StyleType.DISABLED);
            buyText.disable();
        }

        GameObject gui = new GameGUIObject(getAssets(), getCamera(), getPlayer());
        gui.load();

        TextObject priceText = new TextObject(getAssets(), getCamera(), getPlayer());
        priceText.setOrigin(0, 1);
        priceText.setPosition(100, getCamera().viewportHeight - 200);
        priceText.pad(10, 10);
        priceText.create("ZCOOL-30.ttf", () -> "Price: " + DRINK_PRICE + " gold");
        priceText.load();

        TextObject drinksText = new TextObject(getAssets(), getCamera(), getPlayer());
        drinksText.setOrigin(0, 1);
        drinksText.setPosition(100, getCamera().viewportHeight - 250);
        drinksText.pad(10, 10);
        drinksText.create("ZCOOL-30.ttf", () -> "Drinks bought today: " + getWorld().getDrinkCount());
        drinksText.load();

        TextObject probabilityText = new TextObject(getAssets(), getCamera(), getPlayer());
        probabilityText.setOrigin(0, 1);
        probabilityText.setPosition(100, getCamera().viewportHeight - 300);
        probabilityText.pad(10, 10);
        probabilityText.create("ZCOOL-30.ttf", () -> "Chance of increasing popularity: " +
                Math.max(100 - (getWorld().getDrinkCount() + 1) * 10, 5) + "%");
        probabilityText.load();

        getScene().addObjects("Buttons", priceText, buyText, probabilityText);
        getScene().addObjects("GUI", gui, drinksText);
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
