package fi.chop.model.fsm.states.person;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.fsm.machines.PersonStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.PersonObject;

public class PersonSavedState extends ObjectState<PersonStateMachine, PersonObject> {

    private static final int VEL_Y_PX = 200;
    private static final int MAX_X_OFFSET_PX = 75;

    private float startX;
    private float xOffset;
    private float time;

    public PersonSavedState(PersonStateMachine machine, PersonObject object) {
        super(machine, object);
    }

    @Override
    public void enter() {
        startX = getObject().getX();
        xOffset = 0;
        time = 0;
    }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) {
        time += 5 * delta;
        xOffset = (float) Math.sin(time);
        getObject().setX(startX + xOffset * MAX_X_OFFSET_PX);
        getObject().translate(0, VEL_Y_PX * delta);

        if (getObject().isOutsideCameraView())
            getObject().die();
    }

    @Override
    public void render(SpriteBatch batch) {
        getObject().drawSavedHead(batch);
    }
}
