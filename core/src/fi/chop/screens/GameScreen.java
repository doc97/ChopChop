package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.GameScreenInput;
import fi.chop.model.fsm.states.guillotine.GuillotineStates;
import fi.chop.model.fsm.states.powermeter.PowerMeterStates;
import fi.chop.model.object.GuillotineObject;
import fi.chop.model.object.PowerBarObject;
import fi.chop.model.object.PowerMeterObject;

public class GameScreen extends ChopScreen implements EventListener {

    private PowerBarObject powerBar;
    private PowerMeterObject powerMeter;
    private GuillotineObject guillotine;

    public GameScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Chop.events.addListener(this, Events.ACTION_BACK, Events.ACTION_INTERACT);
        Gdx.input.setInputProcessor(new GameScreenInput(this, getInputMap()));

        powerBar = new PowerBarObject(getAssets());
        powerBar.setPosition(getCamera().viewportWidth * 3 / 4, getCamera().viewportHeight / 2 - 200);
        powerMeter = new PowerMeterObject(getAssets());
        powerMeter.setPosition(powerBar.getX() + 100 + 10, powerBar.getY());
        guillotine = new GuillotineObject(getAssets());
        guillotine.setPosition(getCamera().viewportWidth / 4, 100);

        powerBar.load();
        powerMeter.load();
        guillotine.load();
    }

    @Override
    public void hide() {
        Chop.events.clear();
    }

    @Override
    protected void update(float delta) {
        powerBar.update(delta);
        powerMeter.update(delta);
        guillotine.update(delta);
    }

    @Override
    protected void render(SpriteBatch batch) {
        beginRender();
        batch.begin();
        powerBar.render(batch);
        powerMeter.render(batch);
        guillotine.render(batch);
        batch.end();
    }

    @Override
    public void handle(Events event, EventData data) {
        switch (event) {
            case ACTION_INTERACT:
                boolean isMeterIdle = powerMeter.getState() == PowerMeterStates.IDLE;
                boolean isGuillotineIdle = guillotine.getState() == GuillotineStates.IDLE;
                if (isMeterIdle && isGuillotineIdle) {
                    powerMeter.addPower(powerBar.getValue());
                    guillotine.raiseBlade(powerBar.getValue());
                }
                break;
            case ACTION_BACK:
                Gdx.app.exit();
                break;
        }
    }
}
