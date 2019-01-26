package fi.chop.model.object.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.engine.DrawParameters;
import fi.chop.input.TextButtonHandler;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.GameObject;
import fi.chop.model.world.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PopUpBoxObject extends GameObject {

    private TextObject text;
    private List<TextButtonObject> buttons;
    private TextureRegion background;
    private DrawParameters backgroundParams;

    private float paddingX;
    private float paddingY;
    private Color tint;

    public PopUpBoxObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
        buttons = new ArrayList<>();
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
        for (TextButtonObject btn : buttons) {
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
        text.setAlign(Align.TOP_LEFT);
        return this;
    }

    public PopUpBoxObject btn(String fontName, Supplier<String> supplier, Consumer<TextButtonObject> onClick) {
        TextButtonObject btn = new TextButtonObject(getAssets(), getCamera(), getPlayer());
        btn.create(fontName, supplier == null ? () -> "" : supplier);
        btn.setTouchHandler(new TextButtonHandler(btn, onClick));
        btn.getTransform().setParent(getTransform());
        btn.setAlign(Align.BOTTOM_LEFT);
        buttons.add(btn);
        return this;
    }

    public PopUpBoxObject tint(Color tint) {
        this.tint = tint;
        return this;
    }

    public void pack() {
        load();
        float width = calculateWidth();
        float height = calculateHeight();

        for (int i = 0; i < buttons.size(); i++) {
            // btn origin = (0.5f, 0.5f)
            buttons.get(i).getTransform().setPosition(
                    (i + 1) * width / (buttons.size() + 1),
                    buttons.get(i).getTransform().getHeight());
        }

        getTransform().setSize(width + paddingX, height + paddingY);
        backgroundParams.size(width + paddingX, height + paddingY).pos(-paddingX / 2, paddingY / 2);
    }

    private float calculateWidth() {
        float width = getTransform().getWidth();
        if (text != null)
            width = Math.max(width, text.getTransform().getWidth());

        float btnWidth = 0;
        for (TextButtonObject btn : buttons)
            btnWidth += btn.getTransform().getWidth();
        width = Math.max(width, btnWidth);

        return width;
    }

    private float calculateHeight() {
        float height = getTransform().getHeight();
        float textHeight = 0;
        if (text != null)
            textHeight = text.getTransform().getHeight();

        float btnMaxHeight = 0;
        for (TextButtonObject btn : buttons)
            btnMaxHeight = Math.max(btnMaxHeight, btn.getTransform().getHeight());
        height = Math.max(height, textHeight + btnMaxHeight);

        return height;
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
        for (TextButtonObject btn : buttons)
            btn.die();
    }

    @Override
    public GameObject[] getChildren() {
        GameObject[] children = new GameObject[buttons.size() + 1];
        children[0] = text;
        for (int i = 0; i < buttons.size(); i++)
            children[i + 1] = buttons.get(i);
        return children;
    }
}
