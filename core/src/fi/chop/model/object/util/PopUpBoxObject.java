package fi.chop.model.object.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.input.TextButtonHandler;
import fi.chop.model.object.GameObject;
import fi.chop.model.world.Player;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PopUpBoxObject extends GameObject {

    private TextObject text;
    private TextButtonObject btn;
    private TextureRegion background;
    private DrawParameters backgroundParams;

    private float paddingX;
    private float paddingY;
    private Color tint;

    public PopUpBoxObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
        getTransform().setOrigin(0, 1);
    }

    @Override
    public void load() {
        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion("meter-fill");
        backgroundParams = new DrawParameters();

        if (text != null) {
            text.load();
            text.generateTexture();
        }
        if (btn != null) {
            btn.load();
            btn.generateTexture();
        }
    }

    public PopUpBoxObject pad(float paddingX, float paddingY) {
        this.paddingX = paddingX;
        this.paddingY = paddingY;
        return this;
    }

    public PopUpBoxObject size(float width, float height) {
        getTransform().setSize(width, height);
        return this;
    }

    public PopUpBoxObject text(String fontName, Supplier<String> supplier, Color tint) {
        if (text != null)
            text.dispose();
        text = new TextObject(getAssets(), getCamera(), getPlayer());
        text.create(fontName, supplier == null ? () -> "" : supplier);
        text.load();
        text.tint(tint);
        text.pad(10, 10);
        text.getTransform().setParent(getTransform());
        text.getTransform().setOrigin(0, 1);
        return this;
    }

    public PopUpBoxObject btn(String fontName, Supplier<String> supplier, Consumer<TextButtonObject> onClick) {
        if (btn != null)
            btn.dispose();
        btn = new TextButtonObject(getAssets(), getCamera(), getPlayer());
        btn.create(fontName, supplier == null ? () -> "" : supplier);
        btn.setTouchHandler(new TextButtonHandler(btn, onClick));
        btn.getTransform().setParent(getTransform());
        return this;
    }

    public PopUpBoxObject tint(Color tint) {
        this.tint = tint;
        return this;
    }

    public void pack() {
        load();
        float width = getTransform().getWidth();
        float height = getTransform().getHeight();
        if (text != null) {
            width = Math.max(width, text.getTransform().getWidth());
            height = Math.max(height, text.getTransform().getHeight());
        }
        if (btn != null) {
            width = Math.max(width, btn.getTransform().getWidth());
            height += btn.getTransform().getHeight();
            // btn origin = (0.5f, 0.5f)
            btn.getTransform().setPosition(width / 2, -height + btn.getTransform().getHeight() / 2);
        }

        getTransform().setSize(width + paddingX, height + paddingY);
        backgroundParams.size(width + paddingX, height + paddingY).pos(-paddingX / 2, paddingY / 2);
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(tint);
        draw(batch, background, backgroundParams);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        text.die();
        btn.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { text, btn };
    }
}
