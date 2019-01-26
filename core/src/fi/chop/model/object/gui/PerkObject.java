package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.model.object.GameObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.PopularityPerk;

public class PerkObject extends GameObject {

    private static final int SPACING_X_PX = 10;

    private TextureRegion eye;
    private TextureRegion gift;
    private TextureRegion heart;
    private DrawParameters eyeParams;
    private DrawParameters giftParams;
    private DrawParameters heartParams;

    public PerkObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        eye = atlas.findRegion("ic-perk-turning-a-blind-eye");
        gift = atlas.findRegion("ic-perk-gift-of-the-people");
        heart = atlas.findRegion("ic-perk-meaningful-work");

        eyeParams = new DrawParameters(eye);
        giftParams = new DrawParameters(gift);
        heartParams = new DrawParameters(heart);

        getTransform().setSize(
                eyeParams.width + giftParams.width + heartParams.width+ 2 * SPACING_X_PX,
                Math.max(Math.max(eyeParams.height, giftParams.height), heartParams.height));
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) {
        float offset = 0;//getTransform().getX();
        if (getPlayer().hasPerk(PopularityPerk.TURNING_A_BLIND_EYE)) {
            eyeParams.x = offset;
            draw(batch, eye, eyeParams);
            offset += eyeParams.width + SPACING_X_PX;
        }
        if (getPlayer().hasPerk(PopularityPerk.GIFT_OF_THE_PEOPLE)) {
            giftParams.x = offset;
            draw(batch, gift, giftParams);
            offset += giftParams.width + SPACING_X_PX;
        }
        if (getPlayer().hasPerk(PopularityPerk.MEANINGFUL_WORK)) {
            heartParams.x = offset;
            draw(batch, heart, heartParams);
        }
    }

    @Override
    public void dispose() { }
}
