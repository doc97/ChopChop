package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.fsm.states.guillotine.GuillotineStates;

public class GuillotineObject extends GameObject {

    public static final int MAX_RAISE_PX = 200;

    private final GuillotineStateMachine state;
    private TextureRegion blade;
    private float bladeYOffset;
    private float toRaise;

    public GuillotineObject(AssetManager assets) {
        super(assets);
        state = new GuillotineStateMachine(this);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        blade = atlas.findRegion("blade");
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(blade, getX(), getY() + bladeYOffset);
    }

    public void raiseBlade(float amount) {
        amount = Math.min(Math.max(amount, 0), 1);
        toRaise += amount * MAX_RAISE_PX;
        state.setCurrent(GuillotineStates.BLADE_RAISE);
    }

    public void addBladeYOffset(float amount) {
        bladeYOffset += amount;
    }

    public void setToRaise(float amount) {
        toRaise = amount;
    }

    public float getToRaise() {
        return toRaise;
    }
}
