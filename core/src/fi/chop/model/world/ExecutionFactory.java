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
            new Victim("Ryan Silver", SocialStatus.ROYALTY),
            new Victim("Monsieur d'Artagnan", SocialStatus.NOBILITY)
    };

    private static final String[] allCharges = {
            "Murder of another human",
            "Plotting against the revolution",
            "Stealing food from community storage"
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
                return 25 + r.nextInt(25);
            case WORKING_CLASS:
                return 75 + r.nextInt(50);
            case MIDDLE_CLASS:
                return 250 + r.nextInt(50);
            case NOBILITY:
                return 400 + r.nextInt(100);
            case ROYALTY:
                return 600 + r.nextInt(50);
            default:
                return 0;
        }
    }

    private static int randomizeSalary(Random r, SocialStatus status) {
        switch (status) {
            case LOWER_CLASS:
                return 5 + r.nextInt(5);
            case WORKING_CLASS:
                return 15 + r.nextInt(10);
            case MIDDLE_CLASS:
                return 30 + r.nextInt(20);
            case NOBILITY:
                return 75 + r.nextInt(50);
            case ROYALTY:
                return 150;
            default:
                return 0;
        }
    }

    private static boolean randomizeFairness(Random r) {
        return r.nextBoolean();
    }
}
