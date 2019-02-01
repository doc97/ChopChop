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

        VolumeSliderObject masterVolumeSlider = initVolumeSlider("Master volume");
        masterVolumeSlider.getTransform().setPosition(
                getCamera().viewportWidth / 2, getCamera().viewportHeight * 2 / 3);

        VolumeSliderObject musicVolumeSlider = initVolumeSlider("Music volume");
        musicVolumeSlider.getTransform().setPosition(
                getCamera().viewportWidth / 2, masterVolumeSlider.getTransform().getBottom() - 30);

        VolumeSliderObject soundVolumeSlider = initVolumeSlider("Sound volume");
        soundVolumeSlider.getTransform().setPosition(
                getCamera().viewportWidth / 2, musicVolumeSlider.getTransform().getBottom() - 30);

        getScene().addLayer("Controls", new Layer());
        getScene().addObjects("Controls", masterVolumeSlider, musicVolumeSlider, soundVolumeSlider);
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

    private VolumeSliderObject initVolumeSlider(String label) {
        VolumeSliderObject slider = new VolumeSliderObject(getAssets(), getCamera(), getWorld(), getPlayer());
        slider.init("textures/packed/Chop.atlas", "meter-background", "knob",
                "ZCOOL-40.ttf", label);
        slider.load();
        slider.pack();
        slider.getTransform().setOrigin(0.5f, 1);
        return slider;
    }
}
