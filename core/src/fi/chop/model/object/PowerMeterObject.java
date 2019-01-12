package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.model.DrawParameters;
import fi.chop.model.PowerMeter;
import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.fsm.states.powermeter.PowerMeterStates;

public class PowerMeterObject extends GameObject implements EventListener {

    private final PowerMeter meter;
    private final PowerMeterStateMachine state;
    private TextureRegion background;
    private TextureRegion fill;
    private DrawParameters fillParams;
    private BitmapFont font;
    private float toAdd;
    private boolean ready;

    public PowerMeterObject(AssetManager assets) {
        super(assets);
        meter = new PowerMeter();
        state = new PowerMeterStateMachine(this);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion("powermeter-background");
        fill = atlas.findRegion("powermeter-fill");
        font = getAssets().get("fonts/ZCOOL-Regular.ttf", BitmapFont.class);
        setSize(background.getRegionWidth(), background.getRegionHeight());

        fillParams = new DrawParameters(fill);
        fillParams.srcX = fill.getRegionX();
        fillParams.srcY = fill.getRegionY();
        fillParams.srcWidth = fill.getRegionWidth();
        fillParams.srcHeight = fill.getRegionHeight();
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        drawBackground(batch);
        drawFill(batch);
        drawPercent(batch);
    }

    private void drawBackground(SpriteBatch batch) {
        batch.draw(background, getX(), getY());
    }

    private void drawFill(SpriteBatch batch) {
        fillParams.height = fill.getRegionHeight() * meter.getFillPercentage();
        draw(batch, fill.getTexture(), fillParams);
    }

    private void drawPercent(SpriteBatch batch) {
        float drawX = getX() + getWidth() + 10;
        float drawY = getY() + font.getLineHeight();
        String percentStr = String.format("%.1f", meter.getFillPercentage() * 100);
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

    public void addMeterPower(float power) {
        meter.add(power);
    }

    public float getMeterFillPercentage() {
        return meter.getFillPercentage();
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
