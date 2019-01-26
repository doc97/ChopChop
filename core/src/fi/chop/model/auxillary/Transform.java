package fi.chop.model.auxillary;

public class Transform {

    private Transform parent;
    private float x;
    private float y;
    private float width;
    private float height;
    private float scaleX = 1;
    private float scaleY = 1;
    private float originX;
    private float originY;
    private double rotDeg;

    public void setParent(Transform parent) {
        this.parent = parent;
    }

    public Transform get() {
        if (parent == null)
            return this;

        Transform parentTransform = parent.get();
        Transform result = cpy();
        result.translate(parentTransform.getX(), parentTransform.getY());
        return result;
    }

    public Transform parent() {
        return parent;
    }

    public Transform cpy() {
        Transform copy = new Transform();
        copy.parent = parent;
        copy.setPosition(x, y);
        copy.setSize(width, height);
        copy.setScale(scaleX, scaleY);
        copy.setOrigin(originX, originY);
        copy.setRotationDeg(rotDeg);
        return copy;
    }

    private Transform combine(Transform other) {
        translate(other.x, other.y);
        resize(other.width, other.height);
        scale(other.scaleX, other.scaleY);
        setOrigin(Math.min(Math.max(originX + other.originX, 0), 1), Math.min(Math.max(originY + other.originY, 0), 1));
        rotateDeg(other.rotDeg);
        return this;
    }

    public void translate(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void resize(float dw, float dh) {
        width += dw;
        height += dh;
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

    public float getTop() {
        return y + (1 - originY) * height;
    }

    public float getLeft() {
        return x - originX * width;
    }

    public float getRight() {
        return x + (1 - originX) * width;
    }

    public float getBottom() {
        return y - originY * height;
    }
}
