package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
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

    private TextureRegion head;
    private DrawParameters headParams;
    private PersonStateMachine state;

    public PersonObject(AssetManager assets) {
        super(assets);
        setOrigin(0.5f, 0.5f);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        head = atlas.findRegion("head-alive");
        setSize(head.getRegionWidth(), head.getRegionHeight());
        headParams = new DrawParameters(head);
        state = new PersonStateMachine(this);
    }

    @Override
    public void update(float delta) {
        state.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        draw(batch, head, headParams);
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.EVT_HEAD_CHOP) {
            state.setCurrent(PersonStates.DEAD);
            TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
            head = atlas.findRegion("head-dead");
        }
    }
}
