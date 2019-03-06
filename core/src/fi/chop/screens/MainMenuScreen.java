package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.BasicScreenInput;
import fi.chop.input.touchhandler.TextButtonHandler;
import fi.chop.model.object.gui.TextButtonObject;
import fi.chop.model.object.gui.TextButtonStyle;
import fi.chop.model.object.gui.TextObject;
import fi.chop.model.world.PopularityPerk;
import fi.chop.sound.SoundType;

import java.util.function.Consumer;

public class MainMenuScreen extends ChopScreen implements EventListener {

    public MainMenuScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new BasicScreenInput(this, getInputMap()));
        Chop.events.addListener(this, Events.ACTION_BACK);

        getWorld().reset();

        TextObject title = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
        title.pad(25, 25, 25, 50);
        title.create("Roboto-180.ttf", () -> "ChopChop!");
        title.load();
        title.pack();
        title.getTransform().setOrigin(0.5f, 1);
        title.getTransform().setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight - 100);

        Consumer<TextButtonObject> onEnter = (btn) -> getSounds().playOnce(SoundType.MENU_MOVE);

        float x = 100;
        TextButtonObject playBtn = initButton("Roboto-120.ttf", "Play");
        playBtn.setTouchHandler(new TextButtonHandler(playBtn)
                .onClick((btn) -> {
                    getSounds().playOnce(SoundType.MENU_SELECT);
                    gotoTown();
                })
                .onEnter(onEnter)
        );
        playBtn.pack();
        playBtn.getTransform().setPosition(
                x + playBtn.getTransform().getWidth() / 2,
                getCamera().viewportHeight * 3 / 5
        );

        TextButtonObject settingsBtn = initButton("Roboto-60.ttf", "Settings");
        settingsBtn.setTouchHandler(new TextButtonHandler(settingsBtn)
                .onClick((btn) -> {
                    getSounds().playOnce(SoundType.MENU_SELECT);
                    setScreen(Screens.SETTINGS);
                })
                .onEnter(onEnter)
        );
        settingsBtn.pack();
        settingsBtn.getTransform().setPosition(
                x + settingsBtn.getTransform().getWidth() / 2,
                playBtn.getTransform().getBottom() - settingsBtn.getTransform().getHeight() / 2 - 90
        );

        TextButtonObject exitBtn = initButton("Roboto-60.ttf", "Exit");
        exitBtn.setTouchHandler(new TextButtonHandler(exitBtn)
                .onClick((btn) -> {
                    getSounds().playOnce(SoundType.MENU_SELECT);
                    Gdx.app.exit();
                })
                .onEnter(onEnter)
        );
        exitBtn.pack();
        exitBtn.getTransform().setPosition(
                x + exitBtn.getTransform().getWidth() / 2,
                settingsBtn.getTransform().getBottom() - exitBtn.getTransform().getHeight() / 2 - 15
        );

        getScene().addLayer("Text", new Layer());
        getScene().addLayer("Buttons", new Layer());
        getScene().addObjects("Text", title);
        getScene().addObjects("Buttons", playBtn, settingsBtn, exitBtn);
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
            Gdx.app.exit();
    }

    private TextButtonObject initButton(String fontName, String text) {
        TextButtonObject btn = new TextButtonObject(getAssets(), getCamera(), getWorld(), getPlayer());
        btn.create(fontName, () -> text);
        btn.load();
        btn.setStyle(TextButtonObject.StyleType.NORMAL, new TextButtonStyle());
        btn.setStyle(TextButtonObject.StyleType.HOVER, new TextButtonStyle().tint(Color.GOLDENROD));
        btn.setStyle(TextButtonObject.StyleType.PRESSED, new TextButtonStyle().tint(Color.GOLDENROD));
        btn.setHoverOffset(20, 0);
        btn.setPressedOffset(20, 0);
        btn.setPressedScale(0.95f, 0.95f);
        return btn;
    }

    private void gotoTown() {
        getPlayer().randomizePopularityPerks();
        getPlayer().addPerks(PopularityPerk.values());
        getWorld().nextDay();
        setScreen(Screens.TOWN);
        Chop.events.notify(Events.EVT_NEW_GAME);
    }
}
