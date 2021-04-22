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

public class AddMahasiswaActivity extends AppCompatActivity {

    private EditText editNpm, editNama;
    private Spinner spProdi;
    private Button btnTambah;
    private String pilProdi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mahasiswa);

        editNpm = findViewById(R.id.text_input_npm);
        editNama = findViewById(R.id.text_input_nama);
        spProdi = findViewById(R.id.sp_prodi);
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

        btnTambah.setOnClickListener(v -> {
            DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
            Mahasiswa mahasiswa = new Mahasiswa(editNpm.getText().toString(),
                    editNama.getText().toString(), pilProdi);
            databaseHandler.save(mahasiswa);
            finish();
        });
    }
}