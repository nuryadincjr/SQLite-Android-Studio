package com.abuunity.latihanfragmant.api;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.abuunity.latihanfragmant.pojo.old_Mahasiswa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class old_MahasiswaRepository {

    private FirebaseFirestore firebaseFirestore;

    public old_MahasiswaRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<ArrayList<old_Mahasiswa>> getAllMahasiswa() {
        ArrayList<old_Mahasiswa> oldMahasiswas = new ArrayList<>();;
        final MutableLiveData<ArrayList<old_Mahasiswa>> mahasiswaMutableLiveData = new MutableLiveData<>();

        firebaseFirestore.collection("mahasiswa")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        old_Mahasiswa oldMahasiswa = document.toObject(old_Mahasiswa.class);
                        oldMahasiswa.setId(document.getId());
                        oldMahasiswas.add(oldMahasiswa);
                    }
                    mahasiswaMutableLiveData.postValue(oldMahasiswas);
                }
                else
                    mahasiswaMutableLiveData.setValue(null);

            }
        });

        return mahasiswaMutableLiveData;
    }

    public void saveMahasiswa(old_Mahasiswa oldMahasiswa) {

        firebaseFirestore.collection("oldMahasiswa").add(oldMahasiswa).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        });

    }

    public void editMahasiswa(old_Mahasiswa oldMahasiswa) {
        Map<String, Object> data = new HashMap<>();
        data.put("nim", oldMahasiswa.getNim());
        data.put("nama", oldMahasiswa.getNama());
        data.put("prodi", oldMahasiswa.getProdi());

        firebaseFirestore.collection("oldMahasiswa")
                .document(oldMahasiswa.getId()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void deleteMahasiswa(String id) {
        firebaseFirestore.collection("mahasiswa").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

}
