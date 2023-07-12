package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dtn.schedulemanagementapp.R;

public class AdminActivity extends AppCompatActivity {

    Button btnUserM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Admin");


        btnUserM = findViewById(R.id.btnUserM);

        btnUserM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToUser = new Intent(AdminActivity.this, AdminUserActivity.class);
                startActivity(intentToUser);
            }
        });
    }
}