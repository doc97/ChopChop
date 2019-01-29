package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.functional.Procedure;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.util.TextureRegionObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

import java.util.function.Supplier;

public class DialogBoxObject extends GUIObject {

    private int charsToShow;
    private float startDelaySec;
    private float charDelaySec;
    private float widthPx;
    private Color backgroundTint;
    private Color textTint;
    private String avatarAssetName;
    private String atlasAssetName;
    private String fontName;
    private Supplier<String> textSupplier;
    private TextObject text;
    private TextureRegionObject avatar;
    private TextureRegionObject background;
    private Procedure onFinish;

    public DialogBoxObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        backgroundTint = new Color(Color.WHITE);
        onFinish = () -> {};
    }

    @Override
    public void pack() {
        if (text == null)
            throw new IllegalStateException("A text must be specified before calling load()");
        text.pack();

        float textWidth = text.getTransform().getWidth();
        float textHeight = text.getTransform().getHeight();
        float avatarSize = textHeight;
        float totalHeight = textHeight + getPadTop() + getPadBottom();
        float totalWidth = textWidth + avatarSize + getPadLeft() + getPadRight();
        background.getParameters().size(textWidth + getPadRight(), textHeight + getPadTop() + getPadBottom());
        background.getTransform().setPosition(getPadLeft(), 0);
        background.getTransform().setSize(textWidth + getPadRight(), textHeight + getPadTop() + getPadBottom());
        avatar.getParameters().size(avatarSize, avatarSize);
        avatar.getTransform().setPosition(getPadLeft(), - getPadTop());
        text.getTransform().setPosition(-textWidth - getPadRight(), -getPadTop());
        getTransform().setSize(totalWidth, totalHeight);
    }

    @Override
    public void load() {
        if (text == null)
            throw new IllegalStateException("text() must be called before load()");
        if (atlasAssetName == null)
            throw new IllegalStateException("avatar() must be called before load()");

        background = new TextureRegionObject(getAssets(), getCamera(), getWorld(), getPlayer());
        background.setRegion("textures/packed/Chop.atlas", "pixel-white");
        background.load();
        background.getTransform().setParent(getTransform());
        background.getTransform().setAlign(Align.TOP_RIGHT);
        background.getTransform().setOrigin(1, 1);
        background.setTint(backgroundTint);

        avatar = new TextureRegionObject(getAssets(), getCamera(), getWorld(), getPlayer());
        avatar.setRegion(atlasAssetName, avatarAssetName);
        avatar.load();
        avatar.getTransform().setParent(getTransform());
        avatar.getTransform().setAlign(Align.TOP_LEFT);
        avatar.getTransform().setOrigin(0, 1);

        text.create(fontName, this::getCurrentText, widthPx, com.badlogic.gdx.utils.Align.left, true);
        text.load();
        text.tint(textTint);
        text.pad(25, 25, 25, 25);
        text.getTransform().setParent(getTransform());
        text.getTransform().setAlign(Align.TOP_RIGHT);
        text.getTransform().setOrigin(0, 1);
    }

    @Override
    public void render(SpriteBatch batch) { }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        background.die();
        avatar.die();
        text.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { background, avatar, text };
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

    public DialogBoxObject textWidth(float widthPx) {
        this.widthPx = widthPx;
        return this;
    }

    public DialogBoxObject avatar(String atlasAssetName, String avatarAssetName) {
        this.atlasAssetName = atlasAssetName;
        this.avatarAssetName = avatarAssetName;
        return this;
    }

    public DialogBoxObject text(String fontName, Supplier<String> textSupplier, Color textTint) {
        this.fontName = fontName;
        this.textSupplier = textSupplier;
        this.textTint = textTint;
        charsToShow = textSupplier.get().length();
        text = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
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
