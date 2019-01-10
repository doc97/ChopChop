package fi.chop.input;

import com.badlogic.gdx.InputAdapter;
import fi.chop.model.InputMap;
import fi.chop.screens.ChopScreen;

public class ChopScreenInput extends InputAdapter {

    private InputMap inputMap;
    private ChopScreen screen;

    public ChopScreenInput(ChopScreen screen, InputMap inputMap) {
        this.screen = screen;
        this.inputMap = inputMap;
    }

    protected InputMap getInputMap() {
        return inputMap;
    }

    protected ChopScreen getScreen() {
        return screen;
    }
}
