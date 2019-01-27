package fi.chop.model.auxillary;

import com.badlogic.gdx.math.Vector2;

public class Align {
    public static final Align CENTER = new Align(Vertical.CENTER, Horizontal.CENTER);
    public static final Align TOP_LEFT = new Align(Vertical.TOP, Horizontal.LEFT);
    public static final Align TOP_RIGHT = new Align(Vertical.TOP, Horizontal.RIGHT);
    public static final Align TOP_CENTER = new Align(Vertical.TOP, Horizontal.CENTER);
    public static final Align BOTTOM_LEFT = new Align(Vertical.BOTTOM, Horizontal.LEFT);
    public static final Align BOTTOM_RIGHT = new Align(Vertical.BOTTOM, Horizontal.RIGHT);
    public static final Align BOTTOM_CENTER = new Align(Vertical.BOTTOM, Horizontal.CENTER);
    public static final Align RIGHT_CENTER = new Align(Vertical.CENTER, Horizontal.RIGHT);
    public static final Align LEFT_CENTER = new Align(Vertical.CENTER, Horizontal.LEFT);

    public enum Vertical {
        TOP, BOTTOM, CENTER
    }
    public enum Horizontal {
        LEFT, RIGHT, CENTER
    }

    private Vertical vAlign;
    private Horizontal hAlign;

    private Align(Vertical vAlign, Horizontal hAlign) {
        this.vAlign = vAlign;
        this.hAlign = hAlign;
    }

    public void apply(Transform parent, Transform child) {
        Vector2 parentDelta = getParentDelta(parent);
        child.translate(parentDelta.x, parentDelta.y);
    }

    private Vector2 getParentDelta(Transform parent) {
        Vector2 delta = new Vector2();
        if (parent == null)
            return delta;

        if (vAlign == Vertical.TOP)
            delta.y = parent.getTop() - parent.getY();
        else if (vAlign == Vertical.BOTTOM)
            delta.y = parent.getBottom() - parent.getY();

        if (hAlign == Horizontal.LEFT)
            delta.x = parent.getLeft() - parent.getX();
        else if (hAlign == Horizontal.RIGHT)
            delta.x = parent.getRight() - parent.getX();

        return delta;
    }

    public Vertical getVAlign() {
        return vAlign;
    }

    public Horizontal getHAlign() {
        return hAlign;
    }
}
