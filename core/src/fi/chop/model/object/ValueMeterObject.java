package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.model.ValueMeter;
import fi.chop.model.world.Player;
import fi.chop.util.FontRenderer;

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
    private FontRenderer labelText;

    protected ValueMeterObject(AssetManager assets, OrthographicCamera camera, Player player,
                               FillDirection direction, TextOriginX textOriginX, TextOriginY textOriginY,
                               float textOriginXOffset, float textOriginYOffset,
                               float textSpacingX, float textSpacingY,
                               String backgroundAssetName, String fillAssetName, String fontName) {
        super(assets, camera, player);
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

        BitmapFont font = getAssets().get(fontName, BitmapFont.class);
        labelText = new FontRenderer(font);

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
                fillParams.y = (1 - p) * rh * -oy;
                fillParams.height = p * rh;
                fillParams.srcY = Math.round(ry);
                fillParams.srcHeight = Math.round(p * rh);
                break;
            case DOWN:
                fillParams.y = (1 - p) * rh * (1 - oy);
                fillParams.height = p * rh;
                fillParams.srcY = Math.round(ry + (1 - p) * rh);
                fillParams.srcHeight = Math.round(p * rh);
                break;
            case RIGHT:
                fillParams.x = (1 - p) * rw * -ox;
                fillParams.width = p * rw;
                fillParams.srcX = Math.round(rx);
                fillParams.srcWidth = Math.round(p * rw);
                break;
            case LEFT:
                fillParams.x = (1 - p) * rw * (1 - ox);
                fillParams.width = p * rw;
                fillParams.srcX = Math.round(rx + (1 - p) * rw);
                fillParams.srcWidth = Math.round(p * rw);
                break;
        }
        draw(batch, fill.getTexture(), fillParams);
    }

    private void drawLabel(SpriteBatch batch) {
        if (textOriginX == TextOriginX.NONE || textOriginY == TextOriginY.NONE)
            return;

        labelText.text(getLabel());
        float drawX = getX() + textSpacingX;
        float drawY = getY() - textSpacingY;

        if (textOriginX == TextOriginX.RIGHT)
            drawX = getX() - labelText.width() - textSpacingX;
        if (textOriginY == TextOriginY.BOTTOM)
            drawY = getY() + labelText.getLineHeight() + textSpacingY;

        drawX += (-getOriginX() + textOriginXOffset) * getWidth();
        drawY += (-getOriginY() + textOriginYOffset) * getHeight();

        labelText.pos(drawX, drawY).draw(batch);
    }

    @Override
    public void dispose() { }

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
