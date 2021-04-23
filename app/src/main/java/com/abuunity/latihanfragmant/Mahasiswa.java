package com.abuunity.latihanfragmant;

public class Mahasiswa {
    private int id;

    // menambahkan variabel fakultas dengan tipe data String
    private String npm, nama, prodi, fakultas;

    public Mahasiswa() {
    }

    // modifikasi konstruktor dengan menambahkan variabel fakultas bertipe data String
    public Mahasiswa(String npm, String nama, String prodi, String fakultas) {
        this.npm = npm;
        this.nama = nama;
        this.prodi = prodi;
        this.fakultas = fakultas;
    }

    // modifikasi konstruktor dengan menambahkan variabel fakultas bertipe data String
    public Mahasiswa(int id, String npm, String nama, String prodi, String fakultas) {
        this.id = id;
        this.npm = npm;
        this.nama = nama;
        this.prodi = prodi;
        this.fakultas = fakultas;
    }
    // akhir modifikasi

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

    // menambahkan getter dan setter untuk variabel fakultas
    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }
    // akhir modifikasi

}