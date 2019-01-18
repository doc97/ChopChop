package fi.chop.model.world;

import fi.chop.event.EventData;
import fi.chop.event.EventListener;
import fi.chop.event.EventSystem;
import fi.chop.event.Events;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPlayer implements EventListener {

    private static final float EPSILON = 0.000001f;
    private EventSystem eventSystem;
    private Player player;

    private int reputationLvl;
    private float reputation;
    private float popularity;

    @Before
    public void setUp() {
        eventSystem = new EventSystem();
        player = new Player(eventSystem);
        eventSystem.addListener(this, Events.EVT_POPULARITY_CHANGED,
                Events.EVT_REPUTATION_CHANGED, Events.EVT_REPUTATION_LVL_CHANGED);

        reputationLvl = 1;
        reputation = 0;
        popularity = 0;
    }

    @Test
    public void testDefaultValues() {
        assertFalse(player.hasAnyPerks());
        assertEquals(0, player.getPopularity(), EPSILON);
        assertEquals(0, player.getReputation(), EPSILON);
        assertEquals(0, player.getReputationLevel());
        assertEquals(0, player.getMoney());
        assertTrue(player.hasEnoughMoney(0));
        assertFalse(player.hasEnoughMoney(1));
    }

    @Test
    public void testAddOnePerk() {
        player.addPerks(PopularityPerk.MEANINGFUL_WORK);
        assertTrue(player.hasAnyPerks());
        assertTrue(player.hasPerk(PopularityPerk.MEANINGFUL_WORK));
    }

    @Test
    public void testAddTwoPerks() {
        player.addPerks(PopularityPerk.TURNING_A_BLIND_EYE,
                PopularityPerk.GIFT_OF_THE_PEOPLE);
        assertTrue(player.hasAnyPerks());
        assertTrue(player.hasPerk(PopularityPerk.TURNING_A_BLIND_EYE));
        assertTrue(player.hasPerk(PopularityPerk.GIFT_OF_THE_PEOPLE));
        assertFalse(player.hasPerk(PopularityPerk.MEANINGFUL_WORK));
    }

    @Test
    public void testRemoveOnePerk() {
        player.addPerks(PopularityPerk.MEANINGFUL_WORK);
        player.removePerks(PopularityPerk.MEANINGFUL_WORK);
        assertFalse(player.hasAnyPerks());
        assertFalse(player.hasPerk(PopularityPerk.MEANINGFUL_WORK));
    }

    @Test
    public void testAddPopularityZero() {
        player.addPopularity(0);
        eventSystem.update();
        assertEquals(0, player.getPopularity(), EPSILON);
        assertEquals(0, popularity, EPSILON);
    }

    @Test
    public void testAddPopularityPositive() {
        player.addPopularity(0.2f);
        eventSystem.update();
        assertEquals(0.2f, player.getPopularity(), EPSILON);
        assertEquals(0.2f, popularity, EPSILON);
        player.addPopularity(0.2f);
        eventSystem.update();
        assertEquals(0.4f, player.getPopularity(), EPSILON);
        assertEquals(0.4f, popularity, EPSILON);
    }

    @Test
    public void testAddPopularityNegative() {
        player.addPopularity(0.8f);
        player.addPopularity(-0.2f);
        eventSystem.update();
        assertEquals(0.6f, player.getPopularity(), EPSILON);
        assertEquals(0.6f, popularity, EPSILON);
        player.addPopularity(-0.1f);
        eventSystem.update();
        assertEquals(0.5f, player.getPopularity(), EPSILON);
        assertEquals(0.5f, popularity, EPSILON);
    }

    @Test
    public void testAddPopularityBelowZero() {
        player.addPopularity(-0.1f);
        eventSystem.update();
        assertEquals(0, player.getPopularity(), EPSILON);
        assertEquals(0, popularity, EPSILON);
    }

    @Test
    public void testAddPopularityAboveOne() {
        player.addPopularity(0.8f);
        player.addPopularity(0.3f);
        eventSystem.update();
        assertEquals(1, player.getPopularity(), EPSILON);
        assertEquals(1, popularity, EPSILON);
    }

    @Test
    public void testAddReputationZero() {
        player.addReputation(0);
        eventSystem.update();
        assertEquals(0, player.getReputation(), EPSILON);
        assertEquals(0, reputation, EPSILON);
    }

    @Test
    public void testAddReputationPositive() {
        player.addReputation(0.2f);
        eventSystem.update();
        assertEquals(0.2f, player.getReputation(), EPSILON);
        assertEquals(0.2f, reputation, EPSILON);
        player.addReputation(0.2f);
        eventSystem.update();
        assertEquals(0.4f, player.getReputation(), EPSILON);
        assertEquals(0.4f, reputation, EPSILON);
    }

    @Test
    public void testAddReputationNegative() {
        player.addReputation(0.8f);
        player.addReputation(-0.2f);
        eventSystem.update();
        assertEquals(0.6f, player.getReputation(), EPSILON);
        assertEquals(0.6f, reputation, EPSILON);
        player.addReputation(-0.1f);
        eventSystem.update();
        assertEquals(0.5f, player.getReputation(), EPSILON);
        assertEquals(0.5f, reputation, EPSILON);
    }

    @Test
    public void testAddReputationBelowZero() {
        player.addReputation(2.1f);
        player.addReputation(-0.2f);
        eventSystem.update();
        assertEquals(0.9f, player.getReputation(), EPSILON);
        assertEquals(0.9f, reputation, EPSILON);
        assertEquals(1, player.getReputationLevel());
        assertEquals(1, reputationLvl, EPSILON);
    }

    @Test
    public void testAddReputationAboveOne() {
        player.addReputation(0.8f);
        player.addReputation(0.3f);
        eventSystem.update();
        assertEquals(0.1f, player.getReputation(), EPSILON);
        assertEquals(0.1f, reputation, EPSILON);
        assertEquals(1, player.getReputationLevel());
        assertEquals(1, reputationLvl, EPSILON);
    }

    @Test
    public void testIncreaseReputationLevel() {
        player.addReputation(1);
        eventSystem.update();
        assertEquals(0, player.getReputation(), EPSILON);
        assertEquals(0, reputation, EPSILON);
        assertEquals(1, player.getReputationLevel());
        assertEquals(1, reputationLvl, EPSILON);
    }

    @Test
    public void testDecreaseReputationLevel() {
        player.addReputation(2);
        player.addReputation(-1);
        eventSystem.update();
        assertEquals(0, player.getReputation(), EPSILON);
        assertEquals(0, reputation, EPSILON);
        assertEquals(1, player.getReputationLevel());
        assertEquals(1, reputationLvl, EPSILON);
    }

    @Test
    public void testAddReputationLevelBelowMinLevel() {
        player.addReputation(-0.1f);
        assertEquals(0, player.getReputation(), EPSILON);
        assertEquals(Player.MIN_REPUTATION_LEVEL, player.getReputationLevel());
    }

    @Test
    public void testAddReputationLevelAboveMaxLevel() {
        player.addReputation(5.1f);
        assertEquals(1, player.getReputation(), EPSILON);
        assertEquals(Player.MAX_REPUTATION_LEVEL, player.getReputationLevel());
    }

    @Test
    public void testAddMoneyZero() {
        player.addMoney(0);
        assertEquals(0, player.getMoney());
    }

    @Test
    public void testAddMoneyPositive() {
        player.addMoney(10);
        assertEquals(10, player.getMoney());
        player.addMoney(20);
        assertEquals(30, player.getMoney());
    }

    @Test
    public void testAddMoneyNegative() {
        player.addMoney(80);
        player.addMoney(-20);
        assertEquals(60, player.getMoney());
        player.addMoney(-10);
        assertEquals(50, player.getMoney());
    }

    @Test
    public void testAddMoneyBelowZero() {
        player.addMoney(-10);
        assertEquals(0, player.getMoney());
    }

    @Test
    public void testHasEnoughMoney() {
        player.addMoney(10);
        assertTrue(player.hasEnoughMoney(8));
        assertTrue(player.hasEnoughMoney(10));
        assertFalse(player.hasEnoughMoney(11));
    }

    @Override
    public void handle(Events event, EventData data) {
        switch (event) {
            case EVT_POPULARITY_CHANGED:
                popularity = (float) data.get();
                break;
            case EVT_REPUTATION_CHANGED:
                reputation = (float) data.get();
                break;
            case EVT_REPUTATION_LVL_CHANGED:
                reputationLvl = (int) data.get();
                break;
        }
    }
}
