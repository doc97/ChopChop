package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.event.TooltipData;
import fi.chop.model.object.GameObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public class TooltipObject extends GUIObject {

    private String tooltipText;
    private TextObject tooltip;

    public TooltipObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        tooltipText = "";
    }

    @Override
    public void pack() {
        tooltip.pack();
        tooltip.getTransform().setPosition(getPadLeft(), -getPadTop());
        getTransform().setSize(tooltip.getTransform().getScaledWidth() + getPadLeft() + getPadRight(),
                tooltip.getTransform().getScaledHeight() + getPadTop() + getPadBottom());
    }

    @Override
    public void load() {
        tooltip = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
        tooltip.create("ZCOOL-30.ttf", () -> tooltipText, () -> tooltipText, 200,
                Align.left, true);
        tooltip.load();
        tooltip.bgColor(Color.DARK_GRAY);
        tooltip.getTransform().setParent(getTransform());
        tooltip.getTransform().setOrigin(0.5f, 1);
        tooltip.toggleOff(GameObject.Toggles.UPDATE, GameObject.Toggles.RENDER);
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) { }

    @Override
    public void dispose() { }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.MSG_ADD_TOOLTIP) {
            TooltipData tooltipData = (TooltipData) data.get();
            setTooltip(tooltipData.tooltip, tooltipData.x, tooltipData.y);
        } else if (event == Events.MSG_REMOVE_TOOLTIP) {
            tooltip.toggleOff(GameObject.Toggles.UPDATE, GameObject.Toggles.RENDER);
        }
    }

    @Override
    public void die() {
        super.die();
        tooltip.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { tooltip };
    }

    private void setTooltip(String text, float x, float y) {
        tooltipText = text;
        float width = tooltip.getMaxWidth() * tooltip.getTransform().getScaleX();
        float height = tooltip.getTransform().getScaledHeight();
        float tipX = x;
        float tipY = y;

        if (x < width / 2)
            tipX = width / 2;
        else if (x > getCamera().viewportWidth - width / 2)
            tipX = getCamera().viewportWidth - width / 2;

        if (y < height / 2)
            tipY = height / 2;
        if (y > getCamera().viewportHeight - height / 2)
            tipY = getCamera().viewportHeight - height / 2;

        getTransform().setPosition(tipX, tipY);
        tooltip.toggleOn(GameObject.Toggles.UPDATE, GameObject.Toggles.RENDER);
        tooltip.invalidate();
    }
}
