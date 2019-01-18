package fi.chop.model.world;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPlayer {

    private static final float EPSILON = 0.000001f;
    private Player player;

    @Before
    public void setUp() {
        player = new Player();
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
        assertEquals(0, player.getPopularity(), EPSILON);
    }

    @Test
    public void testAddPopularityPositive() {
        player.addPopularity(0.2f);
        assertEquals(0.2f, player.getPopularity(), EPSILON);
        player.addPopularity(0.2f);
        assertEquals(0.4f, player.getPopularity(), EPSILON);
    }

    @Test
    public void testAddPopularityNegative() {
        player.addPopularity(0.8f);
        player.addPopularity(-0.2f);
        assertEquals(0.6f, player.getPopularity(), EPSILON);
        player.addPopularity(-0.1f);
        assertEquals(0.5f, player.getPopularity(), EPSILON);
    }

    @Test
    public void testAddPopularityBelowZero() {
        player.addPopularity(-0.1f);
        assertEquals(0, player.getPopularity(), EPSILON);
    }

    @Test
    public void testAddPopularityAboveOne() {
        player.addPopularity(0.8f);
        player.addPopularity(0.3f);
        assertEquals(1, player.getPopularity(), EPSILON);
    }

    @Test
    public void testAddReputationZero() {
        player.addReputation(0);
        assertEquals(0, player.getReputation(), EPSILON);
    }

    @Test
    public void testAddReputationPositive() {
        player.addReputation(0.2f);
        assertEquals(0.2f, player.getReputation(), EPSILON);
        player.addReputation(0.2f);
        assertEquals(0.4f, player.getReputation(), EPSILON);
    }

    @Test
    public void testAddReputationNegative() {
        player.addReputation(0.8f);
        player.addReputation(-0.2f);
        assertEquals(0.6f, player.getReputation(), EPSILON);
        player.addReputation(-0.1f);
        assertEquals(0.5f, player.getReputation(), EPSILON);
    }

    @Test
    public void testAddReputationBelowZero() {
        player.addReputation(2.1f);
        player.addReputation(-0.2f);
        assertEquals(0.9f, player.getReputation(), EPSILON);
        assertEquals(1, player.getReputationLevel());
    }

    @Test
    public void testAddReputationAboveOne() {
        player.addReputation(0.8f);
        player.addReputation(0.3f);
        assertEquals(0.1f, player.getReputation(), EPSILON);
        assertEquals(1, player.getReputationLevel());
    }

    @Test
    public void testIncreaseReputationLevel() {
        player.addReputation(1);
        assertEquals(0, player.getReputation(), EPSILON);
        assertEquals(1, player.getReputationLevel());
    }

    @Test
    public void testDecreaseReputationLevel() {
        player.addReputation(2);
        player.addReputation(-1);
        assertEquals(0, player.getReputation(), EPSILON);
        assertEquals(1, player.getReputationLevel());
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
}
