package fi.chop.input.touchhandler;

import fi.chop.model.object.gui.TextButtonObject;

import java.util.function.Consumer;

public class TextButtonHandler extends TouchHandler<TextButtonObject> {

    private Consumer<TextButtonObject> onClick;

    public TextButtonHandler(TextButtonObject object, Consumer<TextButtonObject> onClick) {
        super(object);
        this.onClick = onClick;
    }

    @Override
    public boolean touchDown(TextButtonObject obj, float worldX, float worldY, int pointer, int button) {
        obj.press();
        return true;
    }

    @Override
    public boolean touchUp(TextButtonObject obj, float worldX, float worldY, int pointer, int button) {
        obj.hover();
        onClick.accept(obj);
        return true;
    }

    @Override
    public boolean enter(TextButtonObject obj, float worldX, float worldY) {
        obj.hover();
        return true;
    }

    @Override
    public boolean exit(TextButtonObject obj, float worldX, float worldY) {
        obj.normal();
        return false;
    }
}
