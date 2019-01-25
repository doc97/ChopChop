package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.util.TextObject;
import fi.chop.model.world.Player;

public class GameGUIObject extends GameObject {

    private TextObject money;
    private PopularityMeterObject popularity;
    private ReputationMeterObject reputation;

    public GameGUIObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
    }

    @Override
    public void load() {
        money = new TextObject(getAssets(), getCamera(), getPlayer());
        money.getTransform().setOrigin(0, 1);
        money.getTransform().setPosition(50, getCamera().viewportHeight - 50);
        money.pad(10, 10);
        money.create("ZCOOL-40.ttf", () -> "Money: " + getPlayer().getMoney() + " gold");
        money.load();

        popularity = new PopularityMeterObject(getAssets(), getCamera(), getPlayer());
        popularity.getTransform().setOrigin(1, 1);
        popularity.getTransform().setPosition(getCamera().viewportWidth - 50, getCamera().viewportHeight - 50);
        popularity.load();

        reputation = new ReputationMeterObject(getAssets(), getCamera(), getPlayer());
        reputation.getTransform().setOrigin(1, 1);
        reputation.getTransform().setPosition(getCamera().viewportWidth - 50, getCamera().viewportHeight - 125);
        reputation.load();

        Chop.events.addListener(popularity, Events.EVT_POPULARITY_CHANGED);
        Chop.events.addListener(reputation, Events.EVT_REPUTATION_CHANGED, Events.EVT_REPUTATION_LVL_CHANGED);

        // Initialize meters
        Chop.events.notify(Events.EVT_POPULARITY_CHANGED, new EventData<>(getPlayer().getPopularity()));
        Chop.events.notify(Events.EVT_REPUTATION_CHANGED, new EventData<>(getPlayer().getReputation()));
        Chop.events.notify(Events.EVT_REPUTATION_LVL_CHANGED, new EventData<>(getPlayer().getReputationLevel()));
    }

    @Override
    public void update(float delta) {
        money.update(delta);
        popularity.update(delta);
        reputation.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        money.render(batch);
        popularity.render(batch);
        reputation.render(batch);
    }

    @Override
    public void dispose() {
        Chop.events.removeListener(money);
        Chop.events.removeListener(popularity);
        Chop.events.removeListener(reputation);
        money.dispose();
        popularity.dispose();
        reputation.dispose();
    }
}
