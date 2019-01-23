package fi.chop.model.object.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import fi.chop.model.world.Player;

public class TextButtonObject extends TextObject {

    public enum StyleType {
        CUSTOM, DISABLED, NORMAL, HOVER, PRESSED
    }
    private enum ButtonState {
        DISABLED, NORMAL, HOVER, PRESSED
    }

    private TextButtonStyle disabledStyle;
    private TextButtonStyle pressedStyle;
    private TextButtonStyle normalStyle;
    private TextButtonStyle hoverStyle;
    private StyleType usedStyle = StyleType.NORMAL;
    private ButtonState state = ButtonState.NORMAL;

    private float origX;
    private float origY;
    private float origScaleX = 1;
    private float origScaleY = 1;

    private float hoverScaleX = 1;
    private float hoverScaleY = 1;
    private float pressedScaleX = 1;
    private float pressedScaleY = 1;
    private float pressedOffsetX;
    private float pressedOffsetY;

    public TextButtonObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
        pad(50, 50);
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

    public void setPressedScale(float scaleX, float scaleY) {
        pressedScaleX = scaleX;
        pressedScaleY = scaleY;
    }

    public void setPressedOffset(float offsetX, float offsetY) {
        pressedOffsetX = offsetX;
        pressedOffsetY = offsetY;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (state == ButtonState.NORMAL) {
            origX = getX();
            origY = getY();
        }
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        if (state == ButtonState.NORMAL)
            origX = getX();

    }

    @Override
    public void setY(float y) {
        super.setY(y);
        if (state == ButtonState.NORMAL)
            origY = getY();
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        super.setScale(scaleX, scaleY);
        if (state == ButtonState.NORMAL) {
            origScaleX = getScaleX();
            origScaleY = getScaleY();
        }
    }

    @Override
    public void scale(float scaleX, float scaleY) {
        super.scale(scaleX, scaleY);
        if (state == ButtonState.NORMAL) {
            origScaleX = getScaleX();
            origScaleY = getScaleY();
        }
    }

    public void normal() {
        if (isDisabled())
            return;
        setScale(origScaleX, origScaleY);
        setPosition(origX, origY);
        useStyle(StyleType.NORMAL);
        state = ButtonState.NORMAL;
    }

    public void hover() {
        if (isDisabled())
            return;
        state = ButtonState.HOVER;
        useStyle(StyleType.HOVER);
        setScale(hoverScaleX, hoverScaleY);
        setPosition(origX, origY);
    }

    public void press() {
        if (isDisabled())
            return;
        state = ButtonState.PRESSED;
        useStyle(StyleType.HOVER);
        setScale(pressedScaleX, pressedScaleY);
        setPosition(origX + pressedOffsetX, origY + pressedOffsetY);
    }

    public void disable() {
        state = ButtonState.DISABLED;
        useStyle(StyleType.DISABLED);
        setScale(origScaleX, origScaleY);
        setPosition(origX, origY);
    }

    private boolean isDisabled() {
        return state == ButtonState.DISABLED;
    }
}
