package fi.chop.model;

public class Sentence {

    private String personType;
    private int maxPay;

    public Sentence (String personType, int maxPay) {
        this.personType = personType;
        this.maxPay = maxPay;
    }

    public String getPersonType () {
        return personType;
    }

    public int getMaxPay () {
        return maxPay;
    }
}
