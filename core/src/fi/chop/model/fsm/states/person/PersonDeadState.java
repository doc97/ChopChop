package fi.chop.model.fsm.states.person;

import fi.chop.model.fsm.machines.PersonStateMachine;
import fi.chop.model.object.PersonObject;

public class PersonDeadState extends PersonState {

    private float velocityX;
    private float velocityY;
    private float rotVelocity;

    public PersonDeadState(PersonStateMachine machine, PersonObject object) {
        super(machine, object);
        velocityX = -150;
        rotVelocity = 90;
    }

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

        if (obj.getX() + obj.getWidth() < 0)
            obj.die();
    }
}
