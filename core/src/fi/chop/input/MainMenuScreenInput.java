package fi.chop.input;

import com.badlogic.gdx.Gdx;
import fi.chop.engine.InputMap;
import fi.chop.screens.ChopScreen;
import fi.chop.screens.Screens;

public class MainMenuScreenInput extends ChopScreenInput {

    public MainMenuScreenInput(ChopScreen screen, InputMap inputMap) {
        super(screen, inputMap);
    }

    @Override
    public boolean keyDown(int keyCode) {
        InputMap.Action action = getInputMap().getAction(keyCode);
        if (action == InputMap.Action.INTERACT) {
            getScreen().setScreen(Screens.GUILLOTINE);
            return true;
        } else if (action == InputMap.Action.BACK) {
            Gdx.app.exit();
            return true;
        }
        return false;
    }
}
