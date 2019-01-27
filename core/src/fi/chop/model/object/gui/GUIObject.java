package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import fi.chop.model.object.GameObject;
import fi.chop.model.world.Player;

public abstract class GUIObject extends GameObject {

    private float padTop;
    private float padLeft;
    private float padRight;
    private float padBottom;
    private boolean invalid;

    protected GUIObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
    }

    public abstract void pack();

    @Override
    public void update(float delta) {
        if (invalid)
            build();
    }

    private void build() {
        invalid = false;
        pack();
    }

    public void invalidate() {
        invalid = true;
    }

    public GUIObject pad(float padTop, float padLeft, float padRight, float padBottom) {
        padTop(padTop);
        padLeft(padLeft);
        padRight(padRight);
        padBottom(padBottom);
        return this;
    }

    public GUIObject padTop(float padTop) {
        this.padTop = Math.max(padTop, 0);
        invalidate();
        return this;
    }

    public float getPadTop() {
        return padTop;
    }

    public GUIObject padLeft(float padLeft) {
        this.padLeft = Math.max(padLeft, 0);
        invalidate();
        return this;
    }

    public float getPadLeft() {
        return padLeft;
    }

    public GUIObject padRight(float padRight) {
        this.padRight = Math.max(padRight, 0);
        invalidate();
        return this;
    }

    public float getPadRight() {
        return padRight;
    }

    public GUIObject padBottom(float padBottom) {
        this.padBottom = Math.max(padBottom, 0);
        invalidate();
        return this;
    }

    public float getPadBottom() {
        return padBottom;
    }
}
