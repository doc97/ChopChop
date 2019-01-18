package fi.chop.model.world;

public class Victim {

    private String name;
    private SocialStatus status;

    public Victim(String name, SocialStatus status) {
        this.name = name;
        this.status = status;
    }

    public SocialStatus getSocialStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
