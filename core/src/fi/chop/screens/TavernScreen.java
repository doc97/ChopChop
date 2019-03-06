package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.BasicScreenInput;
import fi.chop.input.touchhandler.AreaHandler;
import fi.chop.model.object.AreaObject;
import fi.chop.model.object.gui.GUIObject;
import fi.chop.model.object.gui.GameGUIObject;
import fi.chop.model.object.gui.TextObject;
import fi.chop.model.object.util.TextureObject;
import fi.chop.sound.MusicType;

import java.util.Random;
import java.util.function.Consumer;

public class TavernScreen extends ChopScreen implements EventListener {

    private static final int DRINK_PRICE = 10;
    private static final float POPULARITY_DELTA = 0.05f;

    private TextureObject background;
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


        Consumer<AreaHandler> drinkOnUp = (handler) -> {
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
                Gdx.app.log("Tavern", "Not enough money!");
            }
        };
        Consumer<AreaHandler> drinkOnMove = (handler) -> {
            String texture = "textures/tavern_screen/Background_Drink.png";
            if (handler.isOver() && !background.getTexture().equals(texture)) {
                background.setTexture(texture);
                background.load();
            }
        };
        Consumer<AreaHandler> drinkOnExit = (handler) -> {
            String texture = "textures/tavern_screen/Background_Neutral.png";
            if (!background.getTexture().equals(texture)) {
                background.setTexture(texture);
                background.load();
            }
        };

        background = new TextureObject(getAssets(), getCamera(), getWorld(), getPlayer());
        background.getTransform().setSize(1920, 1080);
        background.setTexture("textures/tavern_screen/Background_Neutral.png");
        background.load();

        AreaObject drinkArea = new AreaObject(getAssets(), getCamera(), getWorld(), getPlayer());
        drinkArea.setDebug(true);
        drinkArea.getTransform().setPosition(
                getCamera().viewportWidth / 2f + 130,
                getCamera().viewportHeight / 2f - 75);
        drinkArea.getTransform().setSize(128, 128);
        drinkArea.setTouchable(true);
        drinkArea.setTouchHandler(new AreaHandler(drinkArea)
                .onUp(drinkOnUp)
                .onMove(drinkOnMove)
                .onExit(drinkOnExit)
        );

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

        getScene().addObjects("Background", background);
        getScene().addObjects("Buttons", priceText, probabilityText, drinkArea);
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
