package fi.chop.input;

import com.badlogic.gdx.InputAdapter;
import fi.chop.model.InputMap;
import fi.chop.screens.MainMenuScreen;
import fi.chop.screens.Screens;

public class MainMenuScreenInput extends InputAdapter {

    private InputMap inputMap;
    private MainMenuScreen screen;

    public MainMenuScreenInput(MainMenuScreen screen, InputMap inputMap) {
        this.screen = screen;
        this.inputMap = inputMap;
    }

    @Override
    public boolean keyDown(int keyCode) {
        InputMap.Action action = inputMap.getAction(keyCode);
        if (action == InputMap.Action.INTERACT) {
            screen.setScreen(Screens.GAME);
            return true;
        }
        return false;
    }
}
