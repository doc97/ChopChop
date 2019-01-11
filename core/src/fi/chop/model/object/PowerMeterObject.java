package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.model.PowerMeter;
import fi.chop.model.fsm.machines.PowerMeterStateMachine;
import fi.chop.model.fsm.states.powermeter.PowerMeterStates;

public class PowerMeterObject extends GameObject {

    public static final int ROUND_COUNT = 3;

    private TextureRegion background;
    private TextureRegion fill;
    private PowerMeter meter;
    private PowerMeterStateMachine state;
    private int round;
    private float toAdd;

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
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        drawBackground(batch);
        drawFill(batch);
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

    public void addPower(float power) {
        if (state.getCurrent() != PowerMeterStates.IDLE)
            return;

        toAdd = power / ROUND_COUNT;
        round++;
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

    public void setRound(int round) {
        this.round = round;
    }

    public int getRound() {
        return round;
    }
}
