package com.testcom.attendon.model;

import java.io.Serializable;

public class TampilMahasiswaModel implements Serializable {

    private String nim;
    private String nama;
    private String nama_fakultas;


    public TampilMahasiswaModel(){}

    public TampilMahasiswaModel(String nim, String nama, String nama_fakultas) {
        this.nim = nim;
        this.nama = nama;
        this.nama_fakultas = nama_fakultas;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getNama_fakultas() {
        return nama_fakultas;
    }

}
