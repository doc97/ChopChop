package fi.chop.model.object.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import fi.chop.model.world.Player;

public class TextButtonObject extends TextObject {

    public enum StyleType {
        CUSTOM, DISABLED, NORMAL, HOVER
    }

    private TextButtonStyle disabledStyle;
    private TextButtonStyle normalStyle;
    private TextButtonStyle hoverStyle;
    private StyleType usedStyle;
    private boolean disabled;

    public TextButtonObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
        pad(50, 50);
        setTouchable(true);
        disabledStyle = new TextButtonStyle().bgColor(Color.DARK_GRAY).tint(Color.GRAY);
        normalStyle = new TextButtonStyle().bgColor(Color.GOLDENROD).tint(Color.BROWN);
        hoverStyle = new TextButtonStyle().bgColor(Color.FIREBRICK).tint(Color.YELLOW);
        usedStyle = StyleType.NORMAL;
        syncStyle();
    }

    private void syncStyle() {
        style(getUsedStyle());
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
        }
        if (type == usedStyle)
            syncStyle();
    }

    public void useStyle(StyleType type) {
        if (disabled || usedStyle == type)
            return;
        usedStyle = type;
        syncStyle();
    }

    public void enable() {
        disabled = false;
    }

    public void disable() {
        disabled = true;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
