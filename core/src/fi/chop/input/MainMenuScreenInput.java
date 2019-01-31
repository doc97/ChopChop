package fi.chop.input;

import fi.chop.Chop;
import fi.chop.engine.InputMap;
import fi.chop.event.Events;
import fi.chop.screens.ChopScreen;

public class MainMenuScreenInput extends ChopScreenInput {

    public MainMenuScreenInput(ChopScreen screen, InputMap inputMap) {
        super(screen, inputMap);
    }

    @Override
    public boolean keyDown(int keyCode) {
        InputMap.Action action = getInputMap().getAction(keyCode);
        if (action == InputMap.Action.BACK) {
            Chop.events.notify(Events.ACTION_EXIT);
            return true;
        }
        return false;
    }
}
