package fi.chop.input;

import fi.chop.model.object.GameObject;

public class TouchHandler<T extends GameObject> {

    private T object;
    private boolean over;

    public TouchHandler(T object) {
        this.object = object;
    }

    public void registerTouchUp(float worldX, float worldY, int pointer, int button) {
        if (object.isXYInside(worldX, worldY))
            touchUp(object, worldX, worldY, pointer, button);
    }

    public void registerTouchDown(float worldX, float worldY, int pointer, int button) {
        if (object.isXYInside(worldX, worldY))
            touchDown(object, worldX, worldY, pointer, button);
    }

    public void registerTouchDragged(float worldX, float worldY, int pointer) {
        if (object.isXYInside(worldX, worldY))
            touchDragged(object, worldX, worldY, pointer);
    }

    public void registerMouseMoved(float worldX, float worldY) {
        if (object.isXYInside(worldX, worldY)) {
            if (!over)
                enter(object, worldX, worldY);
            over = true;
        } else {
            if (over)
                exit(object, worldX, worldY);
            over = false;
        }
    }

    // Implement these yourself
    public void touchUp(T object, float worldX, float worldY, int pointer, int button) { }
    public void touchDown(T object, float worldX, float worldY, int pointer, int button) { }
    public void touchDragged(T object, float worldX, float worldY, int pointer) { }
    public void enter(T object, float worldX, float worldY) { }
    public void exit(T object, float worldX, float worldY) { }
}
