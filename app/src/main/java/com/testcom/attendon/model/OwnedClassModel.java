package com.testcom.attendon.model;

import java.io.Serializable;

public class OwnedClassModel implements Serializable {

    private String code;
    private String name;
    private String time;
    private String date;
    private String desc;


    public OwnedClassModel(String code, String name, String time, String date, String desc) {
        this.code = code;
        this.name = name;
        this.time = time;
        this.date = date;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

}
