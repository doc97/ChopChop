package fi.chop.model.fsm.states.person;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.fsm.machines.PersonStateMachine;
import fi.chop.model.fsm.states.ObjectState;
import fi.chop.model.object.PersonObject;

public class PersonDeadState extends ObjectState<PersonStateMachine, PersonObject> {

    private float velocityX;
    private float velocityY;
    private float rotVelocity;

    public PersonDeadState(PersonStateMachine machine, PersonObject object) {
        super(machine, object);
    }

    @Override
    public void enter() {
        velocityX = -150;
        velocityY = 0;
        rotVelocity = 90;
    }

    @Override
    public void exit() { }

    @Override
    public void update(float delta) {
        PersonObject obj = getObject();
        if (obj.getTransform().getGlobal().getBottom() <= 0) {
            velocityY = 0;
            obj.getTransform().setBottom(0);
        } else {
            velocityY -= 10;
        }
        obj.getTransform().translate(velocityX * delta, velocityY * delta);
        obj.getTransform().rotateDeg(rotVelocity * delta);

        if (getObject().isOutsideCameraView())
            obj.die();
    }

    @Override
    public void render(SpriteBatch batch) { }
}
