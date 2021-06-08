package com.abuunity.latihanfragmant.activity;

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

import com.abuunity.latihanfragmant.pojo.old_Mahasiswa;
import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.sql.old_DatabaseHandler;

public class old_EditMahasiswaActivity extends AppCompatActivity {

    private EditText editNim, editNama;
    private Spinner spProdi;
    private Button btnUpdate;
    private String id;

    private String pilProdi, nim, nama, prodiLama;

    private ArrayAdapter<CharSequence> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_edit_mahasiswa);

        editNim = findViewById(R.id.text_input_nim);
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
                System.out.println(pilProdi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_DatabaseHandler oldDatabaseHandler = new old_DatabaseHandler(getApplicationContext());

                old_Mahasiswa oldMahasiswa = new old_Mahasiswa(id, editNim.getText().toString(),
                        editNama.getText().toString(), pilProdi);

                // menambahkan falidasi data
                if (!editNim.getText().toString().equals("") && !editNama.getText().toString().equals("") &&
                        !pilProdi.equals("~Pilih Program Studi~")) {
                    oldDatabaseHandler.update(oldMahasiswa);
                    finish();
                } else {
                    Toast.makeText(getApplication(),"Harap periksa masukan anda!", Toast.LENGTH_SHORT).show();
                }
                //akhir modifikasi
            }
        });
    }

    private void getDataIntent()
    {
        Intent intent = getIntent();
        id= intent.getStringExtra("ID");
        nim=intent.getStringExtra("NIM");
        nama=intent.getStringExtra("NAMA");
        prodiLama=intent.getStringExtra("PRODI");
        editNim.setText(nim);
        editNama.setText(nama);
        int posSpiner= arrayAdapter.getPosition(prodiLama);
        spProdi.setSelection(posSpiner);
    }
}