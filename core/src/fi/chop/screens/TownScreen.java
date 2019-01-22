package fi.chop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.engine.Layer;
import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.Events;
import fi.chop.input.TouchHandler;
import fi.chop.input.TownScreenInput;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.TextObject;

public class TownScreen extends ChopScreen implements EventListener {

    public TownScreen(Chop game) {
        super(game);
    }

    @Override
    public void show() {
        registerEventListener();
        initializeScreen();
        initializeScene();

        if (getWorld().getDay() % 7 == 0)
            getPlayer().addMoney(-getWorld().getTaxes());
    }

    private void registerEventListener() {
        Chop.events.addListener(this, Events.ACTION_BACK, Events.ACTION_INTERACT);
    }

    private void initializeScreen() {
        Gdx.input.setInputProcessor(new TownScreenInput(this, getInputMap()));
    }

    private void initializeScene() {
        getScene().addLayer("Background", new Layer());
        getScene().addLayer("Text", new Layer());

        TextObject moneyText = new TextObject(getAssets(), getCamera());
        moneyText.setOrigin(0, 1);
        moneyText.setPosition(50, getCamera().viewportHeight - 50);
        moneyText.pad(5, 5);
        moneyText.create("ZCOOL-40.ttf", () -> "Money: " + getPlayer().getMoney());
        moneyText.load();

        TextObject dayText = new TextObject(getAssets(), getCamera());
        dayText.setOrigin(0.5f, 1);
        dayText.setPosition(getCamera().viewportWidth / 2, getCamera().viewportHeight - 50);
        dayText.create("ZCOOL-60.ttf", () -> "DAY " + getWorld().getDay());
        dayText.load();

        TextObject castleText = new TextObject(getAssets(), getCamera());
        castleText.setOrigin(0.5f, 1);
        castleText.setPosition(getCamera().viewportWidth / 2 - 150, getCamera().viewportHeight - 200);
        castleText.create("ZCOOL-40.ttf", () -> "Castle");
        castleText.pad(50, 50);
        castleText.load();
        castleText.setTouchable(true);
        castleText.setTouchHandler(new TouchHandler() {
            @Override
            public void touchDown(GameObject obj, float worldX, float worldY, int pointer, int button) {
                Gdx.app.log("Castle", "Go!");
            }

            @Override
            public void enter(GameObject obj, float worldX, float worldY) {
                TextObject txt = (TextObject) obj;
                txt.tint(Color.YELLOW);
                txt.background(Color.FIREBRICK);
            }

            @Override
            public void exit(GameObject obj, float worldX, float worldY) {
                TextObject txt = (TextObject) obj;
                txt.tint(Color.WHITE);
                txt.background(null);
            }
        });

        TextObject tavernText = new TextObject(getAssets(), getCamera());
        tavernText.setOrigin(0, 1);
        tavernText.setPosition(200, 300);
        tavernText.create("ZCOOL-40.ttf", () -> "Tavern");
        tavernText.pad(50, 50);
        tavernText.load();
        tavernText.setTouchable(true);
        tavernText.setTouchHandler(new TouchHandler() {
            @Override
            public void touchDown(GameObject obj, float worldX, float worldY, int pointer, int button) {
                Gdx.app.log("Tavern", "Go!");
            }

            @Override
            public void enter(GameObject obj, float worldX, float worldY) {
                TextObject txt = (TextObject) obj;
                txt.tint(Color.YELLOW);
                txt.background(Color.FIREBRICK);
            }

            @Override
            public void exit(GameObject obj, float worldX, float worldY) {
                TextObject txt = (TextObject) obj;
                txt.tint(Color.WHITE);
                txt.background(null);
            }
        });

        TextObject guillotineText = new TextObject(getAssets(), getCamera());
        guillotineText.setOrigin(1, 1);
        guillotineText.setPosition(getCamera().viewportWidth - 200, 500);
        guillotineText.create("ZCOOL-40.ttf", () -> "Guillotine");
        guillotineText.pad(50, 50);
        guillotineText.load();
        guillotineText.setTouchable(true);
        guillotineText.setTouchHandler(new TouchHandler() {
            @Override
            public void touchDown(GameObject obj, float worldX, float worldY, int pointer, int button) {
                setScreen(Screens.EXECUTION);
            }

            @Override
            public void enter(GameObject obj, float worldX, float worldY) {
                TextObject txt = (TextObject) obj;
                txt.tint(Color.YELLOW);
                txt.background(Color.FIREBRICK);
            }

            @Override
            public void exit(GameObject obj, float worldX, float worldY) {
                TextObject txt = (TextObject) obj;
                txt.tint(Color.WHITE);
                txt.background(null);
            }
        });

        getScene().addObjects("Text", moneyText, dayText, castleText, tavernText, guillotineText);
        getScene().addQueued();
    }

    @Override
    protected void update(float delta) {
        getScene().update(delta);
    }

    @Override
    protected void render(SpriteBatch batch) {
        getScene().render(batch);
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.ACTION_BACK) {
            getWorld().reset();
            setScreen(Screens.MAIN_MENU);
        } else if (event == Events.ACTION_INTERACT) {
            setScreen(Screens.EXECUTION);
        }
    }
}
