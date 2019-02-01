package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.BasicScreenInput;
import fi.chop.model.object.gui.TextObject;
import fi.chop.model.object.gui.VolumeSliderObject;
import fi.chop.model.object.util.ValueSliderObject;

public class SettingsScreen extends ChopScreen implements EventListener {

    public SettingsScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new BasicScreenInput(this, getInputMap()));
        Chop.events.addListener(this, Events.ACTION_BACK);


        VolumeSliderObject musicVolumeSlider = new VolumeSliderObject(getAssets(), getCamera(), getWorld(), getPlayer());
        musicVolumeSlider.init("textures/packed/Chop.atlas", "meter-background", "knob",
                "ZCOOL-40.ttf", "Music volume");
        musicVolumeSlider.load();
        musicVolumeSlider.pack();
        musicVolumeSlider.getTransform().setOrigin(0.5f, 0.5f);
        musicVolumeSlider.getTransform().setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight / 3);

        getScene().addLayer("Controls", new Layer());
        getScene().addObjects("Controls", musicVolumeSlider);
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
