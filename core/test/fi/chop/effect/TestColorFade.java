package fi.chop.effect;

import com.badlogic.gdx.graphics.Color;
import fi.chop.MathUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestColorFade {

    @Test
    public void testDefaultValues() {
        ColorFade blackToWhite = new ColorFade(Color.BLACK, Color.WHITE, 1);
        ColorFade redToBlue = new ColorFade(Color.RED, Color.BLUE, 1);
        assertEquals(Color.BLACK, blackToWhite.getColor());
        assertEquals(Color.RED, redToBlue.getColor());
    }

    @Test
    public void testUpdateBlackAndWhite() {
        ColorFade blackToWhite = new ColorFade(Color.BLACK, Color.WHITE, 1);
        Color expected1 = new Color(0.25f, 0.25f, 0.25f, 1);
        Color expected2 = new Color(0.5f, 0.5f, 0.5f, 1);
        blackToWhite.update(0.25f);
        assertEquals(expected1, blackToWhite.getColor());
        blackToWhite.update(0.25f);
        assertEquals(expected2, blackToWhite.getColor());
        assertEquals(new Color(0, 0, 0, 1), Color.BLACK);
        assertEquals(new Color(1, 1, 1, 1), Color.WHITE);
    }

    @Test
    public void testUpdateRedToBlue() {
        ColorFade redToBlue = new ColorFade(Color.RED, Color.BLUE, 1);
        Color expected1 = new Color(0.75f, 0, 0.25f, 1);
        Color expected2 = new Color(0.5f, 0, 0.5f, 1);
        redToBlue.update(0.25f);
        assertEquals(expected1, redToBlue.getColor());
        redToBlue.update(0.25f);
        assertEquals(expected2, redToBlue.getColor());
        assertEquals(new Color(1, 0, 0, 1), Color.RED);
        assertEquals(new Color(0, 0, 1, 1), Color.BLUE);
    }

    @Test
    public void testDuration() {
        ColorFade blackToWhite = new ColorFade(Color.BLACK, Color.WHITE, 2);
        Color expected1 = new Color(0.25f, 0.25f, 0.25f, 1);
        Color expected2 = new Color(0.5f, 0.5f, 0.5f, 1);
        blackToWhite.update(0.5f);
        assertEquals(expected1, blackToWhite.getColor());
        blackToWhite.update(0.5f);
        assertEquals(expected2, blackToWhite.getColor());
    }

    @Test
    public void testCustomFadeFunctionBlackToWhite() {
        ColorFade blackToWhite = new ColorFade(Color.BLACK, Color.WHITE, 1,
                (t) -> MathUtil.smoothStartN(t, 2));
        float value = MathUtil.smoothStartN(0.25f, 2);
        Color expected = new Color(value, value, value, 1);
        blackToWhite.update(0.25f);
        assertEquals(expected, blackToWhite.getColor());
    }

    @Test
    public void testCustomFadeFunctionRedToBlue() {
        ColorFade blackToWhite = new ColorFade(Color.RED, Color.BLUE, 1,
                (t) -> MathUtil.smoothStartN(t, 2));
        float value = MathUtil.smoothStartN(0.75f, 2);
        Color expected = new Color(1 - value, 0, value, 1);
        blackToWhite.update(0.75f);
        assertEquals(expected, blackToWhite.getColor());
    }
}
