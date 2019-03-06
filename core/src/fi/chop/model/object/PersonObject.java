package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.model.fsm.machines.PersonStateMachine;
import fi.chop.model.fsm.states.person.PersonStates;
import fi.chop.model.object.util.TextureRegionObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;
import java.util.Random;

public class PersonObject extends GameObject {

    private TextureRegionObject head;
    private PersonStateMachine state;

    public PersonObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
    }

    @Override
    public void load() {
		Random rand = new Random();
        head = new TextureRegionObject(getAssets(), getCamera(), getWorld(), getPlayer());
        head.setRegion("textures/packed/Chop.atlas", "Head" + rand.nextInt(11));
        head.load();
        head.getTransform().setParent(getTransform());
        head.getTransform().setOrigin(0.5f, 0.5f);
        state = new PersonStateMachine(this);
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) { }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        head.die();
    }

    @Override
    public void handle(Events event, EventData data) {
        switch (event) {
            case EVT_PERSON_KILLED:
                if (state.getCurrent() == PersonStates.IDLE)
                    state.setCurrent(PersonStates.DEAD);
                break;
            case ACTION_MERCY:
                if (state.getCurrent() == PersonStates.IDLE) {
                    Chop.events.notify(Events.EVT_PERSON_SAVED);
                    state.setCurrent(PersonStates.SAVED);
                }
                break;
        }
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { head };
    }
}
