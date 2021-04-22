package com.abuunity.latihanfragmant;

public class Mahasiswa {
    private int id;
    private String nim, nama, prodi;

    public Mahasiswa() {
    }

    public Mahasiswa(String nim, String nama, String prodi) {
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;
    }

    public Mahasiswa(int id, String nim, String nama, String prodi) {
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getProdi() {
        return prodi;
    }
    public void setProdi(String prodi) {
        this.prodi = prodi;
    }
}