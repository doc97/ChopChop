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
        onClick.run();
    }

    @Override
    public void enter(GameObject obj, float worldX, float worldY) {
        TextButtonObject btn = (TextButtonObject) obj;
        btn.useStyle(TextButtonObject.StyleType.HOVER);
    }

    @Override
    public void exit(GameObject obj, float worldX, float worldY) {
        TextButtonObject btn = (TextButtonObject) obj;
        btn.useStyle(TextButtonObject.StyleType.NORMAL);
    }
}
