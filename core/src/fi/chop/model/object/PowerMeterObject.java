package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.model.PowerMeter;
import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.fsm.states.powermeter.PowerMeterStates;

public class PowerMeterObject extends GameObject implements EventListener {

    private final PowerMeter meter;
    private final PowerMeterStateMachine state;
    private TextureRegion background;
    private TextureRegion fill;
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
        float originX = 0;
        float originY = 0;
        float scaleX = 1;
        float scaleY = 1;
        float rotationDeg = (float) getRotationDeg();

        int srcX = fill.getRegionX();
        int srcY = fill.getRegionY();
        int srcWidth = fill.getRegionWidth();
        int srcHeight = fill.getRegionHeight();

        float drawWidth = fill.getRegionWidth();
        float drawHeight = fill.getRegionHeight() * meter.getFillPercentage();
        float drawX = getX();
        float drawY = getY();
        batch.draw(
                fill.getTexture(),
                drawX, drawY,
                originX, originY,
                scaleX, scaleY,
                drawWidth, drawHeight,
                rotationDeg,
                srcX, srcY,
                srcWidth, srcHeight,
                false, false
                );
    }

    private void drawPercent(SpriteBatch batch) {
        float drawX = getX() + background.getRegionWidth() + 10;
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
