package fi.chop.input.touchhandler;

import fi.chop.model.object.gui.TextButtonObject;

import java.util.function.Consumer;

public class TextButtonHandler extends TouchHandler<TextButtonObject> {

    private Consumer<TextButtonObject> onClick;
    private Consumer<TextButtonObject> onEnter;

    public TextButtonHandler(TextButtonObject object) {
        super(object);
        this.onClick = onClick;
    }

    public TextButtonHandler onClick(Consumer<TextButtonObject> onClick) {
        this.onClick = onClick;
        return this;
    }

    public TextButtonHandler onEnter(Consumer<TextButtonObject> onEnter) {
        this.onEnter = onEnter;
        return this;
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
        onEnter.accept(obj);
        return true;
    }

    @Override
    public boolean exit(TextButtonObject obj, float worldX, float worldY) {
        obj.normal();
        return false;
    }
}
