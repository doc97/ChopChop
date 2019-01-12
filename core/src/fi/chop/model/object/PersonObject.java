package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PersonObject extends GameObject {

    private TextureRegion head;

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
}
