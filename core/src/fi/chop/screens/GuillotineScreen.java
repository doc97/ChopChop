package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.GuillotineScreenInput;
import fi.chop.model.fsm.states.guillotine.GuillotineStates;
import fi.chop.model.fsm.states.powermeter.PowerMeterStates;
import fi.chop.model.object.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GuillotineScreen extends ChopScreen implements EventListener {

    private PowerBarObject powerBar;
    private PowerMeterObject powerMeter;
    private GuillotineObject guillotine;
    private List<PersonObject> persons;
    private BitmapFont font;
    private float bestFill;

    public GuillotineScreen(Chop game) {
        super(game);
        persons = new ArrayList<>();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GuillotineScreenInput(this, getInputMap()));

        powerBar = new PowerBarObject(getAssets());
        powerBar.setPosition(getCamera().viewportWidth * 3 / 4, getCamera().viewportHeight / 2 - 200);
        powerMeter = new PowerMeterObject(getAssets());
        powerMeter.setPosition(powerBar.getX() + 100 + 10, powerBar.getY());
        guillotine = new GuillotineObject(getAssets());
        guillotine.setPosition(getCamera().viewportWidth / 4, 100);

        Chop.events.addListener(this,
                Events.ACTION_BACK, Events.ACTION_INTERACT,
                Events.EVT_GUILLOTINE_RAISED, Events.EVT_GUILLOTINE_RESTORED,
                Events.EVT_HEAD_CHOP);
        Chop.events.addListener(powerMeter, Events.EVT_GUILLOTINE_RAISED);

        powerBar.load();
        powerMeter.load();
        guillotine.load();

        newPerson();

        font = getAssets().get("fonts/ZCOOL-Regular.ttf", BitmapFont.class);
    }

    @Override
    public void hide() {
        Chop.events.clear();
    }

    @Override
    protected void update(float delta) {
        powerBar.update(delta);
        powerMeter.update(delta);
        guillotine.update(delta);
        for (Iterator<PersonObject> it = persons.iterator(); it.hasNext();) {
            PersonObject person = it.next();
            person.update(delta);
            if (person.getX() < person.getWidth())
                it.remove();
        }
    }

    @Override
    protected void render(SpriteBatch batch) {
        beginRender();
        batch.begin();
        powerBar.render(batch);
        powerMeter.render(batch);
        guillotine.render(batch);
        for (PersonObject p : persons)
            p.render(batch);

        drawGUI(batch);
        batch.end();
    }

    private void drawGUI(SpriteBatch batch) {
        float bestX = 50;
        float bestY = getCamera().viewportHeight - 50;
        float killX = 50;
        float killY = getCamera().viewportHeight - 50 - font.getLineHeight();

        drawBestPercent(batch, bestX, bestY);
        drawKillStats(batch, killX, killY);
    }

    private void drawBestPercent(SpriteBatch batch, float x, float y) {
        String percentStr = String.format("%.1f", bestFill * 100);
        font.draw(batch, "Best: " + percentStr + "%", x, y);
    }

    private void drawKillStats(SpriteBatch batch, float x, float y) {
        font.draw(batch, "Kills: " + getStats().getDailyKills(), x, y);
    }

    private void newPerson() {
        PersonObject person = new PersonObject(getAssets());
        person.setPosition(guillotine.getX() + 150, guillotine.getY() + 125);
        person.load();
        Chop.events.addListener(Events.EVT_HEAD_CHOP, person);
        persons.add(person);
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
                float fill = powerMeter.getMeterFillPercentage();
                if (fill > bestFill)
                    bestFill = fill;
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
