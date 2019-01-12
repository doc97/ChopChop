package fi.chop.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawParameters {
    public float x;
    public float y;
    public float width;
    public float height;
    public float originX;
    public float originY;
    public float scaleX;
    public float scaleY;
    public double rotationDeg;
    public int srcX;
    public int srcY;
    public int srcWidth;
    public int srcHeight;
    public boolean flipX = false;
    public boolean flipY = false;

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
}
