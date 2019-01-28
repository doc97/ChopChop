package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.util.ValueMeterObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public class GameGUIObject extends GUIObject {

    private TextObject money;
    private TextObject taxes;
    private PerkObject perks;
    private PopularityMeterObject popularity;
    private ReputationMeterObject reputation;

    public GameGUIObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player);
        pad(30, 30, 30, 30);
    }

    @Override
    public void pack() {
        taxes.getTransform().setPosition(50, money.getTransform().getBottom() - 15);
        reputation.getTransform().setPosition(0, popularity.getTransform().getBottom() - 15);
        perks.getTransform().setPosition(0, reputation.getTransform().getBottom() - 15);

        getTransform().setSize(getCamera().viewportWidth, getCamera().viewportHeight);
        money.getTransform().translate(getPadLeft(), -getPadTop());
        taxes.getTransform().translate(getPadLeft(), -getPadTop());
        perks.getTransform().translate(-getPadRight(), -getPadTop());
        popularity.getTransform().translate(-getPadRight(), -getPadTop());
        reputation.getTransform().translate(-getPadRight(), -getPadTop());
    }

    @Override
    public void load() {
        money = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
        money.getTransform().setParent(getTransform());
        money.getTransform().setOrigin(0, 1);
        money.getTransform().setAlign(Align.TOP_LEFT);
        money.create("ZCOOL-40.ttf", () -> "Money: " + getPlayer().getMoney() + " gold");
        money.load();
        money.pack();

        taxes = new TextObject(getAssets(), getCamera(), getWorld(), getPlayer());
        taxes.getTransform().setParent(getTransform());
        taxes.getTransform().setOrigin(0, 1);
        taxes.getTransform().setAlign(Align.TOP_LEFT);
        taxes.create("ZCOOL-30.ttf", () ->
                getWorld().getDaysUntilTaxation() + " days until taxation " + "(" + getWorld().getTaxes() + " gold)");
        taxes.load();
        taxes.pack();

        perks = new PerkObject(getAssets(), getCamera(), getWorld(), getPlayer());
        perks.getTransform().setParent(getTransform());
        perks.getTransform().setOrigin(1, 1);
        perks.getTransform().setAlign(Align.TOP_RIGHT);
        perks.load();

        popularity = new PopularityMeterObject(getAssets(), getCamera(), getWorld(), getPlayer());
        initValueMeter(popularity);

        reputation = new ReputationMeterObject(getAssets(), getCamera(), getWorld(), getPlayer());
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
        taxes.die();
        perks.die();
        popularity.die();
        reputation.die();
    }

    @Override
    public GameObject[] getChildren() {
        return new GameObject[] { money, taxes, perks, popularity, reputation };
    }
}
