package fi.chop.input;

import fi.chop.Chop;
import fi.chop.engine.InputMap;
import fi.chop.event.Events;
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
                Chop.events.notify(Events.ACTION_BACK);
                return true;
            case INTERACT:
                Chop.events.notify(Events.ACTION_INTERACT);
            default:
                return false;
        }
    }
}
