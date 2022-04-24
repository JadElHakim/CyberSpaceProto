package com.cyberspacesolutions.cyberspace;

public class User {
    int id;
    String username;
    String career;
    String Profile_description;

    public User(int id, String username, String career, String profile_description) {
        this.id = id;
        this.username = username;
        this.career = career;
        Profile_description = profile_description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getProfile_description() {
        return Profile_description;
    }

    public void setProfile_description(String profile_description) {
        Profile_description = profile_description;
    }
}
