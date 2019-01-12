package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;

public class PersonObject extends GameObject implements EventListener {

    private TextureRegion head;
    private float velocityY;
    private boolean isRolling;

    public PersonObject(AssetManager assets) {
        super(assets);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        head = atlas.findRegion("head");
    }

    @Override
    public void update(float delta) {
        if (isRolling) {
            velocityY -= 10 * delta;
            setY(getY() + velocityY);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        drawHead(batch);
    }

    private void drawHead(SpriteBatch batch) {
        float drawX = getX() - head.getRegionWidth() / 2f;
        float drawY = getY() - head.getRegionHeight() / 2f;
        batch.draw(head, drawX, drawY);
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.EVT_HEAD_CHOP)
            isRolling = true;
    }
}
