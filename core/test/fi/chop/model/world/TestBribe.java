package fi.chop.model.world;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBribe {

    private Player player;

    @Before
    public void setUp() {
        player = new Player(null);
    }

    @Test
    public void testApplyZeroAmount() {
        new Bribe(0).apply(player);
        assertEquals(0, player.getMoney());
    }

    @Test
    public void testApplyPositiveAmount() {
        new Bribe(10).apply(player);
        assertEquals(10, player.getMoney());
        new Bribe(20).apply(player);
        assertEquals(30, player.getMoney());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAmount() {
        new Bribe(-1);
    }
}
