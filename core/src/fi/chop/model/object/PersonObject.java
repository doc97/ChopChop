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
    private float velocityX;
    private float velocityY;
    private float rotVelocity;
    private boolean hasHeadAttached;

    public PersonObject(AssetManager assets) {
        super(assets);
        hasHeadAttached = true;
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        head = atlas.findRegion("head");
        setSize(head.getRegionWidth(), head.getRegionHeight());
    }

    @Override
    public void update(float delta) {
        if (hasHeadAttached)
            return;

        if (getY() <= getHeight() / 2) {
            velocityY = 0;
            setY(getHeight() / 2);
        } else {
            velocityY -= 10;
        }
        translate(velocityX * delta, velocityY * delta);
        rotateDeg(rotVelocity * delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        drawHead(batch);
    }

    private void drawHead(SpriteBatch batch) {
        float drawX = getX() - head.getRegionWidth() / 2f;
        float drawY = getY() - head.getRegionHeight() / 2f;
        float originX = getWidth() / 2;
        float originY = getHeight() / 2;
        float scaleX = 1;
        float scaleY = 1;
        batch.draw(head, drawX, drawY, originX, originY, getWidth(), getHeight(), scaleX, scaleY, (float) getRotationDeg());
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.EVT_HEAD_CHOP) {
            velocityX = -150;
            velocityY = 0;
            rotVelocity = 90;
            hasHeadAttached = false;
        }
    }
}
