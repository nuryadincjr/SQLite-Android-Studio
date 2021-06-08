package com.abuunity.latihanfragmant.pojo;

import java.util.ArrayList;

public class old_ResponseMahasiswa {
    private ArrayList<old_Mahasiswa> oldMahasiswas;
    private String message;
    private boolean error;

    public old_ResponseMahasiswa(ArrayList<old_Mahasiswa> oldMahasiswas, String message, boolean error) {
        this.oldMahasiswas = oldMahasiswas;
        this.message = message;
        this.error = error;
    }

    public ArrayList<old_Mahasiswa> getOldMahasiswas() {
        return oldMahasiswas;
    }

    public void setOldMahasiswas(ArrayList<old_Mahasiswa> oldMahasiswas) {
        this.oldMahasiswas = oldMahasiswas;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
