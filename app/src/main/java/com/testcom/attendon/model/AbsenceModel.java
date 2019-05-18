package com.testcom.attendon.model;

import java.io.Serializable;

public class AbsenceModel implements Serializable {
    private String date;
    private String record;

    public AbsenceModel(String date, String record){
        this.date = date;
        this.record = record;

    }

    public String getDate() {
        return date;
    }

    public String getRecord() {
        return record;
    }
}
