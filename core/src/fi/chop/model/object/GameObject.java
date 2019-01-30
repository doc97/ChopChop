package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import fi.chop.engine.DrawParameters;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.TouchHandler;
import fi.chop.model.auxillary.Transform;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

import java.util.Arrays;
import java.util.EnumSet;

public abstract class GameObject implements EventListener, Disposable {

    public enum Toggles {
        UPDATE, RENDER
    }

    private int id = -1;
    private boolean hasTooltip;
    private boolean touchable;
    private boolean dead;

    private Color tint;
    private Color oldColor;
    private String tooltip;
    private Transform transform;
    private TouchHandler touchHandler;
    private EnumSet<Toggles> enabled;

    private final AssetManager assets;
    private final OrthographicCamera camera;
    private final WorldState world;
    private final Player player;

    protected GameObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        this.assets = assets;
        this.camera = camera;
        this.world = world;
        this.player = player;
        tint = new Color(Color.WHITE);
        oldColor = new Color(Color.WHITE);
        transform = new Transform();
        touchHandler = new TouchHandler<>(this);
        enabled = EnumSet.allOf(Toggles.class);
    }

    public abstract void load();
    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

    protected void draw(SpriteBatch batch, TextureRegion region, DrawParameters params) {
        if (params == null)
            params = new DrawParameters(region);
        Transform t = transform.getGlobal();

        saveAndSetTint(batch);
        batch.draw(region,
                t.getX() - (t.getOriginX() + params.originX) * params.width + params.x,
                t.getY() - (t.getOriginY() + params.originY) * params.height + params.y,
                (t.getOriginX() + params.originX) * params.width,
                (t.getOriginY() + params.originY) * params.height,
                Math.min(t.getWidth(), params.width), Math.min(t.getHeight(), params.height),
                t.getScaleX() * params.scaleX, t.getScaleX() * params.scaleY,
                (float) (t.getRotationDeg() + params.rotationDeg)
        );
        restoreTint(batch);
    }

    protected void draw(SpriteBatch batch, Texture texture, DrawParameters params) {
        if (params == null)
            params = new DrawParameters(texture);
        Transform t = transform.getGlobal();

        saveAndSetTint(batch);
        batch.draw(texture,
                t.getX() - (t.getOriginX() + params.originX) * params.width + params.x,
                t.getY() - (t.getOriginY() + params.originY) * params.height + params.y,
                (t.getOriginX() + params.originX) * params.width,
                (t.getOriginY() + params.originY) * params.height,
                Math.min(t.getWidth(), params.width), Math.min(t.getHeight(), params.height),
                t.getScaleX() * params.scaleX, t.getScaleY() * params.scaleY,
                (float) (t.getRotationDeg() + params.rotationDeg),
                params.srcX, params.srcY,
                params.srcWidth, params.srcHeight,
                params.flipX, params.flipY
        );
        restoreTint(batch);
    }

    private void saveAndSetTint(SpriteBatch batch) {
        oldColor.set(batch.getColor());
        batch.setColor(oldColor.cpy().mul(tint));
    }

    private void restoreTint(SpriteBatch batch) {
        batch.setColor(oldColor);
    }

    @Override
    public void handle(Events event, EventData data) { }

    public void onSceneAdd() { }

    public void onSceneRemove() { }

    public void resizeToFitChildren() {
        getTransform().resizeToFitChildren(getChildTransforms());
    }

    public GameObject[] getChildren() {
        return new GameObject[0];
    }

    public Transform[] getChildTransforms() {
        GameObject[] children = getChildren();
        Transform[] childTransforms = new Transform[children.length];
        for (int i = 0; i < children.length; i++)
            childTransforms[i] = children[i].getTransform();
        return childTransforms;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTint(Color tint) {
        this.tint = tint == null ? new Color(Color.WHITE) : tint;
    }

    public Color getTint() {
        return tint;
    }

    public void invalidateID() {
        id = -1;
    }

    public void growID() {
        if (id >= 0)
            id++;
    }

    public void setID(int id) {
        if (id < 0)
            throw new IllegalArgumentException("id must be positive");
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public <T extends GameObject> void setTouchHandler(TouchHandler<T> touchHandler) {
        this.touchHandler = touchHandler == null ? new TouchHandler<>(this) : touchHandler;
    }

    public TouchHandler getTouchHandler() {
        return touchHandler;
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    public boolean isTouchable() {
        return touchable;
    }

    public boolean hasTooltip() {
        return hasTooltip;
    }

    public void setTooltip(String tooltip) {
        hasTooltip = tooltip != null && !tooltip.isEmpty();
        if (hasTooltip)
            this.tooltip = tooltip;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void die() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void toggleOn(Toggles... toggles) {
        enabled.addAll(Arrays.asList(toggles));
    }

    public void toggleOff(Toggles... toggles) {
        enabled.removeAll(Arrays.asList(toggles));
    }

    public boolean isEnabled(Toggles toggle) {
        return enabled.contains(toggle);
    }

    public boolean isOutsideCameraView() {
        if (camera == null)
            return false;
        float camX = camera.position.x;
        float camY = camera.position.y;
        float camW = camera.viewportWidth;
        float camH = camera.viewportHeight;
        float camTop = camY + camH / 2;
        float camLeft = camX - camW / 2;
        float camRight = camX + camW / 2;
        float camBottom = camY - camH / 2;
        Transform t = transform.getGlobal();
        return t.getTop() < camBottom ||
                t.getLeft() > camRight ||
                t.getRight() < camLeft ||
                t.getBottom() > camTop;
    }

    public boolean isXYInside(float x0, float y0) {
        if (!isInsideRadius(x0, y0))
            return false;
        Transform t = transform.getGlobal();
        return y0 <= t.getTop() && y0 >= t.getBottom() && x0 <= t.getRight() && x0 >= t.getLeft();
    }

    private boolean isInsideRadius(float x0, float y0) {
        Transform t = transform.getGlobal();
        float radiusSquared = Math.max(t.getWidth() * t.getWidth(),
                t.getHeight() * t.getHeight());
        float distX = x0 - t.getX();
        float distY = y0 - t.getY();
        float distanceSquared = distX * distX + distY * distY;
        return  distanceSquared < radiusSquared;
    }

    protected AssetManager getAssets() {
        return assets;
    }

    protected OrthographicCamera getCamera() {
        return camera;
    }

    protected WorldState getWorld() {
        return world;
    }

    protected Player getPlayer() {
        return player;
    }
}
