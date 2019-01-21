package fi.chop.input;

import fi.chop.model.object.GameObject;

public class TouchHandler {

    private boolean over;

    public void registerTouchUp(GameObject object, float worldX, float worldY, int pointer, int button) {
        if (object.isXYInside(worldX, worldY))
            touchUp(object, worldX, worldY, pointer, button);
    }

    public void registerTouchDown(GameObject object, float worldX, float worldY, int pointer, int button) {
        if (object.isXYInside(worldX, worldY))
            touchDown(object, worldX, worldY, pointer, button);
    }

    public void registerTouchDragged(GameObject object, float worldX, float worldY, int pointer) {
        if (object.isXYInside(worldX, worldY))
            touchDragged(object, worldX, worldY, pointer);
    }

    public void registerMouseMoved(GameObject object, float worldX, float worldY) {
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
    public void touchUp(GameObject object, float worldX, float worldY, int pointer, int button) { }
    public void touchDown(GameObject object, float worldX, float worldY, int pointer, int button) { }
    public void touchDragged(GameObject object, float worldX, float worldY, int pointer) { }
    public void enter(GameObject object, float worldX, float worldY) { }
    public void exit(GameObject object, float worldX, float worldY) { }
}
