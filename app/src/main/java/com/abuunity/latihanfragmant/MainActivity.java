package com.abuunity.latihanfragmant;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.abuunity.latihanfragmant.activity.PostActivity;
import com.abuunity.latihanfragmant.fragment.HomeFragment;
import com.abuunity.latihanfragmant.fragment.NotificationFragment;
import com.abuunity.latihanfragmant.fragment.ProfileFragment;
import com.abuunity.latihanfragmant.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentPage(new HomeFragment());

        bottomNavigationView = findViewById(R.id.button_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getFragmentPage(new HomeFragment());
                        break;
                    case R.id.nav_search:
                        getFragmentPage(new SearchFragment());
                        break;
                    case R.id.nav_add:
                        startActivity(new Intent(MainActivity.this, PostActivity.class));
                        break;
                    case R.id.nav_notification:
                        getFragmentPage(new NotificationFragment());
                        break;
                    case R.id.nav_profil:
                        getFragmentPage(new ProfileFragment());
                        break;
                }
                return true;
            }
        });

        getBundle();
    }

    private boolean getFragmentPage(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return true;
    }

    private void getBundle () {
        Bundle intentBundle = getIntent().getExtras();
        if(intentBundle != null) {
            String commentProfileID = intentBundle.getString("publisherId");
            getSharedPreferences("PROFILE", MODE_PRIVATE).edit().putString("profileId", commentProfileID).apply();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_profil);
        } else{
            getFragmentPage( new HomeFragment());
        }
    }
}