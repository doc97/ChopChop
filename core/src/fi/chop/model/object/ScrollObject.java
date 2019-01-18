package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.model.world.Execution;

public class ScrollObject extends GameObject {

    private Execution execution;
    private TextureRegion scrollTexture;
    private DrawParameters scrollParams;
    private BitmapFont font;
    private final Color textColor;

    public ScrollObject(AssetManager assets, OrthographicCamera camera) {
        super(assets, camera);
        textColor = new Color(62.5f / 255f, 44.5f / 255f, 15f / 255f, 1);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        scrollTexture = atlas.findRegion("scroll-background");
        scrollParams = new DrawParameters(scrollTexture);
        font = getAssets().get("Dance-30.ttf", BitmapFont.class);

        setSize(scrollTexture.getRegionWidth(), scrollTexture.getRegionHeight());
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) {
        drawBackground(batch);
        drawText(batch);
    }

    private void drawBackground(SpriteBatch batch) {
        draw(batch, scrollTexture, scrollParams);
    }

    private void drawText(SpriteBatch batch) {
        float offset = getWidth() * 0.07f;
        float leftX = getX() - getOriginX() * getWidth();
        float drawX = leftX + offset;

        float topY = getY() + (1 - getOriginY()) * getHeight();
        float drawY = topY - offset;

        StringBuilder text = new StringBuilder();
        text.append("Name: ").append(execution.getVictimName()).append("\n");
        text.append("Social status: ").append(execution.getVictimSocialStatus()).append("\n");
        text.append("\n");
        text.append("Charges: ").append("\n");
        for (String charge : execution.getCharges())
            text.append(" - ").append(charge).append("\n");
        text.append("Sentence: Death penalty").append("\n");
        text.append("\n");
        text.append("Crowd's opinion: ").append(execution.isFairPunishment() ? "Fair :)" : "Unfair :(").append("\n");
        text.append("\n");
        text.append("Bribe: ").append(execution.getBribe()).append("$").append("\n");

        font.getCache().clear();
        font.getCache().addText(text.toString(), drawX, drawY);
        font.getCache().tint(textColor);
        font.getCache().draw(batch);
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }
}
