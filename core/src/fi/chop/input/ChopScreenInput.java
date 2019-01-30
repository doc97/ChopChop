package fi.chop.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import fi.chop.engine.InputMap;
import fi.chop.model.object.GameObject;
import fi.chop.screens.ChopScreen;

import java.util.Collections;
import java.util.List;

public class ChopScreenInput extends InputAdapter {

    private final InputMap inputMap;
    private final ChopScreen screen;

    public ChopScreenInput(ChopScreen screen, InputMap inputMap) {
        this.screen = screen;
        this.inputMap = inputMap;
    }

    private boolean triggerForTouchables(int screenX, int screenY, int pointer, int button, Trigger trigger) {
        Vector3 worldPos3D = screen.getCamera().unproject(new Vector3(screenX, screenY, 0));

        List<GameObject> objects = screen.getScene().findAll(GameObject::isTouchable);
        Collections.reverse(objects); // findAll returns them in the order: bottom to up
        for (GameObject obj : objects) {
            if (trigger.call(obj, worldPos3D.x, worldPos3D.y, pointer, button))
                return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return triggerForTouchables(screenX, screenY, pointer, button,
                (obj, wx, wy, p, btn) -> obj.getTouchHandler().registerTouchUp(wx, wy, p, btn));
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return triggerForTouchables(screenX, screenY, pointer, button,
                (obj, wx, wy, p, btn) -> obj.getTouchHandler().registerTouchDown(wx, wy, p, btn));
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return triggerForTouchables(screenX, screenY, pointer, -1,
                (obj, wx, wy, p, btn) -> obj.getTouchHandler().registerTouchDragged(wx, wy, p));
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return triggerForTouchables(screenX, screenY, -1, -1,
                (obj, wx, wy, p, btn) -> obj.getTouchHandler().registerMouseMoved(wx, wy));
    }

    protected InputMap getInputMap() {
        return inputMap;
    }

    protected ChopScreen getScreen() {
        return screen;
    }

    private interface Trigger {
        boolean call(GameObject obj, float worldX, float worldY, int pointer, int button);
    }
}
