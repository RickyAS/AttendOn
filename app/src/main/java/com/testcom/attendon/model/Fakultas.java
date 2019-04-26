package com.uc.appsmahasiswa.model;

import java.io.Serializable;

public class Fakultas implements Serializable {

    private String id_fakultas;
    private String nama_fakultas;

    public Fakultas(){

    }

    public Fakultas(String id_fakultas, String nama_fakultas) {
        this.id_fakultas = id_fakultas;
        this.nama_fakultas = nama_fakultas;
    }

    public String getId_fakultas() {
        return id_fakultas;
    }

    public void setId_fakultas(String id_fakultas) {
        this.id_fakultas = id_fakultas;
    }

    public String getNama_fakultas() {
        return nama_fakultas;
    }

    public void setNama_fakultas(String nama_fakultas) {
        this.nama_fakultas = nama_fakultas;
    }
}
