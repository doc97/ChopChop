package fi.chop.model.fsm.states.guillotine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.Events;
import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.GuillotineObject;

public class GuillotineRestoreState extends ObjectState<GuillotineStateMachine, GuillotineObject> {

    private static final int RESTORE_SPEED_PX_PER_SEC = 200;

    public GuillotineRestoreState(GuillotineStateMachine machine, GuillotineObject object) {
        super(machine, object);
    }

    @Override
    public void enter() { }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) {
        float leftToRestore = GuillotineObject.IDLE_Y_OFFSET_PX - getObject().getBladeYOffset();
        float offsetDelta = Math.min(RESTORE_SPEED_PX_PER_SEC * delta, leftToRestore);
        getObject().addBladeYOffset(offsetDelta);
        if (getObject().getBladeYOffset() == GuillotineObject.IDLE_Y_OFFSET_PX) {
            getObject().resetRaiseCount();
            Chop.events.notify(Events.EVT_GUILLOTINE_RESTORED);
            getStateMachine().setCurrent(GuillotineStates.IDLE);
        }
    }

    @Override
    public void render(SpriteBatch batch) { }
}
