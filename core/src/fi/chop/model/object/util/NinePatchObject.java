package fi.chop.model.object.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import fi.chop.model.auxillary.Transform;
import fi.chop.model.object.GameObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public class NinePatchObject extends GameObject {

    private String atlasName;
    private String patchName;
    private NinePatch patch;

    public NinePatchObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get(atlasName);
        TextureAtlas.AtlasRegion region = atlas.findRegion(patchName);

        // create a "degenerate" NinePatch if it isn't a nine patch asset (with splits)
        if (region.splits == null)
            patch = new NinePatch(region);
        else
            patch = atlas.createPatch(patchName);
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) {
        Transform t = getTransform().getGlobal();
        saveAndSetTint(batch);
        patch.draw(batch,
                t.getX() - t.getOriginX() * t.getWidth(),
                t.getY() - t.getOriginY() * t.getHeight(),
                t.getOriginX() * t.getWidth(),
                t.getOriginY() * t.getHeight(),
                t.getWidth(), t.getHeight(),
                t.getScaleX(), t.getScaleX(),
                (float) t.getRotationDeg());
        restoreTint(batch);
    }

    @Override
    public void dispose() { }

    public void init(String atlasName, String patchName) {
        this.atlasName = atlasName;
        this.patchName = patchName;
    }
}
