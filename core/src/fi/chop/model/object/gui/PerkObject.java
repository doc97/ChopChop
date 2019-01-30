package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.util.TextureRegionObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.PopularityPerk;
import fi.chop.model.world.WorldState;

public class PerkObject extends GameObject {

    private static final int SPACING_X_PX = 10;

    private TextureRegionObject eye;
    private TextureRegionObject gift;
    private TextureRegionObject heart;

    public PerkObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
    }

    @Override
    public void load() {
        String atlasName = "textures/packed/Chop.atlas";
        eye = loadTextureRegionObject(atlasName, "ic-perk-turning-a-blind-eye",
                "You won't lose popularity during the next execution");
        gift = loadTextureRegionObject(atlasName, "ic-perk-gift-of-the-people",
                "If you follow the crowd's opinion, your earnings will be doubled");
        heart = loadTextureRegionObject(atlasName, "ic-perk-meaningful-work",
                "The next execution will be easier to perform");

        float totalWidth = eye.getTransform().getScaledWidth() + gift.getTransform().getScaledWidth() +
                heart.getTransform().getScaledWidth() + 2 * SPACING_X_PX;
        float totalHeight = Math.max(
                Math.max(eye.getTransform().getScaledHeight(), gift.getTransform().getScaledHeight()),
                heart.getTransform().getScaledHeight()
        );
        getTransform().setSize(totalWidth, totalHeight);
    }

    @Override
    public void update(float delta) {
        float offset = 0;
        if (getPlayer().hasPerk(PopularityPerk.TURNING_A_BLIND_EYE)) {
            if (!eye.isEnabled(Toggles.RENDER)) {
                eye.toggleOn(Toggles.RENDER);
                eye.getTransform().setX(offset);
                offset -= eye.getTransform().getScaledWidth() + SPACING_X_PX;
            }
        } else {
            eye.toggleOff(Toggles.RENDER);
        }

        if (getPlayer().hasPerk(PopularityPerk.GIFT_OF_THE_PEOPLE)) {
            if (!gift.isEnabled(Toggles.RENDER)) {
                gift.toggleOn(Toggles.RENDER);
                gift.getTransform().setX(offset);
                offset -= gift.getTransform().getScaledWidth() + SPACING_X_PX;
            }
        } else {
            gift.toggleOff(Toggles.RENDER);
        }

        if (getPlayer().hasPerk(PopularityPerk.MEANINGFUL_WORK)) {
            if (!heart.isEnabled(Toggles.RENDER)) {
                heart.toggleOn(Toggles.RENDER);
                heart.getTransform().setX(offset);
            }
        } else {
            heart.toggleOff(Toggles.RENDER);
        }
    }

    @Override
    public void render(SpriteBatch batch) { }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        eye.die();
        gift.die();
        heart.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { eye, gift, heart };
    }

    private TextureRegionObject loadTextureRegionObject(String atlasName, String regionName, String tooltip) {
        TextureRegionObject object = new TextureRegionObject(getAssets(), getCamera(), getWorld(), getPlayer());
        object.setTooltip(tooltip);
        object.getTransform().setParent(getTransform());
        object.getTransform().setAlign(Align.TOP_RIGHT);
        object.getTransform().setOrigin(1, 1);
        object.setRegion(atlasName, regionName);
        object.load();
        // Required to update position for the first time
        object.toggleOff(Toggles.RENDER);
        return object;
    }
}
