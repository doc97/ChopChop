package fi.chop.input;

import fi.chop.engine.InputMap;
import fi.chop.screens.ChopScreen;
import fi.chop.screens.Screens;

public class TownScreenInput extends ChopScreenInput {

    public TownScreenInput(ChopScreen screen, InputMap inputMap) {
        super(screen, inputMap);
    }

    @Override
    public boolean keyDown(int keyCode) {
        InputMap.Action action = getInputMap().getAction(keyCode);
        switch (action) {
            case BACK:
                getScreen().setScreen(Screens.MAIN_MENU);
                return true;
            case INTERACT:
                getScreen().setScreen(Screens.EXECUTION);
            default:
                return false;
        }
    }
}
