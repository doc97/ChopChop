package fi.chop.model.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
        int bribe = randomizeBribe(random, victim.getSocialStatus());
        int salary = randomizeSalary(random, victim.getSocialStatus());
        boolean isFair = randomizeFairness(random);

        Execution execution = new Execution(victim, charges);
        execution.setBribe(bribe);
        execution.setSalary(salary);
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

    private static int randomizeBribe(Random r, SocialStatus status) {
        // 70% chance of no bribe
        if (r.nextInt(100) < 70)
            return 0;

        switch (status) {
            case LOWER_CLASS:
                return 100 + r.nextInt(50);
            case WORKING_CLASS:
                return 250 + r.nextInt(100);
            case MIDDLE_CLASS:
                return 500 + r.nextInt(100);
            case NOBILITY:
                return 600 + r.nextInt(400);
            case ROYALTY:
                return 750 + r.nextInt(250);
            default:
                return 0;
        }
    }

    private static int randomizeSalary(Random r, SocialStatus status) {
        switch (status) {
            case LOWER_CLASS:
                return 50 + r.nextInt(50);
            case WORKING_CLASS:
                return 150 + r.nextInt(75);
            case MIDDLE_CLASS:
                return 300 + r.nextInt(100);
            case NOBILITY:
                return 400 + r.nextInt(50);
            case ROYALTY:
                return 500;
            default:
                return 0;
        }
    }

    private static boolean randomizeFairness(Random r) {
        return r.nextBoolean();
    }
}
