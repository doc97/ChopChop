package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.TextButtonHandler;
import fi.chop.input.TownScreenInput;
import fi.chop.model.object.gui.*;

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
        getScene().addLayer("Buttons", new Layer());
        getScene().addLayer("Text", new Layer());
        getScene().addLayer("GUI", new Layer());
        getScene().addLayer("PopUp", new Layer());

        TextObject dayText = new TextObject(getAssets(), getCamera(), getPlayer());
        dayText.getTransform().setOrigin(0.5f, 1);
        dayText.getTransform().setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight - 30);
        dayText.create("ZCOOL-60.ttf", () -> "DAY " + getWorld().getDay());
        dayText.load();

        TextButtonObject castleBtn = new TextButtonObject(getAssets(), getCamera(), getPlayer());
        castleBtn.create("ZCOOL-40.ttf", () -> "Castle");
        castleBtn.load();
        castleBtn.pack();
        castleBtn.getTransform().setPosition(getCamera().viewportWidth / 2 - 150, getCamera().viewportHeight - 250);
        castleBtn.setTouchHandler(new TextButtonHandler(castleBtn, (btn) -> Gdx.app.log("Castle", "Go!")));
        castleBtn.disable();

        TextButtonObject tavernBtn = new TextButtonObject(getAssets(), getCamera(), getPlayer());
        tavernBtn.getTransform().setPosition(300, 250);
        tavernBtn.setHoverScale(1.1f, 1.1f);
        tavernBtn.create("ZCOOL-40.ttf", () -> "Tavern");
        tavernBtn.load();
        tavernBtn.setTouchHandler(new TextButtonHandler(tavernBtn, (btn) -> setScreen(Screens.TAVERN)));

        TextButtonObject guillotineBtn = new TextButtonObject(getAssets(), getCamera(), getPlayer());
        guillotineBtn.getTransform().setPosition(getCamera().viewportWidth - 350, 450);
        guillotineBtn.setHoverScale(1.1f, 1.1f);
        guillotineBtn.create("ZCOOL-40.ttf", () -> "Guillotine");
        guillotineBtn.load();
        guillotineBtn.setTouchHandler(new TextButtonHandler(guillotineBtn, (btn) -> setScreen(Screens.EXECUTION)));

        GUIObject gui = new GameGUIObject(getAssets(), getCamera(), getPlayer());
        gui.load();
        gui.pack();

        DialogBoxObject dialog = new DialogBoxObject(getAssets(), getCamera(), getPlayer());
        dialog.text("ZCOOL-40.ttf",
                () -> "Hello! My name is Zachary and I will guide you through the first few steps of " +
                        "ChopChop. We'll start with the (3) locations that can be accessed from this " +
                        "screen; Castle, Tavern and Guillotine.",
                Color.BLACK)
                .speed(0.5f, 0.025f)
                .width(getCamera().viewportWidth * 3 / 4f)
                .pad(25, 25, 25, 25)
                .tint(new Color(0xb5b5b5ff))
                .onFinish(() -> Gdx.app.log("Dialog", "Dismissed."));
        dialog.load();
        dialog.pack();
        dialog.getTransform().setOrigin(0.5f, 0.0f);
        dialog.getTransform().setPosition(getCamera().viewportWidth / 2, 50);
        Chop.events.addListener(dialog, Events.ACTION_INTERACT);

        PopUpBoxObject popUp = new PopUpBoxObject(getAssets(), getCamera(), getPlayer());
        popUp.text("ZCOOL-40.ttf",
                () -> "Welcome to ChopChop!\n\n" +
                        "Guillotine: Execute people and earn your pay\n" +
                        "Tavern: Drink and socialize to raise your popularity\n" +
                        "Castle: (Coming soon)",
                Color.BLACK)
                .btn("ZCOOL-40.ttf", () -> "OK!", (btn) -> popUp.die())
                .pad(25, 25, 25, 25)
                .size(getCamera().viewportWidth / 3, getCamera().viewportHeight / 3)
                .tint(new Color(0xb5e8f2ff));
        popUp.load();
        popUp.pack();
        popUp.getTransform().setOrigin(0.5f, 0.5f);
        popUp.getTransform().setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight / 2);

        getScene().addObjects("Buttons", castleBtn, tavernBtn, guillotineBtn);
        getScene().addObjects("Text", dayText);
        getScene().addObjects("GUI", gui, dialog);
        getScene().addObjects("PopUp", popUp);
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
        }
    }
}
