package fi.chop.model.world;

public class Execution {

    private final Victim victim;
    private final String[] charges;
    private int salary;
    private int bribe;
    private boolean fair;

    public Execution(Victim victim, String... charges) {
        this.victim = victim;
        this.charges = charges;
    }

    public void setFairPunishment(boolean fair) {
        this.fair = fair;
    }

    public boolean isFairPunishment() {
        return fair;
    }

    public void setSalary(int salary) {
        if (salary < 0)
            throw new IllegalArgumentException("A salary cannot be negative");
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setBribe(int bribe) {
        if (bribe < 0)
            throw new IllegalArgumentException("A bribe cannot be negative");
        this.bribe = bribe;
    }

    public int getBribe() {
        return bribe;
    }

    public String getVictimName() {
        return victim.getName();
    }

    public SocialStatus getVictimSocialStatus() {
        return victim.getSocialStatus();
    }

    public String[] getCharges() {
        return charges;
    }
}
