package fi.chop.model.object.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import fi.chop.engine.DrawParameters;
import fi.chop.model.object.GameObject;
import fi.chop.model.world.Player;
import fi.chop.util.FontRenderer;

import java.util.function.Supplier;

public class TextObject extends GameObject {

    public enum StyleType {
        CUSTOM, DISABLED, NORMAL, HOVER
    }

    private String fontName;
    private Supplier<String> textConstructor;
    private FontRenderer renderer;
    private TextObjectStyle customStyle;
    private TextObjectStyle disabledStyle;
    private TextObjectStyle normalStyle;
    private TextObjectStyle hoverStyle;
    private StyleType usedStyle;

    private OrthographicCamera cam;
    private SpriteBatch batch;
    private FrameBuffer fbo;
    private TextureRegion fboRegion;
    private DrawParameters params;

    private float paddingX;
    private float paddingY;
    private boolean dirty;
    private boolean disabled;

    public TextObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
        batch = new SpriteBatch();
        cam = new OrthographicCamera(0, 0);
        customStyle = new TextObjectStyle();
        disabledStyle = new TextObjectStyle().bgColor(Color.DARK_GRAY).tint(Color.GRAY);
        normalStyle = new TextObjectStyle();
        hoverStyle = new TextObjectStyle().bgColor(Color.FIREBRICK).tint(Color.YELLOW);
        usedStyle = StyleType.NORMAL;
    }

    public void create(String fontName, Supplier<String> textConstructor) {
        this.fontName = fontName;
        this.textConstructor = textConstructor;
    }

    public void pad(float paddingX, float paddingY) {
        this.paddingX = Math.max(paddingX, 0);
        this.paddingY = Math.max(paddingY, 0);
        dirty = true;
    }

    private void generateTexture() {
        if (fbo != null)
            fbo.dispose();
        createTexture(renderer);
        setSize(fboRegion.getRegionWidth(), fboRegion.getRegionHeight());
        params = new DrawParameters(fboRegion);
        dirty = false;
    }

    private void createTexture(FontRenderer textRenderer) {
        int width = Math.round(textRenderer.width());
        int height = Math.round(textRenderer.height());
        int totalWidth = Math.round(width + paddingX);
        int totalHeight = Math.round(height + paddingY);
        TextObjectStyle style = getUsedStyle();

        cam.setToOrtho(false, totalWidth, totalHeight);
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, totalWidth, totalHeight, false);
        fboRegion = new TextureRegion(fbo.getColorBufferTexture());
        fboRegion.flip(false, true);

        fbo.begin();
        Color bg = style.getBgColor();
        Gdx.gl.glClearColor(bg.r, bg.g, bg.b, bg.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        batch.setBlendFunction(-1, -1);
        Gdx.gl20.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE);

        if (style.getBgTexture() != null)
            batch.draw(style.getBgTexture(), 0, 0, totalWidth, totalHeight);

        textRenderer
                .center(cam, true, true)
                .translate(0, height)
                .tint(style.getTint())
                .draw(batch);
        batch.end();

        fbo.end();

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void load() {
        BitmapFont font = getAssets().get(fontName, BitmapFont.class);
        renderer = new FontRenderer(font);
    }

    @Override
    public void update(float delta) {
        if (renderer == null || textConstructor == null)
            return;

        String newText = textConstructor.get();
        if (!newText.equals(renderer.str())) {
            dirty = true;
            renderer.edit(newText);
        }

        if (dirty)
            generateTexture();
    }

    @Override
    public void render(SpriteBatch batch) {
        draw(batch, fboRegion, params);
    }

    @Override
    public void dispose() {
        batch.dispose();
        fbo.dispose();
    }

    public void tint(Color tint) {
        customStyle.tint(tint);
        if (usedStyle == StyleType.CUSTOM)
            dirty = true;
    }

    public void bgColor(Color bgColor) {
        customStyle.bgColor(bgColor);
        if (usedStyle == StyleType.CUSTOM)
            dirty = true;
    }

    public void bgTexture(TextureRegion bgTexture) {
        customStyle.bgTexture(bgTexture);
        if (usedStyle == StyleType.CUSTOM)
            dirty = true;
    }

    public void setStyle(StyleType type, TextObjectStyle style) {
        switch (type) {
            case CUSTOM:
                customStyle.set(style);
                break;
            case DISABLED:
                disabledStyle.set(style);
                break;
            case NORMAL:
                normalStyle.set(style);
                break;
            case HOVER:
                hoverStyle.set(style);
                break;
        }
        if (type == usedStyle)
            dirty = true;
    }

    public void useStyle(StyleType type) {
        if (disabled)
            return;
        if (usedStyle != type)
            dirty = true;
        usedStyle = type;
    }

    public TextObjectStyle getUsedStyle() {
        switch (usedStyle) {
            case CUSTOM:
                return customStyle;
            case DISABLED:
                return disabledStyle;
            case NORMAL:
                return normalStyle;
            case HOVER:
                return hoverStyle;
            default:
                throw new RuntimeException("Invalid TextObject style!");
        }
    }

    public void enable() {
        disabled = false;
    }

    public void disable() {
        disabled = true;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
