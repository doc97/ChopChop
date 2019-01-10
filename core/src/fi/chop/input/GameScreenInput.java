package fi.chop.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import fi.chop.model.InputMap;

public class GameScreenInput extends InputAdapter {

    private InputMap inputMap;

    public GameScreenInput(InputMap inputMap) {
        this.inputMap = inputMap;
    }

    @Override
    public boolean keyDown(int keycode) {
        InputMap.Action action = inputMap.getAction(keycode);
        switch (action) {
            case INTERACT:
                Gdx.app.log("GameScreenInput", "INTERACT");
                return true;
            case BACK:
                Gdx.app.exit();
                return true;
            default:
                return false;
        }
    }
}
