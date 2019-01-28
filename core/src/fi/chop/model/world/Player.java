package fi.chop.model.world;

import fi.chop.Chop;
import fi.chop.event.EventData;
import fi.chop.event.Events;

import java.util.*;

public class Player {

    public static final int MIN_REPUTATION_LEVEL = 1;
    public static final int MAX_REPUTATION_LEVEL = 5;

    private final Set<PopularityPerk> perks;
    private float popularity;
    private float reputation;
    private int reputationLevel;
    private int money;

    public Player() {
        perks = EnumSet.noneOf(PopularityPerk.class);
        reputationLevel = MIN_REPUTATION_LEVEL;
    }

    public void randomizePopularityPerks() {
        removeAllPerks();

        Random random = new Random();
        int perkCount = 0;

        /* Chance of perk count
         * 50-100% popularity ->
         *      60%-90% chance of 1 perk
         *      30-60% chance of 2 perks
         *      0-30% chance of 3 perks
         */
        float randomVal = random.nextFloat();
        float perkChance = Math.max((getPopularity() - 0.5f) / 0.5f * 0.9f, 0);
        int maxPerkCount = PopularityPerk.values().length;
        float chanceStep = perkChance / maxPerkCount;
        for (int n = 1; n <= maxPerkCount; n++) {
            float threshold = n * chanceStep;
            if (randomVal < threshold)
                perkCount++;
        }

        EnumSet<PopularityPerk> perkSet = EnumSet.allOf(PopularityPerk.class);
        for (int i = 0; i < perkCount; i++) {
            int index = random.nextInt(perkSet.size());
            Iterator<PopularityPerk> it = perkSet.iterator();
            for (int j = 0; j < index; j++) it.next();
            addPerks(it.next());
            it.remove();
        }
    }

    public void addPerks(PopularityPerk... perks) {
        this.perks.addAll(Arrays.asList(perks));
    }

    public void removePerks(PopularityPerk... perks) {
        this.perks.removeAll(Arrays.asList(perks));
    }

    public void removeAllPerks() {
        perks.clear();
    }

    public boolean hasPerk(PopularityPerk perk) {
        return perks.contains(perk);
    }

    public boolean hasAnyPerks() {
        return !perks.isEmpty();
    }

    public void addPopularity(float delta) {
        popularity = Math.min(Math.max(popularity + delta, 0), 1);
        Chop.events.notify(Events.EVT_POPULARITY_CHANGED, new EventData<>(popularity));
    }

    public float getPopularity() {
        return popularity;
    }

    public void addReputation(float delta) {
        reputation += delta;
        while (reputation >= 1) {
            increaseReputationLevel();
            reputation -= 1.0f;
            if (reputationLevel == MAX_REPUTATION_LEVEL) {
                reputation = 1;
                break;
            }
        }
        while (reputation < 0) {
            decreaseReputationLevel();
            reputation += 1.0f;
            if (reputationLevel == MIN_REPUTATION_LEVEL)
                reputation = 0;
        }
        Chop.events.notify(Events.EVT_REPUTATION_CHANGED, new EventData<>(reputation));
    }

    public float getReputation() {
        return reputation;
    }

    private void increaseReputationLevel() {
        if (++reputationLevel > MAX_REPUTATION_LEVEL)
            reputationLevel = MAX_REPUTATION_LEVEL;
        Chop.events.notify(Events.EVT_REPUTATION_LVL_CHANGED, new EventData<>(reputationLevel));
    }

    private void decreaseReputationLevel() {
        if (--reputationLevel < MIN_REPUTATION_LEVEL)
            reputationLevel = MIN_REPUTATION_LEVEL;
        Chop.events.notify(Events.EVT_REPUTATION_LVL_CHANGED, new EventData<>(reputationLevel));
    }

    public int getReputationLevel() {
        return reputationLevel;
    }

    public void addMoney(int delta) {
        money = Math.max(money + delta, 0);
    }

    public int getMoney() {
        return money;
    }

    public boolean hasEnoughMoney(int limit) {
        return money >= limit;
    }
}
