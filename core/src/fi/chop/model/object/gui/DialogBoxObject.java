package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fi.chop.Chop;
import fi.chop.engine.DrawParameters;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.functional.Procedure;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.GameObject;
import fi.chop.model.world.Player;

import java.util.function.Supplier;

public class DialogBoxObject extends GUIObject {

    private int charsToShow;
    private float startDelaySec;
    private float charDelaySec;
    private float widthPx;
    private Color backgroundTint;
    private Color textTint;
    private String fontName;
    private Supplier<String> textSupplier;
    private TextObject text;
    private TextureRegion background;
    private DrawParameters backgroundParams;
    private Procedure onFinish;

    public DialogBoxObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
        backgroundTint = new Color(Color.WHITE);
        onFinish = () -> {};
    }

    @Override
    public void pack() {
        if (text == null)
            throw new IllegalStateException("A text must be specified before calling load()");
        text.pack();
        text.getTransform().setPosition(getPadLeft(), -getPadTop());

        float width = text.getTransform().getWidth() + getPadLeft() + getPadRight();
        float height = text.getTransform().getHeight() + getPadTop() + getPadBottom();
        getTransform().setSize(width, height);
        backgroundParams.size(width, height);
    }

    @Override
    public void load() {
        if (text == null)
            throw new IllegalStateException("A text must be specified before calling load()");

        TextureAtlas atlas = getAssets().get("textures/packed/Chop.atlas", TextureAtlas.class);
        background = atlas.findRegion("pixel-white");
        backgroundParams = new DrawParameters();

        text.create(fontName, this::getCurrentText, widthPx, com.badlogic.gdx.utils.Align.left, true);
        text.load();
        text.tint(textTint);
        text.getTransform().setParent(getTransform());
        text.getTransform().setOrigin(0, 1);
        text.getTransform().setAlign(Align.TOP_LEFT);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(backgroundTint);
        draw(batch, background, backgroundParams);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        text.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { text };
    }

    @Override
    public DialogBoxObject pad(float padTop, float padLeft, float padRight, float padBottom) {
        super.pad(padTop, padLeft, padRight, padBottom);
        return this;
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.ACTION_INTERACT) {
            int textLength = textSupplier.get().length();
            if (charsToShow != textLength) {
                charsToShow = textLength;
            } else {
                die();
                onFinish.run();
            }
        }
    }

    @Override
    public void onSceneAdd() {
        charsToShow = 0;
        Chop.timer.addAction(startDelaySec + charDelaySec, () -> { charsToShow++; nextLetter(); });
    }

    public DialogBoxObject width(float widthPx) {
        this.widthPx = widthPx;
        return this;
    }

    public DialogBoxObject text(String fontName, Supplier<String> textSupplier, Color textTint) {
        this.fontName = fontName;
        this.textSupplier = textSupplier;
        this.textTint = textTint;
        charsToShow = textSupplier.get().length();
        text = new TextObject(getAssets(), getCamera(), getPlayer());
        return this;
    }

    public DialogBoxObject speed(float startDelaySec, float charDelaySec) {
        this.startDelaySec = startDelaySec;
        this.charDelaySec = charDelaySec;
        return this;
    }

    public DialogBoxObject tint(Color backgroundTint) {
        this.backgroundTint = backgroundTint;
        return this;
    }

    public DialogBoxObject onFinish(Procedure onFinish) {
        this.onFinish = onFinish;
        return this;
    }

    private String getCurrentText() {
        String text = textSupplier.get();
        return text.substring(0, Math.min(charsToShow, text.length()));
    }

    private void nextLetter() {
        if (charsToShow < textSupplier.get().length())
            Chop.timer.addAction(charDelaySec, () -> { charsToShow++; nextLetter(); });
    }
}
