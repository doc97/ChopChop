package fi.chop.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGameStats {

    private GameStats stats;

    @Before
    public void setUp() {
        stats = new GameStats();
    }

    @Test
    public void testDefaultValues() {
        assertEquals(0, stats.getDailyKills());
        assertEquals(0, stats.getOverallKills());
    }

    @Test
    public void testAddDailyKill() {
        stats.addDailyKill();
        assertEquals(1, stats.getDailyKills());
        assertEquals(1, stats.getOverallKills());
        stats.addDailyKill();
        assertEquals(2, stats.getDailyKills());
        assertEquals(2, stats.getOverallKills());
    }

    @Test
    public void testResetDailyKills() {
        stats.addDailyKill();
        stats.resetDailyKills();
        assertEquals(0, stats.getDailyKills());
        assertEquals(1, stats.getOverallKills());
        stats.addDailyKill();
        stats.addDailyKill();
        stats.resetDailyKills();
        assertEquals(0, stats.getDailyKills());
        assertEquals(3, stats.getOverallKills());
    }
}
