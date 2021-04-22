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

public class EditMahasiswaActivity extends AppCompatActivity {

    private EditText editNpm, editNama;
    private Spinner spProdi;
    private Button btnUpdate;
    private int id;
    private String pilProdi, npm, nama, prodiLama;
    private ArrayAdapter<CharSequence> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mahasiswa);

        editNpm = findViewById(R.id.text_input_npm);
        editNama = findViewById(R.id.text_input_nama);
        spProdi = findViewById(R.id.sp_prodi);
        btnUpdate = findViewById(R.id.btn_update);

        arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.daftar_prodi, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProdi.setAdapter(arrayAdapter);
        getDataIntent();
        spProdi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilProdi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                Mahasiswa mahasiswa = new Mahasiswa(id, editNpm.getText().toString(),
                        editNama.getText().toString(), pilProdi);
                databaseHandler.update(mahasiswa);
                finish();
            }
        });
    }



    private void getDataIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",0);
        npm = intent.getStringExtra("NPM");
        nama = intent.getStringExtra("NAMA");
        prodiLama = intent.getStringExtra("PRODI");

        editNpm.setText(npm);
        editNama.setText(nama);
        int posSpiner = arrayAdapter.getPosition(prodiLama);
        spProdi.setSelection(posSpiner);
    }
}