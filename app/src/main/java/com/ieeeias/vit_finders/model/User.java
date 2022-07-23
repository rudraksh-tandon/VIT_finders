package com.ieeeias.vit_finders.model;

public class User {
    public String userId;
    public String personName;
    public String email;
    public String regNo;

    public User(){

    }

    public User(String userId, String personName, String email, String regNo){
        this.userId = userId;
        this.personName = personName;
        this.email = email;
        this.regNo = regNo;
    }
}
