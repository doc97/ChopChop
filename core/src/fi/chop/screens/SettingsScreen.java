package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.BasicScreenInput;
import fi.chop.model.object.util.ValueSliderObject;

public class SettingsScreen extends ChopScreen implements EventListener {

    public SettingsScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new BasicScreenInput(this, getInputMap()));
        Chop.events.addListener(this, Events.ACTION_BACK);

        ValueSliderObject slider = new ValueSliderObject(getAssets(), getCamera(), getWorld(), getPlayer());
        slider.init("textures/packed/Chop.atlas", "meter-background", "knob",
                "ZCOOL-40.ttf", () -> String.format("%.0f", slider.getValue() * 100) + "%",
                () -> "100%");
        slider.load();
        slider.pack();
        slider.getTransform().setOrigin(0.5f, 0.5f);
        slider.getTransform().setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight / 3);

        getScene().addLayer("Controls", new Layer());
        getScene().addObjects("Controls", slider);
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
            setScreen(Screens.MAIN_MENU);
    }
}
