package fi.chop.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {

    private float x;
    private float y;
    private double rotDeg;

    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

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
}
