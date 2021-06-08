package com.abuunity.latihanfragmant.pojo;

public class old_Mahasiswa {
    private String id;


    private String nim, nama, prodi;

    public old_Mahasiswa() {
    }


    public old_Mahasiswa(String nim, String nama, String prodi) {
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;

    }


    public old_Mahasiswa(String id, String nim, String nama, String prodi) {
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.prodi = prodi;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
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