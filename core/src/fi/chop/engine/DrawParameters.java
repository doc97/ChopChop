package fi.chop.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawParameters {

    public float x;
    public float y;
    public float width;
    public float height;
    public float originX;
    public float originY;
    public float scaleX = 1;
    public float scaleY = 1;
    public double rotationDeg;
    public int srcX;
    public int srcY;
    public int srcWidth;
    public int srcHeight;
    public boolean flipX = false;
    public boolean flipY = false;

    public DrawParameters() { }

    public DrawParameters(TextureRegion region) {
        this(0, 0, region.getRegionWidth(), region.getRegionHeight(), 0, 0,
                1, 1, 0);
    }

    public DrawParameters(TextureRegion region, float x, float y) {
        this(x, y, region.getRegionWidth(), region.getRegionHeight(), 0, 0,
                1, 1, 0);
    }

    public DrawParameters(float x, float y, float width, float height) {
        this(x, y, width, height, 0, 0, 1, 1, 0);
    }

    public DrawParameters(float x, float y, float width, float height,
                          float originX, float originY) {
        this(x, y, width, height, originX, originY, 1, 1, 0);
    }

    public DrawParameters(float x, float y, float width, float height,
                          float originX, float originY, float scaleX, float scaleY) {
        this(x, y, width, height, originX, originY, scaleX, scaleY, 0);
    }

    public DrawParameters(float x, float y, float width, float height,
                          float originX, float originY, float scaleX, float scaleY, double rotationDeg) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.originX = originX;
        this.originY = originY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotationDeg = rotationDeg;
    }

    public DrawParameters(Texture texture) {
        this(0, 0, texture.getWidth(), texture.getHeight(), 0, 0,
                1, 1, 0,
                0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public DrawParameters(Texture texture, float x, float y) {
        this(x, y, texture.getWidth(), texture.getHeight(), 0, 0,
                1, 1, 0,
                0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public DrawParameters(Texture texture, float x, float y, float width, float height) {
        this(x, y, width, height, 0, 0, 1, 1, 0,
                0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public DrawParameters(Texture texture, float x, float y, float width, float height,
                          float originX, float originY) {
        this(x, y, width, height, originX, originY, 1, 1, 0,
                0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public DrawParameters(Texture texture, float x, float y, float width, float height,
                          float originX, float originY, float scaleX, float scaleY) {
        this(x, y, width, height, originX, originY, scaleX, scaleY, 0,
                0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public DrawParameters(Texture texture, float x, float y, float width, float height,
                          float originX, float originY, float scaleX, float scaleY, double rotationDeg) {
        this(x, y, width, height, originX, originY, scaleX, scaleY, rotationDeg,
                0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public DrawParameters(float x, float y, float width, float height,
                          float originX, float originY, float scaleX, float scaleY, double rotationDeg,
                          int srcX, int srcY, int srcWidth, int srcHeight,
                          boolean flipX, boolean flipY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.originX = originX;
        this.originY = originY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotationDeg = rotationDeg;
        this.srcX = srcX;
        this.srcY = srcY;
        this.srcWidth = srcWidth;
        this.srcHeight = srcHeight;
        this.flipX = flipX;
        this.flipY = flipY;
    }

    public DrawParameters(DrawParameters params) {
        if (params != null) {
            this.x = params.x;
            this.y = params.y;
            this.width = params.width;
            this.height = params.height;
            this.originX = params.originX;
            this.originY = params.originY;
            this.scaleX = params.scaleX;
            this.scaleY = params.scaleY;
            this.rotationDeg = params.rotationDeg;
            this.srcX = params.srcX;
            this.srcY = params.srcY;
            this.srcWidth = params.srcWidth;
            this.srcHeight = params.srcHeight;
            this.flipX = params.flipX;
            this.flipY = params.flipY;
        }
    }

    public DrawParameters combine(DrawParameters params) {
        this.x += params.x;
        this.y += params.y;
        this.width += params.width;
        this.height += params.height;
        this.originX += params.originX;
        this.originY += params.originY;
        this.scaleX *= params.scaleX;
        this.scaleY *= params.scaleY;
        this.rotationDeg += params.rotationDeg;
        this.srcX += params.srcX;
        this.srcY += params.srcY;
        this.srcWidth += params.srcWidth;
        this.srcHeight += params.srcHeight;
        this.flipX = params.flipX;
        this.flipY = params.flipY;
        return this;
    }

    public DrawParameters cpy() {
        return new DrawParameters(this);
    }

    public DrawParameters pos(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public DrawParameters size(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public DrawParameters origin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
        return this;
    }

    public DrawParameters scale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        return this;
    }

    public DrawParameters rot(double rotationDeg) {
        this.rotationDeg = rotationDeg;
        return this;
    }

    public DrawParameters srcPos(int srcX, int srcY) {
        this.srcX = srcX;
        this.srcY = srcY;
        return this;
    }

    public DrawParameters srcSize(int srcWidth, int srcHeight) {
        this.srcWidth = srcWidth;
        this.srcHeight = srcHeight;
        return this;
    }

    public void flip(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
    }
}
