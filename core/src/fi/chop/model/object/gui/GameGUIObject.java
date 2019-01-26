package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.util.TextObject;
import fi.chop.model.world.Player;

public class GameGUIObject extends GameObject {

    private TextObject money;
    private PerkObject perks;
    private PopularityMeterObject popularity;
    private ReputationMeterObject reputation;

    public GameGUIObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
    }

    @Override
    public void load() {
        getTransform().setSize(getCamera().viewportWidth, getCamera().viewportHeight);

        money = new TextObject(getAssets(), getCamera(), getPlayer());
        money.getTransform().setParent(getTransform());
        money.getTransform().setOrigin(0, 1);
        money.getTransform().setPosition(50, -50);
        money.setAlign(Align.TOP_LEFT);
        money.pad(10, 10);
        money.create("ZCOOL-40.ttf", () -> "Money: " + getPlayer().getMoney() + " gold");
        money.load();

        perks = new PerkObject(getAssets(), getCamera(), getPlayer());
        perks.getTransform().setParent(getTransform());
        perks.getTransform().setOrigin(0, 1);
        perks.getTransform().setPosition(50, -100);//money.getTransform().getBottom() - 15);
        perks.setAlign(Align.TOP_LEFT);
        perks.load();

        popularity = new PopularityMeterObject(getAssets(), getCamera(), getPlayer());
        popularity.getTransform().setParent(getTransform());
        popularity.getTransform().setOrigin(1, 1);
        popularity.getTransform().setPosition(-50, -50);
        popularity.setAlign(Align.TOP_RIGHT);
        popularity.load();

        reputation = new ReputationMeterObject(getAssets(), getCamera(), getPlayer());
        reputation.getTransform().setParent(getTransform());
        reputation.getTransform().setOrigin(1, 1);
        reputation.getTransform().setPosition(-50, popularity.getTransform().getBottom() - 15);
        reputation.setAlign(Align.TOP_RIGHT);
        reputation.load();

        Chop.events.addListener(popularity, Events.EVT_POPULARITY_CHANGED);
        Chop.events.addListener(reputation, Events.EVT_REPUTATION_CHANGED, Events.EVT_REPUTATION_LVL_CHANGED);

        // Initialize meters
        Chop.events.notify(Events.EVT_POPULARITY_CHANGED, new EventData<>(getPlayer().getPopularity()));
        Chop.events.notify(Events.EVT_REPUTATION_CHANGED, new EventData<>(getPlayer().getReputation()));
        Chop.events.notify(Events.EVT_REPUTATION_LVL_CHANGED, new EventData<>(getPlayer().getReputationLevel()));
    }

    @Override
    public void update(float delta) { }

    @Override
    public void render(SpriteBatch batch) { }

    @Override
    public void dispose() { }

    @Override
    public void die() {
        super.die();
        money.die();
        perks.die();
        popularity.die();
        reputation.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { money, perks, popularity, reputation };
    }
}
