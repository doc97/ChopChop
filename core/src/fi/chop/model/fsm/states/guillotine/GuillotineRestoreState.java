package fi.chop.model.fsm.states.guillotine;

import fi.chop.model.fsm.machines.GuillotineStateMachine;
import fi.chop.model.object.GuillotineObject;

public class GuillotineRestoreState extends GuillotineState {

    private static final int RESTORE_SPEED_PX_PER_SEC = 200;

    public GuillotineRestoreState(GuillotineStateMachine machine, GuillotineObject object) {
        super(machine, object);
    }

    @Override
    public void update(float delta) {
        float leftToRestore = GuillotineObject.IDLE_Y_OFFSET_PX - getObject().getBladeYOffset();
        float offsetDelta = Math.min(RESTORE_SPEED_PX_PER_SEC * delta, leftToRestore);
        getObject().addBladeYOffset(offsetDelta);
        if (getObject().getBladeYOffset() == GuillotineObject.IDLE_Y_OFFSET_PX) {
            getObject().resetRaiseCount();
            getStateMachine().setCurrent(GuillotineStates.IDLE);
        }
    }
}
