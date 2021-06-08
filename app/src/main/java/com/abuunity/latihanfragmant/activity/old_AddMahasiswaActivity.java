package com.abuunity.latihanfragmant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.abuunity.latihanfragmant.api.old_MahasiswaRepository;
import com.abuunity.latihanfragmant.pojo.old_Mahasiswa;
import com.abuunity.latihanfragmant.R;

public class old_AddMahasiswaActivity extends AppCompatActivity {

    private EditText editNim, editNama;
    private Spinner spProdi;
    private Button btnTambah;
    private String pilProdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_add_mahasiswa);

        editNim = findViewById(R.id.text_input_nim);
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
            old_Mahasiswa oldMahasiswa = new old_Mahasiswa("",editNim.getText().toString(), editNama.getText().toString(), pilProdi);
            new old_MahasiswaRepository().saveMahasiswa(oldMahasiswa);
            finish();
        });
    }

}