package com.abuunity.latihanfragmant.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.abuunity.latihanfragmant.pojo.old_Mahasiswa;

public class old_DatabaseHandler extends SQLiteOpenHelper {

    //ubah versi database ke versi 2
    private static final int DATABASE_VERSION=3;
    private static final String DATABASE_NAME="MahasiswaDB";
    private static final String TABLE_MAHASISWA="tb_mahasiswa";
    private static final String KEY_ID="id";
    private static final String KEY_NIM ="nim";
    private static final String KEY_NAMA="nama";
    private static final String KEY_PRODI="prodi";


    public old_DatabaseHandler(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_MAHASISWA="CREATE TABLE "+TABLE_MAHASISWA+"("
                +KEY_ID+" INTEGER PRIMARY KEY, "
                + KEY_NIM +" TEXT, "
                +KEY_NAMA+" TEXT, "
                +KEY_PRODI+" TEXT," +" TEXT"+")";
        db.execSQL(CREATE_TABLE_MAHASISWA);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MAHASISWA);
        onCreate(db);
    }


    public void save(old_Mahasiswa oldMahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NIM, oldMahasiswa.getNim());
        values.put(KEY_NAMA, oldMahasiswa.getNama());
        values.put(KEY_PRODI, oldMahasiswa.getProdi());
        db.insert(TABLE_MAHASISWA,null,values);
        db.close();
    }

    public void update(old_Mahasiswa oldMahasiswa) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NIM, oldMahasiswa.getNim());
        values.put(KEY_NAMA, oldMahasiswa.getNama());
        values.put(KEY_PRODI, oldMahasiswa.getProdi());
        db.update(TABLE_MAHASISWA,values,KEY_ID+"=?",
                new String[] {
                        String.valueOf(oldMahasiswa.getId())
        });
        db.close();
    }

    public void delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MAHASISWA, KEY_ID+"=?",
                new String[] {id});
        db.close();
    }

}
