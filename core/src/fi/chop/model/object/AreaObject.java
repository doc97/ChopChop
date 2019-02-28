package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fi.chop.model.auxillary.Transform;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public class AreaObject extends GameObject {

    private ShapeRenderer renderer;
    private boolean debug;

    public AreaObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        renderer = new ShapeRenderer();
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public void load() { }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) {
        if (debug) {
            Transform t = getTransform().getGlobal();
            batch.end();
            renderer.setProjectionMatrix(getCamera().combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            float width = t.getScaledWidth();
            float height = t.getScaledHeight();
            float x = t.getX() - t.getOriginX() * width;
            float y = t.getY() - t.getOriginY() * height;
            renderer.setColor(1, 0, 0, 0.1f);
            renderer.rect(x, y, width, height);
            renderer.end();
            batch.begin();
        }
    }

    @Override
    public void dispose() { }
}
