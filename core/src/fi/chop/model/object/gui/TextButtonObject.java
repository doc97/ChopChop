package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import fi.chop.model.auxillary.Transform;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public class TextButtonObject extends TextObject {

    public enum StyleType {
        CUSTOM, DISABLED, NORMAL, HOVER, PRESSED
    }
    private enum ButtonState {
        DISABLED, NORMAL, HOVER, PRESSED
    }

    private Transform btnTrans;
    private TextButtonStyle disabledStyle;
    private TextButtonStyle pressedStyle;
    private TextButtonStyle normalStyle;
    private TextButtonStyle hoverStyle;
    private StyleType usedStyle = StyleType.NORMAL;
    private ButtonState state = ButtonState.NORMAL;

    private float hoverScaleX = 1;
    private float hoverScaleY = 1;
    private float hoverOffsetX;
    private float hoverOffsetY;
    private float pressedScaleX = 1;
    private float pressedScaleY = 1;
    private float pressedOffsetX;
    private float pressedOffsetY;

    public TextButtonObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        getTransform().setOrigin(0.5f, 0.5f);
        pad(25, 25, 25, 25);
        setTouchable(true);
        disabledStyle = new TextButtonStyle().bgColor(Color.DARK_GRAY).tint(Color.GRAY);
        pressedStyle = new TextButtonStyle().bgColor(Color.FIREBRICK).tint(Color.YELLOW);
        normalStyle = new TextButtonStyle().bgColor(Color.GOLDENROD).tint(Color.BROWN);
        hoverStyle = new TextButtonStyle().bgColor(Color.FIREBRICK).tint(Color.YELLOW);
        syncStyle();
    }

    private void syncStyle() {
        style(getUsedStyle());
    }

    private void useStyle(StyleType type) {
        if (usedStyle != type) {
            usedStyle = type;
            syncStyle();
        }
    }

    private TextButtonStyle getUsedStyle() {
        switch (usedStyle) {
            case CUSTOM:
                return null;
            case DISABLED:
                return disabledStyle;
            case NORMAL:
                return normalStyle;
            case HOVER:
                return hoverStyle;
            case PRESSED:
                return pressedStyle;
            default:
                throw new RuntimeException("Invalid TextObject style!");
        }
    }

    public void setStyle(StyleType type, TextButtonStyle style) {
        switch (type) {
            case CUSTOM:
                style(style);
                break;
            case DISABLED:
                disabledStyle.set(style);
                break;
            case NORMAL:
                normalStyle.set(style);
                break;
            case HOVER:
                hoverStyle.set(style);
                break;
            case PRESSED:
                pressedStyle.set(style);
                break;
        }
        if (type == usedStyle)
            syncStyle();
    }

    public void setHoverScale(float scaleX, float scaleY) {
        hoverScaleX = scaleX;
        hoverScaleY = scaleY;
    }

    public void setHoverOffset(float offsetX, float offsetY) {
        hoverOffsetX = offsetX;
        hoverOffsetY = offsetY;
    }

    public void setPressedScale(float scaleX, float scaleY) {
        pressedScaleX = scaleX;
        pressedScaleY = scaleY;
    }

    public void setPressedOffset(float offsetX, float offsetY) {
        pressedOffsetX = offsetX;
        pressedOffsetY = offsetY;
    }

    public void normal() {
        if (isDisabled())
            return;
        Transform trans = btnTrans;
        if (btnTrans == null)
            trans = getTransform();
        getTransform().setScale(trans.getScaleX(), trans.getScaleY());
        getTransform().setPosition(trans.getX(), trans.getY());
        useStyle(StyleType.NORMAL);
        state = ButtonState.NORMAL;
        btnTrans = null;
    }

    public void hover() {
        activate(ButtonState.HOVER, StyleType.HOVER, hoverScaleX, hoverScaleY, hoverOffsetX, hoverOffsetY);
    }

    public void press() {
        activate(ButtonState.PRESSED, StyleType.PRESSED, pressedScaleX, pressedScaleY, pressedOffsetX, pressedOffsetY);
    }

    public void disable() {
        state = ButtonState.DISABLED;
        useStyle(StyleType.DISABLED);
        if (btnTrans != null) {
            getTransform().setScale(btnTrans.getScaleX(), btnTrans.getScaleY());
            getTransform().setPosition(btnTrans.getX(), btnTrans.getY());
            btnTrans = null;
        }
    }

    private void activate(ButtonState state, StyleType type, float scaleX, float scaleY, float offsetX, float offsetY) {
        if (isDisabled())
            return;
        if (btnTrans == null)
            btnTrans = getTransform().cpy();
        this.state = state;
        useStyle(type);
        getTransform().setScale(scaleX, scaleY);
        getTransform().setPosition(btnTrans.getX() + offsetX, btnTrans.getY() + offsetY);
    }

    private boolean isDisabled() {
        return state == ButtonState.DISABLED;
    }
}
