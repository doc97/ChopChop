package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.model.Sentence;

public class ScrollObject extends GameObject {

    private final Sentence sentence;
    private TextureRegion scrollTexture;

    public ScrollObject(AssetManager assets, OrthographicCamera camera, Sentence sentence) {
        super(assets, camera);
        this.sentence = sentence;
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        scrollTexture = atlas.findRegion("powerbar-background");
        setSize(scrollTexture.getRegionWidth(), scrollTexture.getRegionHeight());
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(scrollTexture, 800, 200, 100, 400);
    }
}
