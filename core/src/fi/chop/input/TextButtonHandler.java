package fi.chop.input;

import fi.chop.functional.Procedure;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.util.TextButtonObject;

public class TextButtonHandler extends TouchHandler {

    private Procedure onClick;

    public TextButtonHandler(Procedure onClick) {
        this.onClick = onClick;
    }

    @Override
    public void touchDown(GameObject obj, float worldX, float worldY, int pointer, int button) {
        ((TextButtonObject) obj).press();
    }

    @Override
    public void touchUp(GameObject obj, float worldX, float worldY, int pointer, int button) {
        ((TextButtonObject) obj).hover();
        onClick.run();
    }

    @Override
    public void enter(GameObject obj, float worldX, float worldY) {
        ((TextButtonObject) obj).hover();
    }

    @Override
    public void exit(GameObject obj, float worldX, float worldY) {
        ((TextButtonObject) obj).normal();
    }
}
