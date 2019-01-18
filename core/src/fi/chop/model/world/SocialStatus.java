package fi.chop.model.world;

public enum SocialStatus {
    LOWER_CLASS("Lower class"),
    WORKING_CLASS("Working class"),
    MIDDLE_CLASS("Middle class"),
    NOBILITY("Nobility"),
    ROYALTY("Royalty");

    private String representation;

    SocialStatus(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
