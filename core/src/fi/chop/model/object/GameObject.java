package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
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

public abstract class GameObject implements EventListener, Disposable {

    private int id = -1;
    private boolean touchable;
    private boolean dead;

    private Transform transform;
    private TouchHandler touchHandler;

    private final AssetManager assets;
    private final OrthographicCamera camera;
    private final Player player;

    protected GameObject(AssetManager assets, OrthographicCamera camera, Player player) {
        this.assets = assets;
        this.camera = camera;
        this.player = player;
        transform = new Transform();
        touchHandler = new TouchHandler<>(this);
    }

    public abstract void load();
    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

    protected void draw(SpriteBatch batch, TextureRegion region, DrawParameters params) {
        if (params == null)
            params = new DrawParameters(region);
        Transform t = transform.getGlobal();
        batch.draw(region,
                t.getX() - (t.getOriginX() + params.originX) * params.width + params.x,
                t.getY() - (t.getOriginY() + params.originY) * params.height + params.y,
                (t.getOriginX() + params.originX) * params.width,
                (t.getOriginY() + params.originY) * params.height,
                Math.min(t.getWidth(), params.width), Math.min(t.getHeight(), params.height),
                t.getScaleX() * params.scaleX, t.getScaleX() * params.scaleY,
                (float) (t.getRotationDeg() + params.rotationDeg)
        );
    }

    protected void draw(SpriteBatch batch, Texture texture, DrawParameters params) {
        if (params == null)
            params = new DrawParameters(texture);
        Transform t = transform.getGlobal();
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
    }

    @Override
    public void handle(Events event, EventData data) { }

    public void onSceneAdd() { }

    public void onSceneRemove() { }

    public GameObject[] getChildren() {
        return new GameObject[0];
    }

    public Transform getTransform() {
        return transform;
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

    public void die() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
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

    protected Player getPlayer() {
        return player;
    }
}
