package com.abuunity.latihanfragmant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditMahasiswaActivity extends AppCompatActivity {

    private EditText editNpm, editNama;

    // menambahkan variabel spFakultas dengan tipe data Spinner
    private Spinner spFakultas;
    // akhir modifikasi

    private Spinner spProdi;
    private Button btnUpdate;
    private int id;

    // menambahkan variabel pilih Fakultas dan fakultas Lama dengan tipe data String
    private String pilProdi, npm, nama, prodiLama, pilFakultas, fakultasLama;
    // akhir modifikasi

    private ArrayAdapter<CharSequence> arrayAdapter;

    // tambah array adapter untuk posisi spinner fakultas
    private ArrayAdapter<CharSequence> arrayAdapter2;
    // akhir modifikasi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mahasiswa);

        editNpm = findViewById(R.id.text_input_npm);
        editNama = findViewById(R.id.text_input_nama);
        spProdi = findViewById(R.id.sp_prodi);

        // panggil Spinner spFakultas pada activity_add_mahasiswa
        spFakultas = findViewById(R.id.sp_fakultas);
        // akhir modifikasi

        btnUpdate = findViewById(R.id.btn_update);

        arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.daftar_prodi, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProdi.setAdapter(arrayAdapter);

        // deklarasika sebuah adapter baru untuk menset daftar Fakultas
        arrayAdapter2 = ArrayAdapter.createFromResource(this,
                R.array.daftar_fakultas, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFakultas.setAdapter(arrayAdapter2);
        // akhir modifikasi

        getDataIntent();

        spProdi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilProdi = parent.getItemAtPosition(position).toString();
                System.out.println(pilProdi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // modifikasi untuk membuat selected listener untuk pilih fakultas
        spFakultas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilFakultas = parent.getItemAtPosition(position).toString();
                System.out.println(pilFakultas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // akhir modifikasi

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());

                // modifikasi dan tambahkan pilFakultas sesuai dengan index konstruktor yang dibuat
                Mahasiswa mahasiswa = new Mahasiswa(id, editNpm.getText().toString(),
                        editNama.getText().toString(), pilProdi, pilFakultas);
                //akhir modifikasi

                // menambahkan falidasi data
                if (!editNpm.getText().toString().equals("") && !editNama.getText().toString().equals("") &&
                        !pilProdi.equals("~Pilih Program Studi~") && !pilFakultas.equals("~Pilih Fakultas~")) {
                    databaseHandler.update(mahasiswa);
                    finish();
                } else {
                    Toast.makeText(getApplication(),"Harap periksa masukan anda!", Toast.LENGTH_SHORT).show();
                }
                //akhir modifikasi
            }
        });
    }
    
    private void getDataIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",0);
        npm = intent.getStringExtra("NPM");
        nama = intent.getStringExtra("NAMA");
        prodiLama = intent.getStringExtra("PRODI");

        // deklarasikan fakultas lama
        fakultasLama = intent.getStringExtra("FAKULTAS");
        // akhir deklarasi

        editNpm.setText(npm);
        editNama.setText(nama);
        int posSpiner = arrayAdapter.getPosition(prodiLama);
        spProdi.setSelection(posSpiner);

        // deklarasikan posisi spiner pada fakultas lama
        posSpiner = arrayAdapter2.getPosition(fakultasLama);
        spFakultas.setSelection(posSpiner);
        // akhir diskripsi
    }
}