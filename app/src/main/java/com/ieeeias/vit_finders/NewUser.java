package com.ieeeias.vit_finders;

public class NewUser {
    public String userId;
    public String personName;
    public String email;
    public String regNo;

    public NewUser(){

    }

    public NewUser(String userId, String personName, String email, String regNo){
        this.userId = userId;
        this.personName = personName;
        this.email = email;
        this.regNo = regNo;
    }
}
