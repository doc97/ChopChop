package fi.chop.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameScreenInput extends InputAdapter {
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            Gdx.app.log("GameScreenInput", "SPACE");
            return true;
        }
        return false;
    }
}
