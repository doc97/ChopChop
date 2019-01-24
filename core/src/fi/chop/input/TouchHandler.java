package fi.chop.input;

import fi.chop.model.object.GameObject;

public class TouchHandler<T extends GameObject> {

    private T object;
    private boolean over;

    public TouchHandler(T object) {
        this.object = object;
    }

    public boolean registerTouchUp(float worldX, float worldY, int pointer, int button) {
        if (object.isXYInside(worldX, worldY))
            return touchUp(object, worldX, worldY, pointer, button);
        return false;
    }

    public boolean registerTouchDown(float worldX, float worldY, int pointer, int button) {
        if (object.isXYInside(worldX, worldY))
            return touchDown(object, worldX, worldY, pointer, button);
        return false;
    }

    public boolean registerTouchDragged(float worldX, float worldY, int pointer) {
        if (object.isXYInside(worldX, worldY))
            return touchDragged(object, worldX, worldY, pointer);
        return false;
    }

    public boolean registerMouseMoved(float worldX, float worldY) {
        if (object.isXYInside(worldX, worldY)) {
            if (!over) {
                over = true;
                return enter(object, worldX, worldY);
            }
        } else {
            if (over) {
                over = false;
                return exit(object, worldX, worldY);
            }
        }
        return false;
    }

    // Implement these yourself
    public boolean touchUp(T object, float worldX, float worldY, int pointer, int button) {
        return false;
    }

    public boolean touchDown(T object, float worldX, float worldY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(T object, float worldX, float worldY, int pointer) {
        return false;
    }

    public boolean enter(T object, float worldX, float worldY) {
        return false;
    }

    public boolean exit(T object, float worldX, float worldY) {
        return false;
    }
}
