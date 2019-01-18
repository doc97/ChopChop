package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.fsm.states.powermeter.PowerMeterStates;

public class PowerMeterObject extends ValueMeterObject implements EventListener {

    private final PowerMeterStateMachine state;
    private float toAdd;
    private boolean ready;

    public PowerMeterObject(AssetManager assets, OrthographicCamera camera) {
        super(assets, camera, FillDirection.UP, TextOriginX.LEFT, TextOriginY.BOTTOM,
                1, 0, 10, 0,
                "powermeter-background", "powermeter-fill", "ZCOOL-40.ttf");
        state = new PowerMeterStateMachine(this);
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    protected String getLabel() {
        return String.format("%.1f", getMeterFillPercentage() * 100) + "%";
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
