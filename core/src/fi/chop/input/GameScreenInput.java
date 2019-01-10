package fi.chop.input;

import com.badlogic.gdx.Gdx;
import fi.chop.model.InputMap;
import fi.chop.screens.ChopScreen;

public class GameScreenInput extends ChopScreenInput {

    public GameScreenInput(ChopScreen screen, InputMap inputMap) {
        super(screen, inputMap);
    }

    @Override
    public boolean keyDown(int keyCode) {
        InputMap.Action action = getInputMap().getAction(keyCode);
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
