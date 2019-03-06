package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.input.touchhandler.TextButtonHandler;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.util.NinePatchObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PopUpBoxObject extends GUIObject {

    private TextObject text;
    private List<TextButtonObject> buttons;
    private NinePatchObject background;

    public PopUpBoxObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        buttons = new ArrayList<>();
    }

    @Override
    public void pack() {
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
        background.getTransform().setSize(width + paddingX,  height + paddingY);
    }

    @Override
    public void load() {
        background.load();

        if (text != null) {
            text.load();
            text.pack();
        }
        for (TextButtonObject btn : buttons) {
            btn.load();
            btn.pack();
        }
    }

    @Override
    public void render(SpriteBatch batch) { }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        background.die();
        text.die();
        for (TextButtonObject btn : buttons)
            btn.die();
    }

    @Override
    public GameObject[] getChildren() {
        GameObject[] children = new GameObject[buttons.size() + 2];
        children[0] = background;
        children[1] = text;
        for (int i = 0; i < buttons.size(); i++)
            children[i + 2] = buttons.get(i);
        return children;
    }

    public PopUpBoxObject size(float width, float height) {
        getTransform().setSize(width, height);
        return this;
    }

    public PopUpBoxObject background(String atlasName, String patchName, Color tint) {
        background = new NinePatchObject(getAssets(), getCamera(), getWorld(), getPlayer());
        background.init(atlasName, patchName);
        background.setTint(tint);
        background.getTransform().setParent(getTransform());
        background.getTransform().setAlign(Align.TOP_LEFT);
        background.getTransform().setOrigin(0, 1);
        return this;
    }

    public PopUpBoxObject background(String atlasName, String patchName) {
        return background(atlasName, patchName, Color.WHITE);
    }

    public PopUpBoxObject text(String fontName, Supplier<String> supplier, Color tint,
                               float widthPx, int hAlign, boolean wrap) {
        if (text != null)
            text.dispose();
        supplier = supplier == null ? () -> "" : supplier;
        text = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
        text.create(fontName, supplier, supplier, widthPx, hAlign, wrap);
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
        supplier = supplier == null ? () -> "" : supplier;
        TextButtonObject btn = new TextButtonObject(getAssets(), getCamera(), getWorld(), getPlayer());
        btn.create(fontName, supplier, supplier, widthPx, hAlign, wrap);
        btn.setTouchHandler(new TextButtonHandler(btn).onClick(onClick));
        btn.getTransform().setParent(getTransform());
        btn.getTransform().setAlign(Align.BOTTOM_LEFT);
        buttons.add(btn);
        return this;
    }

    public PopUpBoxObject btn(String fontName, Supplier<String> supplier, Consumer<TextButtonObject> onClick) {
        return btn(fontName, supplier, onClick, 0, com.badlogic.gdx.utils.Align.left, false);
    }

    public PopUpBoxObject pad(float padTop, float padLeft, float padRight, float padBottom) {
        super.pad(padTop, padLeft, padRight, padBottom);
        return this;
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
}
