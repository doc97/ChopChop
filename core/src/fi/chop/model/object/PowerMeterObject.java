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
    private int round;

    public PowerMeterObject(AssetManager assets) {
        super(assets);
        meter = new PowerMeter();
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion("powermeter-background");
        fill = atlas.findRegion("powermeter-fill");
    }

    @Override
    public void update(float delta) {

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
        if (round < ROUND_COUNT) {
            meter.add(power / ROUND_COUNT);
            round++;
        } else {
            meter.set(0);
            round = 0;
        }
    }
}
