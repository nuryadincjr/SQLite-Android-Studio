package com.abuunity.latihanfragmant.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.pojo.old_Team;

public class old_DescriptionActivity extends AppCompatActivity {
    private TextView name, desc;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        old_Team oldTeam = getIntent().getParcelableExtra("TEAMS");
        name = findViewById(R.id.textName);
        logo = findViewById(R.id.imgDescription);
        desc = findViewById(R.id.textDescription);

        name.setText(oldTeam.getNama());
        logo.setImageResource(Integer.parseInt(oldTeam.getLogo()));
        desc.setText(oldTeam.getDescription());
    }
}