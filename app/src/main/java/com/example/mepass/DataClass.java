package com.example.mepass;

public class DataClass {
    String email;
    String website;
    String password;
    String user;

    public DataClass(){

    }

    public DataClass(String website, String email, String password) {
        //this.user = user;
        this.website = website;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
