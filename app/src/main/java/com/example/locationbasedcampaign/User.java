package com.example.locationbasedcampaign;

public class User {
    private String usr_Name;
    private String usr_Pass;
    private String usr_Email;

    public User(String name,String email,String pass){
        this.usr_Name = name;
        this.usr_Email = email;
        this.usr_Pass = pass;
    }

    public String getUsr_Name() {
        return usr_Name;
    }

    public void setUsr_Name(String usr_Name) {
        this.usr_Name = usr_Name;
    }

    public String getUsr_Pass() {
        return usr_Pass;
    }

    public void setUsr_Pass(String usr_Pass) {
        this.usr_Pass = usr_Pass;
    }

    public String getUsr_Email() {
        return usr_Email;
    }

    public void setUsr_Email(String usr_Email) {
        this.usr_Email = usr_Email;
    }
}