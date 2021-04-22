package com.abuunity.latihanfragmant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="MahasiswaDB";
    private static final String TABLE_MAHASISWA="tb_mahasiswa";
    private static final String KEY_ID="id";
    private static final String KEY_NPM="npm";
    private static final String KEY_NAMA="nama";
    private static final String KEY_PRODI="prodi";


    public DatabaseHandler(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_MAHASISWA="CREATE TABLE "+TABLE_MAHASISWA+"("
                +KEY_ID+" INTEGER PRIMARY KEY, "
                +KEY_NPM+" TEXT, "
                +KEY_NAMA+" TEXT, "
                +KEY_PRODI+" TEXT"+")";
        db.execSQL(CREATE_TABLE_MAHASISWA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MAHASISWA);
        onCreate(db);
    }

    public void save(Mahasiswa mahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NPM, mahasiswa.getNim());
        values.put(KEY_NAMA, mahasiswa.getNama());
        values.put(KEY_PRODI, mahasiswa.getProdi());
        db.insert(TABLE_MAHASISWA,null,values);
        db.close();
    }

    public void update(Mahasiswa mahasiswa) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NPM, mahasiswa.getNim());
        values.put(KEY_NAMA, mahasiswa.getNama());
        values.put(KEY_PRODI, mahasiswa.getProdi());
        db.update(TABLE_MAHASISWA,values,KEY_ID+"=?",
                new String[] {
                        String.valueOf(mahasiswa.getId())
        });
        db.close();
    }

    public void delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_NAME, KEY_ID+"=?",
                new String[] {
                        id
        });
        db.close();
    }

    public List<Mahasiswa> findAll() {
        List<Mahasiswa> mahasiswaList = new ArrayList<Mahasiswa>();
        String quary = "SELECT * FROM "+TABLE_MAHASISWA;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(quary,null);
        if(cursor.moveToFirst()) {
            do {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setId(Integer.valueOf(cursor.getString(0)));
                mahasiswa.setNim(cursor.getString(1));
                mahasiswa.setNama(cursor.getString(2));
                mahasiswa.setProdi(cursor.getString(3));

                mahasiswaList.add(mahasiswa);
            } while (cursor.moveToNext());
        }
        return mahasiswaList;
    }
}
