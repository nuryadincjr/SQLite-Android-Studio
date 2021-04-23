package com.abuunity.latihanfragmant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddMahasiswaActivity extends AppCompatActivity {

    private EditText editNpm, editNama;

    // menambahkan variabel spinner spFakultas
    private Spinner spProdi, spFakultas;

    private Button btnTambah;

    // menambahkan variabel pilih Fakultas
    private String pilProdi, pilFakultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mahasiswa);

        editNpm = findViewById(R.id.text_input_npm);
        editNama = findViewById(R.id.text_input_nama);
        spProdi = findViewById(R.id.sp_prodi);

        // panggil Spinner spFakultas pada activity_add_mahasiswa
        spFakultas = findViewById(R.id.sp_fakultas);
        // akhir modifikasi

        btnTambah = findViewById(R.id.btn_tambah);

        final ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.daftar_prodi,
                        android.R.layout.simple_spinner_item);
        spProdi.setAdapter(adapter);
        spProdi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilProdi=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // tambahkan sebuah adapter baru untuk menset daftar Fakultas
        final ArrayAdapter<CharSequence> adapter1 =
                ArrayAdapter.createFromResource(this, R.array.daftar_fakultas,
                        android.R.layout.simple_spinner_item);
        spFakultas.setAdapter(adapter1);
        spFakultas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilFakultas=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // akhir modifikasi

        btnTambah.setOnClickListener(v -> {
            DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());

            // modifikasi dan tambahkan pilFakultas sesuai dengan index konstruktor yang dibuat
            Mahasiswa mahasiswa = new Mahasiswa(editNpm.getText().toString(),
                    editNama.getText().toString(), pilProdi, pilFakultas);

            // menambahkan falidasi data
            if (!editNpm.getText().toString().equals("") && !editNama.getText().toString().equals("") &&
                    !pilProdi.equals("~Pilih Program Studi~") && !pilFakultas.equals("~Pilih Fakultas~")) {
                databaseHandler.save(mahasiswa);
                finish();
            } else {
                Toast.makeText(getApplication(),"Harap periksa masukan anda!", Toast.LENGTH_SHORT).show();
            }
            //akhir modifikasi
        });
    }
}