package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.model.PowerMeter;

public class PowerMeterObject extends GameObject {

    private static final int ROUND_COUNT = 3;

    private TextureRegion background;
    private TextureRegion fill;
    private PowerMeter meter;
    private PowerMeterStates state;
    private int round;
    private float toAdd;

    public PowerMeterObject(AssetManager assets) {
        super(assets);
        meter = new PowerMeter();
        state = PowerMeterStates.IDLE;
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion("powermeter-background");
        fill = atlas.findRegion("powermeter-fill");
    }

    @Override
    public void update(float delta) {
        if (state == PowerMeterStates.POWER_DOWN) {
            float meterDelta = -Math.max(2 * meter.getFillPercentage() * delta, 0.01f);
            meter.add(meterDelta);
            if (meter.getFillPercentage() == 0) {
                state = PowerMeterStates.IDLE;
                round = 0;
            }
        } else if (state == PowerMeterStates.POWER_UP) {
            float meterDelta = Math.min(delta, toAdd);
            meter.add(meterDelta);
            toAdd -= meterDelta;

            if (toAdd <= 0) {
                if (round == ROUND_COUNT)
                    state = PowerMeterStates.POWER_DOWN;
                else
                    state = PowerMeterStates.IDLE;
            }
        }
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
        if (state != PowerMeterStates.IDLE)
            return;

        toAdd = power / ROUND_COUNT;
        round++;
        state = PowerMeterStates.POWER_UP;
    }
}
