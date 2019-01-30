package fi.chop.model.object.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import fi.chop.input.TouchHandler;
import fi.chop.model.ValueMeter;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.gui.GUIObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public class ValueSliderObject extends GUIObject {

    private boolean isDragging;
    private TextureRegionObject background;
    private TextureRegionObject knob;
    private ValueMeter meter;

    public ValueSliderObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        meter = new ValueMeter();
    }

    @Override
    public void pack() { }

    @Override
    public void load() {
        background = new TextureRegionObject(getAssets(), getCamera(), getWorld(), getPlayer());
        background.setRegion("textures/packed/Chop.atlas", "meter-background");
        background.load();
        background.getTransform().setParent(getTransform());
        background.getTransform().setOrigin(0.5f, 0.5f);

        knob = new TextureRegionObject(getAssets(), getCamera(), getWorld(), getPlayer());
        knob.setRegion("textures/packed/Chop.atlas", "head-dead");
        knob.load();
        knob.getTransform().setParent(getTransform());
        knob.getTransform().setOrigin(0.5f, 0.5f);
        knob.setTouchable(true);
        knob.setTouchHandler(new TouchHandler<TextureRegionObject>(knob) {
            @Override
            public boolean enter(TextureRegionObject object, float worldX, float worldY) {
                object.getTransform().setScale(1.1f, 1.1f);
                return true;
            }

            @Override
            public boolean exit(TextureRegionObject object, float worldX, float worldY) {
                object.getTransform().setScale(1, 1);
                return true;
            }

            @Override
            public boolean touchDown(TextureRegionObject object, float worldX, float worldY, int pointer, int button) {
                isDragging = true;
                return true;
            }

            @Override
            public boolean registerTouchUp(float worldX, float worldY, int pointer, int button) {
                isDragging = false;
                return false;
            }
        });
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        updateKnobPosition(knob, Gdx.input.getX(), Gdx.input.getY());
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void die() {
        super.die();
        background.die();
        knob.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { background, knob };
    }

    private void updateKnobPosition(TextureRegionObject object, float screenX, float screenY) {
        if (isDragging) {
            Vector3 worldPos = getCamera().unproject(new Vector3(screenX, screenY, 0));
            float localX = worldPos.x - object.getTransform().getParent().getX();
            localX = Math.min(Math.max(localX, -getTransform().getWidth() / 2), getTransform().getWidth() / 2);
            object.getTransform().setX(localX);

            float value = (localX + getTransform().getWidth() / 2) / getTransform().getWidth();
            meter.set(value);
            String valString = String.format("%.1f", value * 100);
            Gdx.app.log("ValueSliderObject", "Slider percent: " + valString + " %");
        }
    }

    public float getValue() {
        return meter.getFillPercentage();
    }
}
