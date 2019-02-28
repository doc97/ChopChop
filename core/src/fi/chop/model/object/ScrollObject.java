package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.gui.TextObject;
import fi.chop.model.object.util.TextureRegionObject;
import fi.chop.model.world.Execution;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

import java.util.function.Supplier;

public class ScrollObject extends GameObject {

    private TextureRegionObject scrollBackground;
    private TextObject scrollText;

    public ScrollObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);

        Color textColor = new Color(62.5f / 255f, 44.5f / 255f, 15f / 255f, 1);
        scrollText = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
        scrollText.tint(textColor);
    }

    @Override
    public void load() {
        scrollBackground = new TextureRegionObject(getAssets(), getCamera(), getWorld(), getPlayer());
        scrollBackground.getTransform().setParent(getTransform());
        scrollBackground.getTransform().setAlign(Align.TOP_LEFT);
        scrollBackground.getTransform().setOrigin(0, 1);
        scrollBackground.getTransform().setScale(0.6f, 0.6f);
        scrollBackground.setRegion("textures/packed/Chop.atlas", "scroll-background");
        scrollBackground.load();

        scrollText.getTransform().setParent(getTransform());
        scrollText.getTransform().setAlign(Align.TOP_LEFT);
        scrollText.getTransform().setOrigin(0, 1);
        scrollText.getTransform().setPosition(48, -48);

        getTransform().setSize(575, 730);
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) { }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        scrollBackground.die();
        scrollText.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { scrollBackground, scrollText };
    }

    public void setExecution(Execution execution) {
        Supplier<String> textConstructor = () -> {
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
        };

        scrollText.create("Dance-30.ttf", textConstructor, textConstructor,
                575 - 2 * 48, com.badlogic.gdx.utils.Align.left, true);
        scrollText.load();
    }
}
