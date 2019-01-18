package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;

public abstract class GameObject {

    private int id = -1;
    private float x;
    private float y;
    private float width;
    private float height;
    private float scaleX = 1;
    private float scaleY = 1;
    private float originX;
    private float originY;
    private double rotDeg;
    private boolean dead;

    private final AssetManager assets;
    private final OrthographicCamera camera;

    protected GameObject(AssetManager assets, OrthographicCamera camera) {
        this.assets = assets;
        this.camera = camera;
    }

    public abstract void load();
    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

    protected void draw(SpriteBatch batch, TextureRegion region, DrawParameters params) {
        batch.draw(region,
                x - (originX + params.originX) * params.width + params.x,
                y - (originY + params.originY) * params.height + params.y,
                (originX + params.originX) * params.width,
                (originY + params.originY) * params.height,
                params.width, params.height,
                scaleX * params.scaleX, scaleY * params.scaleY,
                (float) (rotDeg + params.rotationDeg)
        );
    }

    protected void draw(SpriteBatch batch, Texture texture, DrawParameters params) {
        batch.draw(texture,
                x - (originX + params.originX) * params.width + params.x,
                y - (originY + params.originY) * params.height + params.y,
                (originX + params.originX) * params.width,
                (originY + params.originY) * params.height,
                params.width, params.height,
                scaleX * params.scaleX, scaleY * params.scaleY,
                (float) (rotDeg + params.rotationDeg),
                params.srcX, params.srcY,
                params.srcWidth, params.srcHeight,
                params.flipX, params.flipY
        );
    }

    public void translate(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void scale(float sx, float sy) {
        scaleX *= sx;
        scaleY *= sy;
    }

    public void rotateDeg(double delta) {
        setRotationDeg(rotDeg + delta);
    }

    public void rotateRad(double delta) {
        setRotationRad(getRotationRad() + delta);
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

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setOriginPx(float originXPx, float originYPx) {
        if (width == 0 || height == 0)
            throw new IllegalStateException("Cannot set pixel origin when width or height is 0");
        setOrigin(originXPx / width, originYPx / height);
    }

    public void setOrigin(float originX, float originY) {
        if (originX < 0 || originY < 0 || originX > 1 || originY > 1)
            throw new IllegalArgumentException("origin x/y values must be between 0 and 1");
        this.originX = originX;
        this.originY = originY;
    }

    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setRotationRad(double radians) {
        setRotationDeg(180 * radians / Math.PI);
    }

    public double getRotationRad() {
        return Math.PI * rotDeg / 180d;
    }

    public void setRotationDeg(double degrees) {
        while (degrees < 0)
            degrees += 360;
        rotDeg = degrees % 360;
    }

    public double getRotationDeg() {
        return rotDeg;
    }

    public void die() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isOutsideCameraView() {
        return camera != null &&
                (
                    x + (1 - originX) * width < camera.position.x - camera.viewportWidth / 2 ||
                    x - originX * width > camera.position.x + camera.viewportWidth / 2 ||
                    y + (1 - originY) * height < camera.position.y - camera.viewportHeight / 2 ||
                    y - originY * height > camera.position.y + camera.viewportHeight / 2
                );
    }

    protected AssetManager getAssets() {
        return assets;
    }
}
