package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.fsm.states.guillotine.GuillotineStates;

public class GuillotineObject extends GameObject {

    public static final int IDLE_Y_OFFSET_PX = 150;
    public static final int MAX_RAISE_AMOUNT_PX = 150;
    public static final int MAX_RAISE_COUNT = 3;

    private final GuillotineStateMachine state;
    private TextureRegion blade;
    private float bladeYOffset;
    private float toRaise;
    private int raiseCount;

    public GuillotineObject(AssetManager assets, OrthographicCamera camera) {
        super(assets, camera);
        state = new GuillotineStateMachine(this);
        bladeYOffset = IDLE_Y_OFFSET_PX;
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        blade = atlas.findRegion("blade");
        setSize(blade.getRegionWidth(), blade.getRegionHeight());
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(blade, getX(), getY() + bladeYOffset);
    }

    public GuillotineStates getState() {
        return state.getCurrent();
    }

    public void raiseBlade(float amount) {
        amount = Math.min(Math.max(amount, 0), 1);
        toRaise = amount * MAX_RAISE_AMOUNT_PX;
        raiseCount++;
        state.setCurrent(GuillotineStates.BLADE_RAISE);
    }

    public void addBladeYOffset(float amount) {
        bladeYOffset = Math.max(bladeYOffset + amount, 0);
    }

    public float getBladeYOffset() {
        return bladeYOffset;
    }

    public void setToRaise(float amount) {
        toRaise = amount;
    }

    public float getToRaise() {
        return toRaise;
    }

    public void resetRaiseCount() {
        raiseCount = 0;
    }

    public boolean isReady() {
        return raiseCount >= MAX_RAISE_COUNT;
    }
}
