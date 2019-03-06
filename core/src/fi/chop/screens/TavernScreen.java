package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.BasicScreenInput;
import fi.chop.input.touchhandler.TextButtonHandler;
import fi.chop.model.object.gui.GUIObject;
import fi.chop.model.object.gui.GameGUIObject;
import fi.chop.model.object.gui.TextButtonObject;
import fi.chop.model.object.gui.TextObject;
import fi.chop.sound.MusicType;

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
        Gdx.input.setInputProcessor(new BasicScreenInput(this, getInputMap()));
        random = new Random();
        getSounds().setBackgroundMusic(MusicType.CROWD_SOUNDS);
    }

    private void initializeScene() {
        getScene().addLayer("Background", new Layer());
        getScene().addLayer("Buttons", new Layer());
        getScene().addLayer("GUI", new Layer());

        TextButtonObject buyBtn = new TextButtonObject(getAssets(), getCamera(), getWorld(), getPlayer());
        buyBtn.setPressedOffset(0, -8);
        buyBtn.create("ZCOOL-40.ttf", () -> "Buy a drink");
        buyBtn.load();
        buyBtn.pack();
        buyBtn.getTransform().setPosition(
                getCamera().viewportWidth / 2f,
                getCamera().viewportHeight / 2f - buyBtn.getTransform().getHeight());
        buyBtn.setTouchHandler(new TextButtonHandler(buyBtn, (btn) -> {
            if (getPlayer().hasEnoughMoney(DRINK_PRICE)) {
                Gdx.app.log("Tavern", "Bought a drink...");
                getPlayer().addMoney(-DRINK_PRICE);
                if (!getPlayer().hasEnoughMoney(DRINK_PRICE))
                    btn.disable();

                getWorld().incrementDrinkCount();
                int threshold = Math.max(100 - getWorld().getDrinkCount() * 10, 5);
                if (random.nextInt(100) < threshold) {
                    getPlayer().addPopularity(POPULARITY_DELTA);
                } else {
                    Gdx.app.log("Tavern", "No luck... No one was looking.");
                }
            } else {
                Gdx.app.log("Tavern", "Not enough money!");
            }
        }));
        if (!getPlayer().hasEnoughMoney(DRINK_PRICE))
            buyBtn.disable();

        GUIObject gui = new GameGUIObject(getAssets(), getCamera(), getWorld(), getPlayer());
        gui.load();
        gui.pack();

        TextObject priceText = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
        priceText.getTransform().setOrigin(0, 1);
        priceText.getTransform().setPosition(60, getCamera().viewportHeight - 150);
        priceText.create("ZCOOL-30.ttf", () -> "Price: " + DRINK_PRICE + " gold");
        priceText.load();
        priceText.pack();

        TextObject drinksText = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
        drinksText.getTransform().setOrigin(0, 1);
        drinksText.getTransform().setPosition(60, priceText.getTransform().getBottom() - 15);
        drinksText.create("ZCOOL-30.ttf", () -> "Drinks bought today: " + getWorld().getDrinkCount());
        drinksText.load();
        drinksText.pack();

        TextObject probabilityText = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
        probabilityText.getTransform().setOrigin(0, 1);
        probabilityText.getTransform().setPosition(60, drinksText.getTransform().getBottom() - 15);
        probabilityText.create("ZCOOL-30.ttf", () -> "Chance of increasing popularity: " +
                Math.max(100 - (getWorld().getDrinkCount() + 1) * 10, 5) + "%");
        probabilityText.load();
        probabilityText.pack();

        getScene().addObjects("Buttons", priceText, buyBtn, probabilityText);
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
