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
        if (obj.getY() <= obj.getHeight() / 2) {
            velocityY = 0;
            obj.setY(obj.getHeight() / 2);
        } else {
            velocityY -= 10;
        }
        obj.translate(velocityX * delta, velocityY * delta);
        obj.rotateDeg(rotVelocity * delta);

        if (getObject().isOutsideCameraView())
            obj.die();
    }

    @Override
    public void render(SpriteBatch batch) {
        getObject().drawDeadHead(batch);
    }
}
