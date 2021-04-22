package com.abuunity.latihanfragmant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {
    private TextView name, desc;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Team team = getIntent().getParcelableExtra("TEAMS");
        name = findViewById(R.id.textName);
        logo = findViewById(R.id.imgDescription);
        desc = findViewById(R.id.textDescription);

        name.setText(team.getNama());
        logo.setImageResource(Integer.parseInt(team.getLogo()));
        desc.setText(team.getDescription());
    }
}