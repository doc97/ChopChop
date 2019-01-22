package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.effect.ColorFade;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.ExecutionScreenInput;
import fi.chop.model.fsm.states.guillotine.GuillotineStates;
import fi.chop.model.fsm.states.powermeter.PowerMeterStates;
import fi.chop.model.object.*;
import fi.chop.timer.GameTimer;
import fi.chop.util.FontRenderer;
import fi.chop.util.MathUtil;

public class ExecutionScreen extends ChopScreen implements EventListener {

    private static final float FADEOUT_START_DELAY_SEC = 1.5f;
    private static final float POPULARITY_DELTA = 0.05f;
    private static final float REPUTATION_DELTA = 0.05f;

    private FontRenderer powerText;
    private FontRenderer killText;
    private FontRenderer timeText;
    private ColorFade fadeOut;
    private GameTimer.DelayedAction timer;

    private boolean isPowerMeterIdle;
    private boolean isGuillotineIdle;

    public ExecutionScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        loadAssets();
        initializeScreen();

        createScene();
        initializeScene();
        initializeEventListener();
    }

    private void initializeScreen() {
        Gdx.input.setInputProcessor(new ExecutionScreenInput(this, getInputMap()));
        timer = Chop.timer.addAction(60, this::endDay);

        isGuillotineIdle = true;
        isPowerMeterIdle = true;
    }

    private void loadAssets() {
        BitmapFont font = getAssets().get("ZCOOL-40.ttf", BitmapFont.class);
        powerText = new FontRenderer(font);
        killText = new FontRenderer(font);
        timeText = new FontRenderer(font);
    }

    private void createScene() {
        getScene().addLayer("Background", new Layer());
        getScene().addLayer("Guillotine", new Layer());
        getScene().addLayer("Heads", new Layer());
        getScene().addLayer("UI", new Layer());
    }

    private void initializeScene() {
        GameObject popMeter = new PopularityMeterObject(getAssets(), getCamera());
        popMeter.setOrigin(1, 1);
        popMeter.setPosition(getCamera().viewportWidth - 50, getCamera().viewportHeight - 50);
        popMeter.load();

        GameObject repMeter = new ReputationMeterObject(getAssets(), getCamera());
        repMeter.setOrigin(1, 1);
        repMeter.setPosition(getCamera().viewportWidth - 50, getCamera().viewportHeight - 125);
        repMeter.load();

        GameObject powerBar = new PowerBarObject(getAssets(), getCamera());
        powerBar.setOrigin(0.5f, 0.5f);
        powerBar.setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight / 2);
        powerBar.load();

        GameObject powerMeter = new PowerMeterObject(getAssets(), getCamera());
        powerMeter.setOrigin(0, 0.5f);
        powerMeter.setPosition(powerBar.getX() + powerBar.getWidth() / 2 + 10, powerBar.getY());
        powerMeter.load();

        GameObject guillotine = new GuillotineObject(getAssets(), getCamera());
        guillotine.setOrigin(0.5f, 0);
        guillotine.setPosition(getCamera().viewportWidth / 4, 100);
        guillotine.load();

        GameObject person = new PersonObject(getAssets(), getCamera());
        person.setOrigin(0.5f, 0.5f);
        person.setPosition(guillotine.getX(), guillotine.getY() + 125);
        person.load();

        ScrollObject scroll = new ScrollObject(getAssets(), getCamera());
        scroll.setOrigin(0.5f, 0.5f);
        scroll.setPosition(getCamera().viewportWidth * 4 / 5, getCamera().viewportHeight / 2);
        scroll.load();
        scroll.setExecution(getWorld().getExecution());

        Chop.events.addListener(popMeter, Events.EVT_POPULARITY_CHANGED);
        Chop.events.addListener(repMeter, Events.EVT_REPUTATION_CHANGED, Events.EVT_REPUTATION_LVL_CHANGED);
        Chop.events.addListener(powerMeter, Events.EVT_GUILLOTINE_RAISE, Events.EVT_GUILLOTINE_PREPARED);
        Chop.events.addListener(guillotine, Events.EVT_GUILLOTINE_RAISE);
        Chop.events.addListener(person, Events.ACTION_MERCY, Events.EVT_PERSON_KILLED);

        // Initialize meters
        Chop.events.notify(Events.EVT_POPULARITY_CHANGED, new EventData<>(getPlayer().getPopularity()));
        Chop.events.notify(Events.EVT_REPUTATION_CHANGED, new EventData<>(getPlayer().getReputation()));
        Chop.events.notify(Events.EVT_REPUTATION_LVL_CHANGED, new EventData<>(getPlayer().getReputationLevel()));

        getScene().addObjects("Heads", person);
        getScene().addObjects("Guillotine", guillotine);
        getScene().addObjects("UI", popMeter, repMeter, powerBar, powerMeter, scroll);
        getScene().addQueued();
    }

    private void initializeEventListener() {
        Chop.events.addListener(this,
                Events.ACTION_BACK, Events.ACTION_INTERACT,
                Events.EVT_GUILLOTINE_PREPARED, Events.EVT_GUILLOTINE_RESTORED,
                Events.EVT_PERSON_SAVED, Events.EVT_PERSON_KILLED,
                Events.EVT_GUILLOTINE_STATE_CHANGED, Events.EVT_POWERMETER_STATE_CHANGED
                );
    }

    @Override
    public void hide() {
        super.hide();
        getStats().resetDailyKills();
    }

    @Override
    protected void update(float delta) {
        getScene().update(delta);

        if (fadeOut != null)
            fadeOut.update(delta);
    }

    @Override
    protected void render(SpriteBatch batch) {
        if (fadeOut != null)
            batch.setColor(fadeOut.getColor());
        getScene().render(batch);
        drawGUI(batch);
        batch.setColor(Color.WHITE);
    }

    private void drawGUI(SpriteBatch batch) {
        drawHighestPower(batch);
        drawKillStats(batch);
        drawDayStats(batch);
    }

    private void drawHighestPower(SpriteBatch batch) {
        String percentStr = String.format("%.1f", getStats().getHighestPower() * 100);
        powerText
                .text("Best: " + percentStr + "%")
                .pos(50, getCamera().viewportHeight - 50)
                .draw(batch);
    }

    private void drawKillStats(SpriteBatch batch) {
        killText
                .text("Daily kills: " + getStats().getDailyKills())
                .pos(50, getCamera().viewportHeight - 50 - 1.5f * killText.getLineHeight())
                .draw(batch);
    }

    private void drawDayStats(SpriteBatch batch) {
        timeText
                .text("Time left: " + String.format("%.0f", Math.max(timer.time, 0)))
                .center(getCamera(), true, false)
                .y(getCamera().viewportHeight - 10)
                .draw(batch);
    }

    private void endDay() {
        if (fadeOut == null) {
            fadeOut = new ColorFade(Color.WHITE, Color.BLACK, 2.0f, (t) -> MathUtil.smoothStartN(t, 2))
                    .onFinish(() -> Chop.timer.addAction(1.0f, () -> setScreen(Screens.TOWN)));
        }
    }

    private void exitGame() {
        if (fadeOut == null) {
            fadeOut = new ColorFade(Color.WHITE, Color.BLACK, 1.0f, (t) -> MathUtil.smoothStartN(t, 2))
                    .onFinish(() -> Chop.timer.addAction(0.5f, () -> setScreen(Screens.MAIN_MENU)));
        }
    }

    private void updatePlayerStats(boolean wasCorrect, boolean wasKill) {
        getPlayer().addPopularity(wasCorrect ? POPULARITY_DELTA : -POPULARITY_DELTA);
        getPlayer().addReputation(wasKill ? REPUTATION_DELTA : -REPUTATION_DELTA);
    }

    @Override
    public void handle(Events event, EventData data) {
        switch (event) {
            case ACTION_INTERACT:
                if (isPowerMeterIdle && isGuillotineIdle) {
                    float value = getScene().findOne(PowerBarObject.class).getValue();
                    Chop.events.notify(Events.EVT_GUILLOTINE_RAISE, new EventData<>(value));
                }
                break;
            case ACTION_BACK:
                exitGame();
                break;
            case EVT_GUILLOTINE_STATE_CHANGED:
                isGuillotineIdle = data.get() == GuillotineStates.IDLE;
                break;
            case EVT_POWERMETER_STATE_CHANGED:
                isPowerMeterIdle = data.get() == PowerMeterStates.IDLE;
                break;
            case EVT_GUILLOTINE_PREPARED:
                PowerMeterObject meter = getScene().findOne(PowerMeterObject.class);
                float power = meter.getMeterFillPercentage();
                getStats().registerPower(power);
                break;
            case EVT_GUILLOTINE_RESTORED:
                endDay();
                break;
            case EVT_PERSON_SAVED:
                Chop.timer.addAction(FADEOUT_START_DELAY_SEC, this::endDay);
                updatePlayerStats(!getWorld().getExecution().isFairPunishment(), false);
                break;
            case EVT_PERSON_KILLED:
                getStats().addDailyKill();
                updatePlayerStats(getWorld().getExecution().isFairPunishment(), true);
                break;
            default:
                break;
        }
    }
}
