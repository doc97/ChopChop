package fi.chop.model.world;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestVictim {

    @Test
    public void testGetName() {
        Victim victim = new Victim("", SocialStatus.LOWER_CLASS);
        assertEquals("", victim.getName());
        victim = new Victim("John Doe", SocialStatus.LOWER_CLASS);
        assertEquals("John Doe", victim.getName());
    }

    @Test
    public void testGetSocialStatus() {
        Victim victim = new Victim("", SocialStatus.LOWER_CLASS);
        assertEquals(SocialStatus.LOWER_CLASS, victim.getSocialStatus());
        victim = new Victim("", SocialStatus.WORKING_CLASS);
        assertEquals(SocialStatus.WORKING_CLASS, victim.getSocialStatus());
        victim = new Victim("", SocialStatus.MIDDLE_CLASS);
        assertEquals(SocialStatus.MIDDLE_CLASS, victim.getSocialStatus());
    }
}
