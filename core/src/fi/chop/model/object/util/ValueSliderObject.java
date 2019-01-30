package fi.chop.model.object.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import fi.chop.input.TouchHandler;
import fi.chop.model.ValueMeter;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.gui.GUIObject;
import fi.chop.model.object.gui.TextObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;
import fi.chop.util.MathUtil;

import java.util.function.Supplier;

public class ValueSliderObject extends GUIObject {

    private String atlasName;
    private String backgroundName;
    private String knobName;
    private String fontName;
    private Supplier<String> valueTextConstructor;
    private Supplier<String> longestValueText;
    private boolean initialized;

    private TextureRegionObject background;
    private TextureRegionObject knob;
    private TextObject valueText;
    private ValueMeter meter;
    private boolean isDragging;

    public ValueSliderObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        meter = new ValueMeter();
    }

    @Override
    public void pack() {
        valueText.pack();

        float totalWidth = background.getTransform().getScaledWidth() + valueText.getMaxWidth();
        float totalHeight = Math.max(background.getTransform().getScaledHeight(),
                valueText.getTransform().getScaledHeight());
        getTransform().setSize(totalWidth, totalHeight);
        resizeToFitChildren();
    }

    @Override
    public void load() {
        if (!initialized)
            throw new IllegalStateException("init() should be called before load()");

        valueText = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
        valueText.create(fontName, valueTextConstructor, longestValueText);
        valueText.load();
        valueText.bgColor(Color.RED);
        valueText.getTransform().setParent(getTransform());
        valueText.getTransform().setAlign(Align.RIGHT_CENTER);
        valueText.getTransform().setOrigin(1, 0.5f);

        background = loadTextureRegionObject(atlasName, backgroundName);
        background.getTransform().setOrigin(0, 0.5f);
        knob = loadTextureRegionObject(atlasName, knobName);
        knob.getTransform().setOrigin(0.5f, 0.5f);
        knob.getTransform().setX(knob.getTransform().getScaledWidth() / 2);
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
        updateKnobPosition(Gdx.input.getX(), Gdx.input.getY());
    }

    @Override
    public void render(SpriteBatch batch) { }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        background.die();
        knob.die();
        valueText.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { background, knob, valueText };
    }

    public void init(String atlasName, String backgroundName, String knobName, String fontName,
                     Supplier<String> valueTextConstructor, Supplier<String> longestValueText) {
        initialized = true;
        this.atlasName = atlasName;
        this.backgroundName = backgroundName;
        this.knobName = knobName;
        this.fontName = fontName;
        this.valueTextConstructor = valueTextConstructor;
        this.longestValueText = longestValueText;
    }

    private TextureRegionObject loadTextureRegionObject(String atlasName, String regionName) {
        TextureRegionObject object = new TextureRegionObject(getAssets(), getCamera(), getWorld(), getPlayer());
        object.setRegion(atlasName, regionName);
        object.load();
        object.getTransform().setParent(getTransform());
        object.getTransform().setAlign(Align.LEFT_CENTER);
        return object;
    }

    private void updateKnobPosition(float screenX, float screenY) {
        if (isDragging) {
            Vector3 worldPos = getCamera().unproject(new Vector3(screenX, screenY, 0));

            // localX is between [knobHalfWidth, bgWidth - knobHalfWidth]
            float minX = knob.getTransform().getScaledWidth() / 2;
            float maxX = background.getTransform().getScaledWidth() - minX;
            float localX = worldPos.x - getTransform().getLeft();
            localX = Math.min(Math.max(localX, minX), maxX);
            knob.getTransform().setX(localX);

            float value = Math.min(Math.max(MathUtil.unlerp(minX, maxX, localX), 0), 1);
            meter.set(value);
        }
    }

    public float getValue() {
        return meter.getFillPercentage();
    }
}
