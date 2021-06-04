package model.entity.user;

public class Profile {

    private int id;

    private String developerName;
    private String profileName;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}