package fi.chop.model.world;

import java.util.*;

public class ExecutionFactory {

    private static final int MAX_CHARGES = 3;

    private static final Victim[] allVictims = {
            new Victim("John Doe", SocialStatus.LOWER_CLASS),
            new Victim("Jane Doe", SocialStatus.WORKING_CLASS),
            new Victim("John Smith", SocialStatus.MIDDLE_CLASS),
            new Victim("Amanda Bloom", SocialStatus.MIDDLE_CLASS),
            new Victim("Jack Matthews", SocialStatus.NOBILITY),
            new Victim("Ryan Silver", SocialStatus.ROYALTY)
    };

    private static final String[] allCharges = {
            "Murder of another human",
            "Plotting against the revolution"
    };

    public static Execution create() {
        Random random = new Random();

        Victim victim = randomizeVictim(random);
        String[] charges = randomizeCharges(random);
        boolean isFair = randomizeFairness(random);

        Execution execution = new Execution(victim, charges);
        execution.setFairPunishment(isFair);
        return execution;
    }

    private static Victim randomizeVictim(Random r) {
        return allVictims[r.nextInt(allVictims.length)];
    }

    private static String[] randomizeCharges(Random r) {
        List<String> chargeList = new ArrayList<>(Arrays.asList(allCharges));
        String[] charges = new String[r.nextInt(MAX_CHARGES - 1) + 1];
        for (int i = 0; i < charges.length; i++)
            charges[i] = chargeList.remove(r.nextInt(chargeList.size()));
        return charges;
    }

    private static boolean randomizeFairness(Random r) {
        return r.nextBoolean();
    }
}
