package fi.chop.engine;

import com.badlogic.gdx.Input;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class InputMap {

    public enum Action {
        NONE, BACK, INTERACT, MERCY
    }

    private final Map<Action, Integer> map;
    private final Map<Integer, Action> reverseMap;

    public InputMap() {
        map = new EnumMap<>(Action.class);
        reverseMap = new HashMap<>();
        defaults();
    }

    public void defaults() {
        bind(Action.BACK, Input.Keys.ESCAPE);
        bind(Action.INTERACT, Input.Keys.SPACE);
        bind(Action.MERCY, Input.Keys.SHIFT_RIGHT);
    }

    public void bind(Action action, int keyCode) {
        reverseMap.remove(map.get(action));
        map.put(action, keyCode);
        reverseMap.put(keyCode, action);
    }

    public void unbind(Action action) {
        reverseMap.remove(map.get(action));
        map.remove(action);
    }

    public int getKeyCode(Action action) {
        return map.getOrDefault(action, -1);
    }

    public Action getAction(int keyCode) {
        return reverseMap.getOrDefault(keyCode, Action.NONE);
    }
}
