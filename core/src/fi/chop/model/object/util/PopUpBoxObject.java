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
import fi.chop.model.object.gui.GUIObject;
import fi.chop.model.world.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PopUpBoxObject extends GUIObject {

    private Color tint;
    private TextObject text;
    private List<TextButtonObject> buttons;
    private TextureRegion background;
    private DrawParameters backgroundParams;

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
            text.pack();
        }
        for (TextButtonObject btn : buttons) {
            btn.load();
            btn.pack();
        }
    }

    public PopUpBoxObject size(float width, float height) {
        getTransform().setSize(width, height);
        return this;
    }

    public PopUpBoxObject text(String fontName, Supplier<String> supplier, Color tint,
                               float widthPx, int hAlign, boolean wrap) {
        if (text != null)
            text.dispose();
        text = new TextObject(getAssets(), getCamera(), getPlayer());
        text.create(fontName, supplier == null ? () -> "" : supplier, widthPx, hAlign, wrap);
        text.load();
        text.tint(tint);
        text.getTransform().setParent(getTransform());
        text.getTransform().setOrigin(0, 1);
        text.getTransform().setAlign(Align.TOP_LEFT);
        return this;
    }

    public PopUpBoxObject text(String fontName, Supplier<String> supplier, Color tint) {
        return text(fontName, supplier, tint, 0, com.badlogic.gdx.utils.Align.left, false);
    }

    public PopUpBoxObject btn(String fontName, Supplier<String> supplier, Consumer<TextButtonObject> onClick,
                              float widthPx, int hAlign, boolean wrap) {
        TextButtonObject btn = new TextButtonObject(getAssets(), getCamera(), getPlayer());
        btn.create(fontName, supplier == null ? () -> "" : supplier, widthPx, hAlign, wrap);
        btn.setTouchHandler(new TextButtonHandler(btn, onClick));
        btn.getTransform().setParent(getTransform());
        btn.getTransform().setAlign(Align.BOTTOM_LEFT);
        buttons.add(btn);
        return this;
    }

    public PopUpBoxObject btn(String fontName, Supplier<String> supplier, Consumer<TextButtonObject> onClick) {
        return btn(fontName, supplier, onClick, 0, com.badlogic.gdx.utils.Align.left, false);
    }

    public PopUpBoxObject tint(Color tint) {
        this.tint = tint;
        return this;
    }

    public PopUpBoxObject pad(float padTop, float padLeft, float padRight, float padBottom) {
        super.pad(padTop, padLeft, padRight, padBottom);
        return this;
    }

    public void pack() {
        load();
        float width = calculateWidth();
        float height = calculateHeight();

        text.getTransform().setPosition(getPadLeft(), -getPadTop());
        for (int i = 0; i < buttons.size(); i++) {
            // btn origin = (0.5f, 0.5f)
            buttons.get(i).getTransform().setPosition(
                    (i + 1) * width / (buttons.size() + 1) + getPadLeft(),
                    buttons.get(i).getTransform().getHeight() / 2f + getPadBottom());
        }

        float paddingX = getPadLeft() + getPadRight();
        float paddingY = getPadTop() + getPadBottom();
        getTransform().setSize(width + paddingX,  height + paddingY);
        backgroundParams.size(width + paddingX,  height + paddingY);
    }

    private float calculateWidth() {
        float width = getTransform().getWidth();
        if (text != null)
            width = Math.max(width, text.getTransform().getWidth());

        float btnWidth = 0;
        for (TextButtonObject btn : buttons)
            btnWidth += btn.getTransform().getWidth();
        width = Math.max(width, btnWidth);

        width += getPadLeft() + getPadRight();
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

        height += getPadTop() + getPadBottom();
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
