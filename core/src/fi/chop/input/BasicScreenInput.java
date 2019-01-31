package fi.chop.input;

import fi.chop.Chop;
import fi.chop.engine.InputMap;
import fi.chop.event.Events;
import fi.chop.screens.ChopScreen;

public class BasicScreenInput extends ChopScreenInput {

    public BasicScreenInput(ChopScreen screen, InputMap inputMap) {
        super(screen, inputMap);
    }

    @Override
    public boolean keyDown(int keyCode) {
        InputMap.Action action = getInputMap().getAction(keyCode);
        if (action == InputMap.Action.BACK) {
            Chop.events.notify(Events.ACTION_BACK);
            return true;
        }
        return false;
    }
}
