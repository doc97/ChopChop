package fi.chop.model.fsm.machines;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.fsm.states.ObjectState;

public class ObjectStateMachine<K, V extends ObjectState> extends StateMachine<K, V> {

    public void render(SpriteBatch batch) {
        if (states.isEmpty())
            return;
        states.get(current).render(batch);
    }
}
