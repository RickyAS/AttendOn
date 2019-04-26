package com.uc.appsmahasiswa.model;

import java.io.Serializable;

public class Mahasiswa implements Serializable {

    private String nim;
    private String nama;
    private String id_prodi;
    private String photo;

    public Mahasiswa(){

    }

    public Mahasiswa(String nim, String nama, String id_prodi, String photo) {
        this.nim = nim;
        this.nama = nama;
        this.id_prodi = id_prodi;
        this.photo = photo;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId_prodi() {
        return id_prodi;
    }

    public void setId_prodi(String id_prodi) {
        this.id_prodi = id_prodi;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}