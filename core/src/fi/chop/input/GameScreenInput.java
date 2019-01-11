package fi.chop.input;

import fi.chop.Chop;
import fi.chop.event.Events;
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
                Chop.events.notify(Events.ACTION_INTERACT);
                return true;
            case BACK:
                Chop.events.notify(Events.ACTION_BACK);
                return true;
            default:
                return false;
        }
    }
}
