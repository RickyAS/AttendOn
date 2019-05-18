package com.testcom.attendon.model;

import java.io.Serializable;

public class StudentModel implements Serializable {

    private String username;
    private String email;
    private String classcode;


    public StudentModel(String username,  String email, String code) {
        this.username = username;
        this.email = email;
        this.classcode = code;

    }

    public String getUsername() {
        return username;
    }

    public String getClasscode() {
        return classcode;
    }

    public String getEmail() {
        return email;
    }
}
