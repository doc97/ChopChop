package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.fsm.states.guillotine.GuillotineStates;
import fi.chop.model.world.Player;

public class GuillotineObject extends GameObject {

    public static final int IDLE_Y_OFFSET_PX = 150;
    public static final int MAX_RAISE_AMOUNT_PX = 150;
    public static final int MAX_RAISE_COUNT = 3;

    private final GuillotineStateMachine state;
    private TextureRegion blade;
    private DrawParameters bladeParams;
    private float toRaise;
    private int raiseCount;

    public GuillotineObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
        state = new GuillotineStateMachine(this);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        blade = atlas.findRegion("blade");
        bladeParams = new DrawParameters(blade, 0, IDLE_Y_OFFSET_PX);
        setSize(blade.getRegionWidth(), blade.getRegionHeight());
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        draw(batch, blade, bladeParams);
    }

    @Override
    public void dispose() { }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.EVT_GUILLOTINE_RAISE)
            raiseBlade((float) data.get());
    }

    public GuillotineStates getState() {
        return state.getCurrent();
    }

    private void raiseBlade(float amount) {
        amount = Math.min(Math.max(amount, 0), 1);
        toRaise = amount * MAX_RAISE_AMOUNT_PX;
        raiseCount++;
        state.setCurrent(GuillotineStates.BLADE_RAISE);
    }

    public void addBladeYOffset(float amount) {
        bladeParams.y = Math.max(bladeParams.y + amount, 0);
    }

    public float getBladeYOffset() {
        return bladeParams.y;
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
