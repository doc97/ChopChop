package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {

    private float x;
    private float y;
    private float width;
    private float height;
    private double rotDeg;

    private final AssetManager assets;

    protected GameObject(AssetManager assets) {
        this.assets = assets;
    }

    public abstract void load();
    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

    public void translate(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void rotateDeg(double delta) {
        setRotationDeg(rotDeg + delta);
    }

    public void rotateRad(double delta) {
        setRotationRad(getRotationRad() + delta);
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

    protected AssetManager getAssets() {
        return assets;
    }
}
