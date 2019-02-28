package fi.chop.input.touchhandler;

import fi.chop.model.object.AreaObject;

import java.util.function.Consumer;

public class AreaHandler extends TouchHandler<AreaObject> {

    private Consumer<AreaHandler> onUp;
    private Consumer<AreaHandler> onEnter;
    private Consumer<AreaHandler> onExit;
    private Consumer<AreaHandler> onMove;

    public AreaHandler(AreaObject object) {
        super(object);
        onUp = (handler) -> {};
        onEnter = (handler) -> {};
        onExit = (handler) -> {};
        onMove = (handler) -> {};
    }

    public AreaHandler onUp(Consumer<AreaHandler> onUp) {
        this.onUp = onUp;
        return this;
    }

    public AreaHandler onEnter(Consumer<AreaHandler> onEnter) {
        this.onEnter = onEnter;
        return this;
    }

    public AreaHandler onExit(Consumer<AreaHandler> onExit) {
        this.onExit = onExit;
        return this;
    }

    public AreaHandler onMove(Consumer<AreaHandler> onMove) {
        this.onMove = onMove;
        return this;
    }

    @Override
    public boolean touchUp(AreaObject object, float worldX, float worldY, int pointer, int button) {
        onUp.accept(this);
        return true;
    }

    @Override
    public boolean enter(AreaObject object, float worldX, float worldY) {
        onEnter.accept(this);
        return true;
    }

    @Override
    public boolean exit(AreaObject object, float worldX, float worldY) {
        onExit.accept(this);
        return true;
    }

    @Override
    public boolean moved(AreaObject object, float worldX, float worldY) {
        onMove.accept(this);
        return false;
    }
}
