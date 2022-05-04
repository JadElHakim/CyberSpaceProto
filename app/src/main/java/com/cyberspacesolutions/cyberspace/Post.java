package com.cyberspacesolutions.cyberspace;

import java.sql.Blob;
import java.sql.Struct;
//CLASS TO BE ABLE TO CREATE A CUSTOM ADAPTER FOR THE LIST VIEW
//DEFINES MAIN CHARACTERISTICS OF THE POST AND HAS GETTERS AND SETTERS TO ALL INFO INSIDE A POST
//EASY TO USE TO PASS INFO INTO NEXT ACTIVITY AND READ THE POST INSIDE OF IT
public class Post {
    String post_title;
    String vulnerability_type;
    String vulnerability_description;
    String mitigation_description;
    String username;
    public Post(String post_title, String vulnerability_type, String vulnerability_description, String mitigation_description,  String username) {
        this.post_title = post_title;
        this.vulnerability_type = vulnerability_type;
        this.vulnerability_description = vulnerability_description;
        this.mitigation_description = mitigation_description;
        this.username = username;
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



    public String getUsername() {
        return username;
    }

    public void setUsername(int id) {
        this.username = username;
    }
}
