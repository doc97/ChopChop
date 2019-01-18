package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.engine.Scene;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.GuillotineScreenInput;
import fi.chop.model.fsm.states.guillotine.GuillotineStates;
import fi.chop.model.fsm.states.powermeter.PowerMeterStates;
import fi.chop.model.object.*;
import fi.chop.model.world.Execution;
import fi.chop.model.world.SocialStatus;
import fi.chop.model.world.Victim;
import fi.chop.util.DrawUtil;

import java.util.Random;

public class ExecutionScreen extends ChopScreen implements EventListener {

    private static final float NEW_PERSON_DELAY_SEC = 2;

    private Scene scene;
    private Execution execution;
    private ScrollObject scroll;
    private PowerBarObject powerBar;
    private PowerMeterObject powerMeter;
    private GuillotineObject guillotine;
    private BitmapFont font;
    private float leftOfDaySec;
    private int day;

    public ExecutionScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GuillotineScreenInput(this, getInputMap()));

        createScene();
        initializeScene();

        newDay();
        newPerson();
        newExecution();

        font = getAssets().get("ZCOOL-40.ttf", BitmapFont.class);
    }

    private void createScene() {
        scene = new Scene();
        scene.addLayer("Background", new Layer());
        scene.addLayer("Guillotine", new Layer());
        scene.addLayer("Heads", new Layer());
        scene.addLayer("UI", new Layer());
    }

    private void initializeScene() {
        PopularityMeterObject popMeter = new PopularityMeterObject(getAssets(), getCamera());
        popMeter.setOrigin(1, 1);
        popMeter.setPosition(getCamera().viewportWidth - 50, getCamera().viewportHeight - 50);
        popMeter.load();

        ReputationMeterObject repMeter = new ReputationMeterObject(getAssets(), getCamera());
        repMeter.setOrigin(1, 1);
        repMeter.setPosition(getCamera().viewportWidth - 50, getCamera().viewportHeight - 125);
        repMeter.load();

        powerBar = new PowerBarObject(getAssets(), getCamera());
        powerBar.setOrigin(0.5f, 0.5f);
        powerBar.setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight / 2);
        powerBar.load();

        powerMeter = new PowerMeterObject(getAssets(), getCamera());
        powerMeter.setOrigin(0, 0.5f);
        powerMeter.setPosition(powerBar.getX() + powerBar.getWidth() / 2 + 10, powerBar.getY());
        powerMeter.load();

        guillotine = new GuillotineObject(getAssets(), getCamera());
        guillotine.setOrigin(0.5f, 0);
        guillotine.setPosition(getCamera().viewportWidth / 4, 100);
        guillotine.load();

        scroll = new ScrollObject(getAssets(), getCamera());
        scroll.setOrigin(0.5f, 0.5f);
        scroll.setPosition(getCamera().viewportWidth * 4 / 5, getCamera().viewportHeight / 2);
        scroll.load();

        Chop.events.addListener(this,
                Events.ACTION_BACK, Events.ACTION_INTERACT,
                Events.EVT_GUILLOTINE_RAISED, Events.EVT_GUILLOTINE_RESTORED,
                Events.EVT_PERSON_SAVED, Events.EVT_HEAD_CHOP);
        Chop.events.addListener(popMeter, Events.EVT_POPULARITY_CHANGED);
        Chop.events.addListener(repMeter, Events.EVT_REPUTATION_CHANGED, Events.EVT_REPUTATION_LVL_CHANGED);
        Chop.events.addListener(powerMeter, Events.EVT_GUILLOTINE_RAISED);

        scene.addObjects("Guillotine", guillotine);
        scene.addObjects("UI", popMeter, repMeter, powerBar, powerMeter, scroll);
    }

    @Override
    public void hide() {
        Chop.events.clear();
    }

    @Override
    protected void update(float delta) {
        scene.update(delta);

        leftOfDaySec -= delta;
        if (leftOfDaySec <= 0)
            newDay();
    }

    @Override
    protected void render(SpriteBatch batch) {
        beginRender();
        batch.begin();
        scene.render(batch);
        drawGUI(batch);
        batch.end();
    }

    private void drawGUI(SpriteBatch batch) {
        float bestX = 50;
        float bestY = getCamera().viewportHeight - 50;
        float killX = 50;
        float killY = getCamera().viewportHeight - 50 - 1.5f * font.getLineHeight();

        drawHighestPower(batch, bestX, bestY);
        drawKillStats(batch, killX, killY);
        drawDayStats(batch);
    }

    private void drawHighestPower(SpriteBatch batch, float x, float y) {
        String percentStr = String.format("%.1f", getStats().getHighestPower() * 100);
        font.draw(batch, "Best: " + percentStr + "%", x, y);
    }

    private void drawKillStats(SpriteBatch batch, float x, float y) {
        font.draw(batch, "Daily kills: " + getStats().getDailyKills(), x, y);
    }

    private void drawDayStats(SpriteBatch batch) {
        float centerX = getCamera().viewportWidth / 2;
        float y0 = getCamera().viewportHeight - 10;
        float y1 = y0 - 2 * font.getLineHeight();
        String timeText = "Time left: " + String.format("%.0f", Math.max(leftOfDaySec, 0));
        DrawUtil.drawCenteredText(batch, font, "DAY " + day, Color.WHITE, centerX, y0);
        DrawUtil.drawCenteredText(batch, font, timeText, Color.WHITE, centerX, y1);
    }

    private void newPerson() {
        PersonObject person = new PersonObject(getAssets(), getCamera());
        person.setOrigin(0.5f, 0.5f);
        person.setPosition(guillotine.getX(), guillotine.getY() + 125);
        person.load();
        Chop.events.addListener(person, Events.ACTION_MERCY, Events.EVT_HEAD_CHOP);
        scene.addObjects("Heads", person);
    }

    private void newExecution() {
        Victim victim = new Victim("John Doe", SocialStatus.NOBILITY);
        execution = new Execution(victim, "Plotting against the revolution");
        execution.setFairPunishment(new Random().nextBoolean());
        scroll.setExecution(execution);
    }

    private void newDay() {
        getStats().resetDailyKills();
        leftOfDaySec = 60;
        day++;
    }

    @Override
    public void handle(Events event, EventData data) {
        switch (event) {
            case ACTION_INTERACT:
                boolean isMeterIdle = powerMeter.getState() == PowerMeterStates.IDLE;
                boolean isGuillotineIdle = guillotine.getState() == GuillotineStates.IDLE;
                if (isMeterIdle && isGuillotineIdle) {
                    powerMeter.addPower(powerBar.getValue());
                    guillotine.raiseBlade(powerBar.getValue());
                }
                break;
            case ACTION_BACK:
                Gdx.app.exit();
                break;
            case EVT_GUILLOTINE_RAISED:
                float power = powerMeter.getMeterFillPercentage();
                getStats().registerPower(power);
                break;
            case EVT_GUILLOTINE_RESTORED:
                newPerson();
                break;
            case EVT_PERSON_SAVED:
                getPlayer().addPopularity(0.15f);
                Chop.timer.addAction(NEW_PERSON_DELAY_SEC, this::newPerson);
                break;
            case EVT_HEAD_CHOP:
                getStats().addDailyKill();
                getPlayer().addReputation(0.05f);
                getPlayer().addPopularity(-0.05f);
                break;
            default:
                break;
        }
    }
}
