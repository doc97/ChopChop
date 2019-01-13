package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.model.DrawParameters;
import fi.chop.model.fsm.machines.PersonStateMachine;
import fi.chop.model.fsm.states.person.PersonStates;

public class PersonObject extends GameObject implements EventListener {

    private TextureRegion headAlive;
    private TextureRegion headDead;
    private DrawParameters headParams;
    private PersonStateMachine state;

    public PersonObject(AssetManager assets, OrthographicCamera camera) {
        super(assets, camera);
        setOrigin(0.5f, 0.5f);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        headAlive = atlas.findRegion("head-alive");
        headDead = atlas.findRegion("head-dead");
        setSize(headAlive.getRegionWidth(), headAlive.getRegionHeight());
        headParams = new DrawParameters(headAlive);
        state = new PersonStateMachine(this);
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        state.render(batch);
    }

    public void drawAliveHead(SpriteBatch batch) {
        draw(batch, headAlive, headParams);
    }

    public void drawDeadHead(SpriteBatch batch) {
        draw(batch, headDead, headParams);
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.EVT_HEAD_CHOP) {
            state.setCurrent(PersonStates.DEAD);
        }
    }
}
