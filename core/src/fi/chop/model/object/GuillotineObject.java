package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GuillotineObject extends GameObject {

    private static final int MAX_RAISE_PX = 200;

    private TextureRegion blade;
    private float bladeYOffset;

    public GuillotineObject(AssetManager assets) {
        super(assets);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        blade = atlas.findRegion("blade");
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(blade, getX(), getY() + bladeYOffset);
    }

    public void raiseBlade(float amount) {
        amount = Math.min(Math.max(amount, 0), 1);
        bladeYOffset += amount * MAX_RAISE_PX;
    }
}
