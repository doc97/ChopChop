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
import fi.chop.model.object.util.ValueMeterObject;
import fi.chop.model.world.Player;

public class GameGUIObject extends GUIObject {

    private TextObject money;
    private PerkObject perks;
    private PopularityMeterObject popularity;
    private ReputationMeterObject reputation;

    public GameGUIObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player);
        pad(30, 30, 30, 30);
    }

    @Override
    public void pack() {
        perks.getTransform().setPosition(0, money.getTransform().getBottom() - 15);
        reputation.getTransform().setPosition(0, popularity.getTransform().getBottom() - 15);

        getTransform().setSize(getCamera().viewportWidth, getCamera().viewportHeight);
        money.getTransform().translate(getPadLeft(), -getPadTop());
        perks.getTransform().translate(getPadLeft(), -getPadTop());
        popularity.getTransform().translate(-getPadRight(), -getPadTop());
        reputation.getTransform().translate(-getPadRight(), -getPadTop());
    }

    @Override
    public void load() {
        money = new TextObject(getAssets(), getCamera(), getPlayer());
        money.getTransform().setParent(getTransform());
        money.getTransform().setOrigin(0, 1);
        money.getTransform().setAlign(Align.TOP_LEFT);
        money.create("ZCOOL-40.ttf", () -> "Money: " + getPlayer().getMoney() + " gold");
        money.load();

        perks = new PerkObject(getAssets(), getCamera(), getPlayer());
        perks.getTransform().setParent(getTransform());
        perks.getTransform().setOrigin(0, 1);
        perks.getTransform().setAlign(Align.TOP_LEFT);
        perks.load();

        popularity = new PopularityMeterObject(getAssets(), getCamera(), getPlayer());
        initValueMeter(popularity);

        reputation = new ReputationMeterObject(getAssets(), getCamera(), getPlayer());
        initValueMeter(reputation);

        Chop.events.addListener(popularity, Events.EVT_POPULARITY_CHANGED);
        Chop.events.addListener(reputation, Events.EVT_REPUTATION_CHANGED, Events.EVT_REPUTATION_LVL_CHANGED);

        // Initialize meters
        Chop.events.notify(Events.EVT_POPULARITY_CHANGED, new EventData<>(getPlayer().getPopularity()));
        Chop.events.notify(Events.EVT_REPUTATION_CHANGED, new EventData<>(getPlayer().getReputation()));
        Chop.events.notify(Events.EVT_REPUTATION_LVL_CHANGED, new EventData<>(getPlayer().getReputationLevel()));
    }

    private void initValueMeter(ValueMeterObject obj) {
        obj.getTransform().setParent(getTransform());
        obj.getTransform().setOrigin(1, 1);
        obj.getTransform().setAlign(Align.TOP_RIGHT);
        obj.load();
        obj.pack();
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
