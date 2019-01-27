package fi.chop.model.object.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.engine.DrawParameters;
import fi.chop.model.ValueMeter;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.gui.GUIObject;
import fi.chop.model.world.Player;

public abstract class ValueMeterObject extends GUIObject {

    public enum FillDirection {
        LEFT, RIGHT, UP, DOWN
    }

    private final String containerAssetName;
    private final String fillAssetName;
    private final String fontName;
    private final ValueMeter meter;
    private final FillDirection direction;
    private final float textOriginX;
    private final float textOriginY;
    private final float textPaddingX;
    private final float textPaddingY;
    private final float meterOriginX;
    private final float meterOriginY;
    private final Align textAlign;
    private final Align meterAlign;
    private TextureRegionObject meterContainer;
    private TextureRegionObject meterFill;
    private TextObject labelText;

    protected ValueMeterObject(AssetManager assets, OrthographicCamera camera, Player player,
                               FillDirection direction, Align textAlign, Align meterAlign,
                               float textOriginX, float textOriginY, float textPaddingX, float textPaddingY,
                               float meterOriginX, float meterOriginY,
                               String containerAssetName, String fillAssetName, String fontName) {
        super(assets, camera, player);
        this.direction = direction;
        this.textAlign = textAlign;
        this.meterAlign = meterAlign;
        this.textOriginX = textOriginX;
        this.textOriginY = textOriginY;
        this.textPaddingX = textPaddingX;
        this.textPaddingY = textPaddingY;
        this.meterOriginX = meterOriginX;
        this.meterOriginY = meterOriginY;
        this.containerAssetName = containerAssetName;
        this.fillAssetName = fillAssetName;
        this.fontName = fontName;
        meter = new ValueMeter();
    }

    @Override
    public void pack() {
        float textWidth = labelText.getTransform().getWidth();
        float textHeight = labelText.getTransform().getHeight();
        float meterWidth = meterContainer.getTransform().getWidth();
        float meterHeight = meterContainer.getTransform().getHeight();
        float width = Math.max(meterWidth, textWidth);
        float height = Math.max(meterHeight, textHeight);

        if (textAlign.getHAlign() == Align.Horizontal.LEFT && meterAlign.getHAlign() == Align.Horizontal.RIGHT ||
                textAlign.getHAlign() == Align.Horizontal.RIGHT && meterAlign.getHAlign() == Align.Horizontal.LEFT)
            width = meterWidth + textWidth;

        if (textAlign.getVAlign() == Align.Vertical.TOP && meterAlign.getVAlign() == Align.Vertical.BOTTOM ||
                textAlign.getVAlign() == Align.Vertical.BOTTOM && meterAlign.getVAlign() == Align.Vertical.TOP)
            height = meterHeight + textHeight;

        getTransform().setSize(width, height);
    }

    @Override
    public void load() {
        meterContainer = new TextureRegionObject(getAssets(), getCamera(), getPlayer());
        initRegionObject(meterContainer, containerAssetName);

        meterFill = new TextureRegionObject(getAssets(), getCamera(), getPlayer());
        initRegionObject(meterFill, fillAssetName);

        labelText = new TextObject(getAssets(), getCamera(), getPlayer());
        labelText.create(fontName, this::getLabel);
        labelText.pad(textPaddingY, textPaddingX, textPaddingX, textPaddingY);
        labelText.load();
        labelText.pack();
        labelText.getTransform().setParent(getTransform());
        labelText.getTransform().setAlign(textAlign);
        labelText.getTransform().setOrigin(textOriginX, textOriginY);
    }

    private void initRegionObject(TextureRegionObject obj, String regionName) {
        obj.setRegion("textures/packed/Chop.atlas", regionName);
        obj.load();
        obj.getTransform().setParent(getTransform());
        obj.getTransform().setAlign(meterAlign);
        obj.getTransform().setOrigin(meterOriginX, meterOriginY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        updateFillMeter();
    }

    private void updateFillMeter() {
        DrawParameters params = meterFill.getParameters();
        float p = meter.getFillPercentage();
        float rx = meterFill.getRegionX();
        float ry = meterFill.getRegionY();
        float rw = meterFill.getRegionWidth();
        float rh = meterFill.getRegionHeight();
        float ox = meterFill.getTransform().getOriginX();
        float oy = meterFill.getTransform().getOriginY();

        switch (direction) {
            case UP:
                params.y = (1 - p) * rh * -oy;
                params.height = p * rh;
                params.srcY = Math.round(ry);
                params.srcHeight = Math.round(p * rh);
                break;
            case DOWN:
                params.y = (1 - p) * rh * (1 - oy);
                params.height = p * rh;
                params.srcY = Math.round(ry + (1 - p) * rh);
                params.srcHeight = Math.round(p * rh);
                break;
            case RIGHT:
                params.x = (1 - p) * rw * -ox;
                params.width = p * rw;
                params.srcX = Math.round(rx);
                params.srcWidth = Math.round(p * rw);
                break;
            case LEFT:
                params.x = (1 - p) * rw * (1 - ox);
                params.width = p * rw;
                params.srcX = Math.round(rx + (1 - p) * rw);
                params.srcWidth = Math.round(p * rw);
                break;
        }
    }

    @Override
    public void render(SpriteBatch batch) { }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        meterContainer.die();
        meterFill.die();
        labelText.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { meterContainer, meterFill, labelText };
    }

    protected abstract String getLabel();

    public void addMeterValue(float value) {
        meter.add(value);
        pack();
    }

    public void setMeterValue(float value) {
        meter.set(value);
        pack();
    }

    public float getMeterFillPercentage() {
        return meter.getFillPercentage();
    }
}
