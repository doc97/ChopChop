package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import fi.chop.engine.DrawParameters;
import fi.chop.model.ValueMeter;

public abstract class ValueMeterObject extends GameObject {

    public enum FillDirection {
        LEFT, RIGHT, UP, DOWN
    }

    public enum TextOriginX {
        NONE, LEFT, RIGHT
    }

    public enum TextOriginY {
        NONE, TOP, BOTTOM
    }

    private final String backgroundAssetName;
    private final String fillAssetName;
    private final String fontName;

    private final ValueMeter meter;
    private final FillDirection direction;
    private final TextOriginX textOriginX;
    private final TextOriginY textOriginY;
    private final float textOriginXOffset;
    private final float textOriginYOffset;
    private final float textSpacingX;
    private final float textSpacingY;
    private TextureRegion background;
    private TextureRegion fill;
    private DrawParameters backgroundParams;
    private DrawParameters fillParams;
    private BitmapFont font;

    protected ValueMeterObject(AssetManager assets, OrthographicCamera camera,
                               FillDirection direction, TextOriginX textOriginX, TextOriginY textOriginY,
                               float textOriginXOffset, float textOriginYOffset,
                               float textSpacingX, float textSpacingY,
                               String backgroundAssetName, String fillAssetName, String fontName) {
        super(assets, camera);
        this.direction = direction;
        this.textOriginX = textOriginX;
        this.textOriginY = textOriginY;
        this.textOriginXOffset = textOriginXOffset;
        this.textOriginYOffset = textOriginYOffset;
        this.textSpacingX = textSpacingX;
        this.textSpacingY = textSpacingY;
        this.backgroundAssetName = backgroundAssetName;
        this.fillAssetName = fillAssetName;
        this.fontName = fontName;
        meter = new ValueMeter();
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion(backgroundAssetName);
        fill = atlas.findRegion(fillAssetName);
        font = getAssets().get(fontName, BitmapFont.class);

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
        drawLabel(batch);
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

    private void drawLabel(SpriteBatch batch) {
        if (textOriginX == TextOriginX.NONE || textOriginY == TextOriginY.NONE)
            return;

        String text = getLabel();
        GlyphLayout layout = new GlyphLayout(font, text);
        float drawX = getX() + textSpacingX;
        float drawY = getY() - textSpacingY;

        if (textOriginX == TextOriginX.RIGHT)
            drawX = getX() - layout.width - textSpacingX;
        if (textOriginY == TextOriginY.BOTTOM)
            drawY = getY() + font.getLineHeight() + textSpacingY;

        drawX += textOriginXOffset * getWidth();
        drawY += textOriginYOffset * getHeight();

        font.draw(batch, text, drawX, drawY);
    }

    protected abstract String getLabel();

    public void addMeterValue(float value) {
        meter.add(value);
    }

    public void setMeterValue(float value) {
        meter.set(value);
    }

    public float getMeterFillPercentage() {
        return meter.getFillPercentage();
    }
}
