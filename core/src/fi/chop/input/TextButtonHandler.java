package fi.chop.input;

import fi.chop.model.object.util.TextButtonObject;

import java.util.function.Consumer;

public class TextButtonHandler extends TouchHandler<TextButtonObject> {

    private Consumer<TextButtonObject> onClick;

    public TextButtonHandler(TextButtonObject object, Consumer<TextButtonObject> onClick) {
        super(object);
        this.onClick = onClick;
    }

    @Override
    public void touchDown(TextButtonObject obj, float worldX, float worldY, int pointer, int button) {
        obj.press();
    }

    @Override
    public void touchUp(TextButtonObject obj, float worldX, float worldY, int pointer, int button) {
        obj.hover();
        onClick.accept(obj);
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
