package fi.chop.input;

import fi.chop.functional.Procedure;
import fi.chop.model.object.util.TextButtonObject;

public class TextButtonHandler extends TouchHandler<TextButtonObject> {

    private Procedure onClick;

    public TextButtonHandler(Procedure onClick) {
        this.onClick = onClick;
    }

    @Override
    public void touchDown(TextButtonObject obj, float worldX, float worldY, int pointer, int button) {
        obj.press();
    }

    @Override
    public void touchUp(TextButtonObject obj, float worldX, float worldY, int pointer, int button) {
        obj.hover();
        onClick.run();
    }

    @Override
    public void enter(TextButtonObject obj, float worldX, float worldY) {
        obj.hover();
    }

    @Override
    public void exit(TextButtonObject obj, float worldX, float worldY) {
        obj.normal();
    }
}
