package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.fsm.states.powermeter.PowerMeterStates;

public class PowerMeterObject extends ValueMeterObject implements EventListener {

    private final PowerMeterStateMachine state;
    private BitmapFont font;
    private float toAdd;
    private boolean ready;

    public PowerMeterObject(AssetManager assets, OrthographicCamera camera) {
        super(assets, camera, FillDirection.UP, "powermeter-background", "powermeter-fill");
        state = new PowerMeterStateMachine(this);
    }

    @Override
    public void load() {
        super.load();
        font = getAssets().get("ZCOOL-40.ttf", BitmapFont.class);
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        drawPercent(batch);
    }

    private void drawPercent(SpriteBatch batch) {
        float drawX = getX() + getWidth() + 10;
        float drawY = getY() + font.getLineHeight();
        String percentStr = String.format("%.1f", getMeterFillPercentage() * 100);
        font.draw(batch, percentStr + "%", drawX, drawY);
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.EVT_GUILLOTINE_RAISED)
            ready = true;
    }

    public PowerMeterStates getState() {
        return state.getCurrent();
    }

    public void addPower(float power) {
        toAdd = power / GuillotineObject.MAX_RAISE_COUNT;
        state.setCurrent(PowerMeterStates.POWER_UP);
    }

    public void setToAdd(float toAdd) {
        this.toAdd = toAdd;
    }

    public float getToAdd() {
        return toAdd;
    }

    public void resetReady() {
        ready = false;
    }

    public boolean isReady() {
        return ready;
    }
}
