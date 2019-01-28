package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.gui.TextObject;
import fi.chop.model.world.Execution;
import fi.chop.model.world.Player;

public class ScrollObject extends GameObject {

    private TextureRegion scrollTexture;
    private DrawParameters scrollParams;
    private TextObject scrollText;

    public ScrollObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);

        Color textColor = new Color(62.5f / 255f, 44.5f / 255f, 15f / 255f, 1);
        scrollText = new TextObject(getAssets(), getCamera(), getPlayer());
        scrollText.tint(textColor);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        scrollTexture = atlas.findRegion("scroll-background");
        scrollParams = new DrawParameters(scrollTexture);

        getTransform().setSize(scrollTexture.getRegionWidth(), scrollTexture.getRegionHeight());
        scrollText.getTransform().setParent(getTransform());
        scrollText.getTransform().setOrigin(0, 1);
        scrollText.getTransform().setPosition(32, -32);
        scrollText.getTransform().setAlign(Align.TOP_LEFT);
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) {
        draw(batch, scrollTexture, scrollParams);
    }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        scrollText.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { scrollText };
    }

    public void setExecution(Execution execution) {
        scrollText.create("Dance-30.ttf", () -> {
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
            text.append("Salary: ").append(execution.getSalary()).append(" gold").append("\n");
            text.append("Bribe: ").append(execution.getBribe()).append(" gold").append("\n");
            return text.toString();
        });
        scrollText.load();
    }
}
