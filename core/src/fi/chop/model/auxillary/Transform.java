package fi.chop.model.auxillary;

public class Transform {

    private Align align = Align.CENTER;
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

    public Transform getParent() {
        return parent;
    }

    public void setAlign(Align align) {
        this.align = align;
    }

    public Transform getGlobal() {
        if (parent == null)
            return this;

        Transform parentTransform = parent.getGlobal();
        Transform result = cpy();
        result.scale(parent.getScaleX(), parent.getScaleY());
        result.rotateDeg(parent.getRotationDeg());
        result.translate(parentTransform.getX(), parentTransform.getY());
        align.apply(parentTransform, result);
        return result;
    }

    public Transform getLocal() {
        Transform result = cpy();
        align.apply(parent, result);
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

    public void resizeToFitChildren(Transform... childTransforms) {
        if (childTransforms.length == 0)
            return;
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        for (Transform child : childTransforms) {
            Transform local = child.getLocal();
            float top = local.getTop();
            float left = local.getLeft();
            float right = local.getRight();
            float bottom = local.getBottom();
            if (top > maxY) maxY = top;
            if (left < minX) minX = left;
            if (right > maxX) maxX = right;
            if (bottom < minY) minY = bottom;
        }
        setSize(maxX - minX, maxY - minY);
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

    public float getScaledWidth() {
        return width * scaleX;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public float getScaledHeight() {
        return height * scaleY;
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

    public void setTop(float top) {
        y = top - (1 - originY) * getScaledHeight();
    }

    public float getTop() {
        return y + (1 - originY) * getScaledHeight();
    }

    public void setLeft(float left) {
        x = left + originX * getScaledWidth();
    }

    public float getLeft() {
        return x - originX * getScaledWidth();
    }

    public void setRight(float right) {
        x = right - (1 - originX) * getScaledWidth();
    }

    public float getRight() {
        return x + (1 - originX) * getScaledWidth();
    }

    public void setBottom(float bottom) {
        y = bottom + originX * getScaledHeight();
    }

    public float getBottom() {
        return y - originY * getScaledHeight();
    }
}
