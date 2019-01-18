package fi.chop.model.world;

public class Bribe {

    private int amount;

    public Bribe(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("A bribe amount cannot be negative");
        this.amount = amount;
    }

    public void apply(Player player) {
        player.addMoney(amount);
    }
}
