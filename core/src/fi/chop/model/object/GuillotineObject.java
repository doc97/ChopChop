package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.engine.DrawParameters;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.fsm.states.guillotine.GuillotineStates;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public class GuillotineObject extends GameObject {

    public static final int IDLE_Y_OFFSET_PX = 170;
    public static final int BLADE_Y_OFFSET_PX = 170;
    public static final int MAX_RAISE_AMOUNT_PX = 110;
    public static final int MAX_RAISE_COUNT = 3;

    private final GuillotineStateMachine state;
    private Texture body;
    private Texture blade;
    private DrawParameters bodyParams;
    private DrawParameters bladeParams;
    private float bladeYOffset;
    private float toRaise;
    private int raiseCount;

    public GuillotineObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        state = new GuillotineStateMachine(this);
    }

    @Override
    public void load() {
        bladeYOffset = IDLE_Y_OFFSET_PX;
        body = getAssets().get("textures/execution_screen/Guillotine_Body.png", Texture.class);
        blade = getAssets().get("textures/execution_screen/Guillotine_Blade.png", Texture.class);
        bodyParams = new DrawParameters(body).scale(0.5f, 0.5f);
        bladeParams = new DrawParameters(blade)
                .pos(-15, BLADE_Y_OFFSET_PX + bladeYOffset)
                .scale(0.5f, 0.5f);
        getTransform().setSize(bodyParams.width, bodyParams.height);
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        draw(batch, blade, bladeParams);
        draw(batch, body, bodyParams);
    }

    @Override
    public void dispose() { }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.EVT_GUILLOTINE_RAISE)
            raiseBlade((float) data.get());
    }

    private void raiseBlade(float amount) {
        amount = Math.min(Math.max(amount, 0), 1);
        toRaise = amount * MAX_RAISE_AMOUNT_PX;
        raiseCount++;
        state.setCurrent(GuillotineStates.BLADE_RAISE);
    }

    public void addBladeYOffset(float amount) {
        bladeYOffset = Math.max(bladeYOffset + amount, 0);
        bladeParams.y = BLADE_Y_OFFSET_PX + bladeYOffset;
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
