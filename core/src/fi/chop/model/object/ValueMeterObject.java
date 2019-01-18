package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.model.ValueMeter;

public abstract class ValueMeterObject extends GameObject {

    private final String backgroundAssetName;
    private final String fillAssetName;

    private final ValueMeter meter;
    private TextureRegion background;
    private TextureRegion fill;
    private DrawParameters fillParams;

    protected ValueMeterObject(AssetManager assets, OrthographicCamera camera,
                               String backgroundAssetName, String fillAssetName) {
        super(assets, camera);
        this.backgroundAssetName = backgroundAssetName;
        this.fillAssetName = fillAssetName;
        meter = new ValueMeter();
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion(backgroundAssetName);
        fill = atlas.findRegion(fillAssetName);
        setSize(background.getRegionWidth(), background.getRegionHeight());

        fillParams = new DrawParameters(fill);
        fillParams.srcX = fill.getRegionX();
        fillParams.srcY = fill.getRegionY();
        fillParams.srcWidth = fill.getRegionWidth();
        fillParams.srcHeight = fill.getRegionHeight();
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
        fillParams.height = fill.getRegionHeight() * meter.getFillPercentage();
        draw(batch, fill.getTexture(), fillParams);
    }

    public void addMeterValue(float value) {
        meter.add(value);
    }

    public float getMeterFillPercentage() {
        return meter.getFillPercentage();
    }
}
