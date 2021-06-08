package com.abuunity.latihanfragmant;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.abuunity.latihanfragmant.fragment.old_AboutFragment;
import com.abuunity.latihanfragmant.fragment.old_FavoritFragment;
import com.abuunity.latihanfragmant.fragment.old_HomeFragment;
import com.abuunity.latihanfragmant.fragment.old_MahasiswaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class old_MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_main);

        getFragmentPage(new old_HomeFragment());

        bottomNavigationItemView = findViewById(R.id.bottom_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        getFragmentPage(new old_HomeFragment());
                        break;
                    case R.id.menu_favorite:
                        getFragmentPage(new old_FavoritFragment());
                        break;
                    case R.id.menu_about:
                        getFragmentPage(new old_AboutFragment());
                        break;
                    case R.id.menu_mahasiswa:
                        getFragmentPage(new old_MahasiswaFragment());
                        break;
                }
                return true;
            }
        });
    }

    private boolean getFragmentPage(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return true;
    }

}