package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.effect.ColorFade;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.MainMenuScreenInput;
import fi.chop.model.world.PopularityPerk;
import fi.chop.util.FontRenderer;
import fi.chop.util.MathUtil;

public class MainMenuScreen extends ChopScreen implements EventListener {

    private ColorFade fade;
    private FontRenderer instructionText;

    public MainMenuScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new MainMenuScreenInput(this, getInputMap()));
        Chop.events.addListener(this, Events.ACTION_EXIT, Events.ACTION_INTERACT);

        getWorld().reset();

        BitmapFont font = getAssets().get("ZCOOL-40.ttf", BitmapFont.class);
        instructionText = new FontRenderer(font);
        instructionText.text("Press [Space] to start");

        fade = new ColorFade(Color.WHITE, Color.BLACK, 1.5f,
                (t) -> MathUtil.smoothStartN(t, 2));
    }

    @Override
    protected void update(float delta) {
        fade.update(delta);
        if (fade.hasFinished())
            fade.flip();
    }

    @Override
    protected void render(SpriteBatch batch) {
        batch.setColor(fade.getColor());
        instructionText
                .center(getCamera(), true, true)
                .draw(batch);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.ACTION_INTERACT) {
            getPlayer().randomizePopularityPerks();
            getPlayer().addPerks(PopularityPerk.values());
            getWorld().nextDay();
            setScreen(Screens.TOWN);
            Chop.events.notify(Events.EVT_NEW_GAME);
        } else if (event == Events.ACTION_EXIT) {
            Gdx.app.exit();
        }
    }
}
