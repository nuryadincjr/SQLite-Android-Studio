package com.abuunity.latihanfragmant;

public class Mahasiswa {
    private int id;
    private String npm, nama, prodi;

    public Mahasiswa() {
    }

    public Mahasiswa(String npm, String nama, String prodi) {
        this.npm = npm;
        this.nama = nama;
        this.prodi = prodi;
    }

    public Mahasiswa(int id, String npm, String nama, String prodi) {
        this.id = id;
        this.npm = npm;
        this.nama = nama;
        this.prodi = prodi;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNpm() {
        return npm;
    }
    public void setNpm(String npm) {
        this.npm = npm;
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