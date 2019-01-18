package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.model.ValueMeter;

public abstract class ValueMeterObject extends GameObject {

    public enum FillDirection {
        LEFT, RIGHT, UP, DOWN
    }

    private final String backgroundAssetName;
    private final String fillAssetName;

    private final ValueMeter meter;
    private final FillDirection direction;
    private TextureRegion background;
    private TextureRegion fill;
    private DrawParameters backgroundParams;
    private DrawParameters fillParams;

    protected ValueMeterObject(AssetManager assets, OrthographicCamera camera, FillDirection direction,
                               String backgroundAssetName, String fillAssetName) {
        super(assets, camera);
        this.direction = direction;
        this.backgroundAssetName = backgroundAssetName;
        this.fillAssetName = fillAssetName;
        meter = new ValueMeter();
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion(backgroundAssetName);
        fill = atlas.findRegion(fillAssetName);

        backgroundParams = new DrawParameters(background);
        fillParams = new DrawParameters(fill);
        fillParams.srcX = fill.getRegionX();
        fillParams.srcY = fill.getRegionY();
        fillParams.srcWidth = fill.getRegionWidth();
        fillParams.srcHeight = fill.getRegionHeight();

        setSize(background.getRegionWidth(), background.getRegionHeight());
    }

    @Override
    public void render(SpriteBatch batch) {
        drawBackground(batch);
        drawFill(batch);
    }

    private void drawBackground(SpriteBatch batch) {
        draw(batch, background, backgroundParams);
    }

    private void drawFill(SpriteBatch batch) {
        float p = meter.getFillPercentage();
        float rx = fill.getRegionX();
        float ry = fill.getRegionY();
        float rw = fill.getRegionWidth();
        float rh = fill.getRegionHeight();
        float ox = getOriginX();
        float oy = getOriginY();

        switch (direction) {
            case UP:
                fillParams.height = p * rh;
                break;
            case DOWN:
                fillParams.y = (1 - p) * rh * (1 - oy);
                fillParams.srcY = Math.round(ry + (1 - p) * rh);
                fillParams.srcHeight = Math.round(p * rh);
                fillParams.height = p * rh;
                break;
            case RIGHT:
                fillParams.width = p * rw;
                break;
            case LEFT:
                fillParams.x = (1 - p) * rw * (1 - ox);
                fillParams.srcX = Math.round(rx + (1 - p) * rw);
                fillParams.srcWidth = Math.round(p * rw);
                fillParams.width = p * rw;
                break;
        }
        draw(batch, fill.getTexture(), fillParams);
    }

    public void addMeterValue(float value) {
        meter.add(value);
    }

    public float getMeterFillPercentage() {
        return meter.getFillPercentage();
    }
}
