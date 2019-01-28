package fi.chop.model.object.gui;

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
import com.badlogic.gdx.utils.Align;
import fi.chop.engine.DrawParameters;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;
import fi.chop.util.FontRenderer;

import java.util.function.Supplier;

public class TextObject extends GUIObject {

    private static final int AUTO_PAD_X = 10;
    private static final int AUTO_PAD_Y = 10;

    private boolean wrap;
    private float widthPx;
    private int hAlign;
    private String fontName;
    private Supplier<String> textConstructor;
    private FontRenderer renderer;
    private TextButtonStyle style;

    private OrthographicCamera cam;
    private SpriteBatch batch;
    private FrameBuffer fbo;
    private TextureRegion fboRegion;
    private DrawParameters params;

    public TextObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        batch = new SpriteBatch();
        cam = new OrthographicCamera(0, 0);
        style = new TextButtonStyle();
    }

    @Override
    public void pack() {
        generateTexture();
    }

    public void create(String fontName, Supplier<String> textConstructor) {
        create(fontName, textConstructor, 0, Align.left, false);
    }

    public void create(String fontName, Supplier<String> textConstructor, float widthPx, int hAlign, boolean wrap) {
        this.fontName = fontName;
        this.textConstructor = textConstructor;
        this.widthPx = widthPx;
        this.hAlign = hAlign;
        this.wrap = wrap;
    }

    private void generateTexture() {
        if (fbo != null)
            fbo.dispose();
        createTexture(renderer);
        getTransform().setSize(fboRegion.getRegionWidth(), fboRegion.getRegionHeight());
        params = new DrawParameters(fboRegion);
    }

    private void createTexture(FontRenderer textRenderer) {
        int width = Math.round(textRenderer.width());
        int height = Math.round(textRenderer.height());
        int totalWidth = Math.round(width + AUTO_PAD_X + getPadLeft() + getPadRight());
        int totalHeight = Math.round(height + AUTO_PAD_Y + getPadTop() + getPadBottom());

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
        renderer.text(textConstructor.get(), widthPx, hAlign, wrap);
        invalidate();
    }

    @Override
    public void update(float delta) {
        if (renderer == null || textConstructor == null)
            throw new IllegalStateException("create() and load() must be called before update()!");

        String newText = textConstructor.get();
        if (!newText.equals(renderer.str())) {
            invalidate();
            renderer.edit(newText);
        }

        super.update(delta);
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
        style.tint(tint);
        invalidate();
    }

    public void bgColor(Color bgColor) {
        style.bgColor(bgColor);
        invalidate();
    }

    public void bgTexture(TextureRegion bgTexture) {
        style.bgTexture(bgTexture);
        invalidate();
    }

    public void style(TextButtonStyle style) {
        this.style.set(style);
        invalidate();
    }
}
