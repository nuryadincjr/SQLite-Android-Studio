package com.abuunity.latihanfragmant.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.abuunity.latihanfragmant.api.old_MahasiswaRepository;
import com.abuunity.latihanfragmant.pojo.old_Mahasiswa;

import java.util.ArrayList;

public class old_MainViewModel extends AndroidViewModel {

    private old_MahasiswaRepository oldMahasiswaRepository;
    private MutableLiveData<ArrayList<old_Mahasiswa>> mahasiswaMutableLive;

    public old_MainViewModel(@NonNull Application application) {
        super(application);
        oldMahasiswaRepository = new old_MahasiswaRepository();

    }

    public MutableLiveData<ArrayList<old_Mahasiswa>> getMahasiswa() {
        return mahasiswaMutableLive= oldMahasiswaRepository.getAllMahasiswa();
    }



}
