package com.uc.appsmahasiswa.model;

import java.io.Serializable;

public class Prodi implements Serializable {

    private String id_prodi;
    private String nama_prodi;
    private String id_fakultas;

    public Prodi(){

    }

    public Prodi(String id_prodi, String nama_prodi, String id_fakultas) {
        this.id_prodi = id_prodi;
        this.nama_prodi = nama_prodi;
        this.id_fakultas = id_fakultas;
    }

    public String getId_prodi() {
        return id_prodi;
    }

    public void setId_prodi(String id_prodi) {
        this.id_prodi = id_prodi;
    }

    public String getNama_prodi() {
        return nama_prodi;
    }

    public void setNama_prodi(String nama_prodi) {
        this.nama_prodi = nama_prodi;
    }

    public String getId_fakultas() {
        return id_fakultas;
    }

    public void setId_fakultas(String id_fakultas) {
        this.id_fakultas = id_fakultas;
    }
}
