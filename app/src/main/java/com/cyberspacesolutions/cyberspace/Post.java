package com.cyberspacesolutions.cyberspace;

import java.sql.Blob;

public class Post {
    String post_title;
    String vulnerability_type;
    String vulnerability_description;
    String mitigation_description;
    //Blob profile ;
    int id ;

    public Post(String post_title, String vulnerability_type, String vulnerability_description, String mitigation_description,  int id) {
        this.post_title = post_title;
        this.vulnerability_type = vulnerability_type;
        this.vulnerability_description = vulnerability_description;
        this.mitigation_description = mitigation_description;
       // this.profile = profile;
        this.id = id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getVulnerability_type() {
        return vulnerability_type;
    }

    public void setVulnerability_type(String vulnerability_type) {
        this.vulnerability_type = vulnerability_type;
    }

    public String getVulnerability_description() {
        return vulnerability_description;
    }

    public void setVulnerability_description(String vulnerability_description) {
        this.vulnerability_description = vulnerability_description;
    }

    public String getMitigation_description() {
        return mitigation_description;
    }

    public void setMitigation_description(String mitigation_description) {
        this.mitigation_description = mitigation_description;
    }

    //public Blob getProfile() {
    //    return profile;
    //}

   // public void setProfile(Blob profile) {
   //     this.profile = profile;
   // }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
