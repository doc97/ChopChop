package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.GuillotineScreenInput;
import fi.chop.model.Layer;
import fi.chop.model.Scene;
import fi.chop.model.fsm.states.guillotine.GuillotineStates;
import fi.chop.model.fsm.states.powermeter.PowerMeterStates;
import fi.chop.model.object.GuillotineObject;
import fi.chop.model.object.PersonObject;
import fi.chop.model.object.PowerBarObject;
import fi.chop.model.object.PowerMeterObject;
import fi.chop.util.DrawUtil;

public class GuillotineScreen extends ChopScreen implements EventListener {

    private Scene scene;
    private PowerBarObject powerBar;
    private PowerMeterObject powerMeter;
    private GuillotineObject guillotine;
    private BitmapFont font;
    private float leftOfDaySec;
    private int day;

    private static final float NEW_PERSON_DELAY_SEC = 2;
    private float newPersonTimer;

    public GuillotineScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GuillotineScreenInput(this, getInputMap()));

        scene = new Scene();
        scene.addLayer("Background", new Layer());
        scene.addLayer("Guillotine", new Layer());
        scene.addLayer("Heads", new Layer());
        scene.addLayer("UI", new Layer());

        powerBar = new PowerBarObject(getAssets(), getCamera());
        powerBar.setPosition(getCamera().viewportWidth * 3 / 4, getCamera().viewportHeight / 2 - 200);
        powerMeter = new PowerMeterObject(getAssets(), getCamera());
        powerMeter.setPosition(powerBar.getX() + 100 + 10, powerBar.getY());
        guillotine = new GuillotineObject(getAssets(), getCamera());
        guillotine.setPosition(getCamera().viewportWidth / 4, 100);

        Chop.events.addListener(this,
                Events.ACTION_BACK, Events.ACTION_INTERACT, Events.ACTION_MERCY,
                Events.EVT_GUILLOTINE_RAISED, Events.EVT_GUILLOTINE_RESTORED,
                Events.EVT_HEAD_CHOP);
        Chop.events.addListener(powerMeter, Events.EVT_GUILLOTINE_RAISED);

        powerBar.load();
        powerMeter.load();
        guillotine.load();

        scene.addObjects("Guillotine", guillotine);
        scene.addObjects("UI", powerBar, powerMeter);

        newDay();
        newPerson();

        newPersonTimer = -1;

        font = getAssets().get("fonts/ZCOOL-Regular.ttf", BitmapFont.class);
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

        if (newPersonTimer > 0) {
            newPersonTimer -= delta;
            if (newPersonTimer <= 0) {
                newPerson();
            }
        }
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
        person.setPosition(guillotine.getX() + 150, guillotine.getY() + 125);
        person.load();
        Chop.events.addListener(person, Events.EVT_HEAD_CHOP, Events.EVT_PERSON_SAVED);
        scene.addObjects("Heads", person);
    }

    private void newDay() {
        getStats().resetDailyKills();
        leftOfDaySec = 60;
        day++;
    }

    @Override
    public void handle(Events event, EventData data) {
        switch (event) {
            case ACTION_MERCY:
                Chop.events.notify(Events.EVT_PERSON_SAVED);
                newPersonTimer = NEW_PERSON_DELAY_SEC;
                break;
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
            case EVT_HEAD_CHOP:
                getStats().addDailyKill();
            default:
                break;
        }
    }
}
