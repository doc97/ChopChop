package fi.chop.model.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import fi.chop.engine.DrawParameters;
import fi.chop.util.FontRenderer;

public class TextObject extends GameObject {

    public interface TextConstructor {
        String construct();
    }

    private String fontName;
    private String text;
    private TextConstructor textConstructor;
    private FontRenderer renderer;

    private OrthographicCamera cam;
    private SpriteBatch batch;
    private FrameBuffer fbo;
    private TextureRegion fboRegion;
    private DrawParameters params;

    public TextObject(AssetManager assets, OrthographicCamera camera) {
        super(assets, camera);
        batch = new SpriteBatch();
        cam = new OrthographicCamera(0, 0);
    }

    public void create(String fontName, TextConstructor textConstructor) {
        this.fontName = fontName;
        this.textConstructor = textConstructor;
    }

    private void generateTexture(String text) {
        if (fbo != null)
            fbo.dispose();
        createTexture(renderer.text(text));
        setSize(fboRegion.getRegionWidth(), fboRegion.getRegionHeight());
        params = new DrawParameters(fboRegion);
    }

    private void createTexture(FontRenderer text) {
        int width = Math.round(text.width());
        int height = Math.round(text.height());

        cam.setToOrtho(false, width, height);
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        fboRegion = new TextureRegion(fbo.getColorBufferTexture());
        fboRegion.flip(false, true);

        fbo.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        batch.setBlendFunction(-1, -1);
        Gdx.gl20.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE);

        text.y(height).draw(batch);
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
        String newText = textConstructor.construct();
        if (!newText.equals(text)) {
            this.text = newText;
            generateTexture(newText);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        draw(batch, fboRegion, params);
    }
}
