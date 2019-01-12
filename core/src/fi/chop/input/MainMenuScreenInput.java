package fi.chop.input;

import fi.chop.model.InputMap;
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
        }
        return false;
    }
}
