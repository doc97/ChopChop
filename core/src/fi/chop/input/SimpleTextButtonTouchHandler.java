package fi.chop.input;

import fi.chop.model.object.gui.TextButtonObject;

import java.util.function.Consumer;

public class SimpleTextButtonTouchHandler extends TouchHandler<TextButtonObject> {

    private Consumer<TextButtonObject> onUp;
    private Consumer<TextButtonObject> onDown;
    private Consumer<TextButtonObject> onDrag;
    private Consumer<TextButtonObject> onEnter;
    private Consumer<TextButtonObject> onExit;
    private Consumer<TextButtonObject> onMove;

    public SimpleTextButtonTouchHandler(TextButtonObject object) {
        super(object);
        onUp = (obj) -> {};
        onDown = (obj) -> {};
        onDrag = (obj) -> {};
        onEnter = (obj) -> {};
        onExit = (obj) -> {};
        onMove = (obj) -> {};
    }

    public SimpleTextButtonTouchHandler onUp(Consumer<TextButtonObject> onUp) {
        this.onUp = onUp;
        return this;
    }

    public SimpleTextButtonTouchHandler onDown(Consumer<TextButtonObject> onDown) {
        this.onDown = onDown;
        return this;
    }

    public SimpleTextButtonTouchHandler onDrag(Consumer<TextButtonObject> onDrag) {
        this.onDrag = onDrag;
        return this;
    }

    public SimpleTextButtonTouchHandler onEnter(Consumer<TextButtonObject> onEnter) {
        this.onEnter = onEnter;
        return this;
    }

    public SimpleTextButtonTouchHandler onExit(Consumer<TextButtonObject> onExit) {
        this.onExit = onExit;
        return this;
    }

    @Override
    public boolean touchDown(TextButtonObject obj, float worldX, float worldY, int pointer, int button) {
        obj.press();
        onDown.accept(obj);
        return true;
    }

    @Override
    public boolean touchUp(TextButtonObject obj, float worldX, float worldY, int pointer, int button) {
        obj.hover();
        onUp.accept(obj);
        return true;
    }

    @Override
    public boolean touchDragged(TextButtonObject obj, float worldX, float worldY, int pointer) {
        onDrag.accept(obj);
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
        onExit.accept(obj);
        return false;
    }

    @Override
    public boolean moved(TextButtonObject obj, float worldX, float worldY) {
        onMove.accept(obj);
        return false;
    }
}
