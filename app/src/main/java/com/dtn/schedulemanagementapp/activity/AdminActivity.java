package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dtn.schedulemanagementapp.R;

public class AdminActivity extends AppCompatActivity {

    Button btnUserM;
    Button btnCategoryM;
    Button btnScheduleM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Admin");


        btnUserM = findViewById(R.id.btnUserM);
        btnCategoryM = findViewById(R.id.btnCategoryM);
        btnScheduleM = findViewById(R.id.btnScheduleM);

        btnUserM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToUser = new Intent(AdminActivity.this, AdminUserActivity.class);
                startActivity(intentToUser);
            }
        });

        btnCategoryM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToUser = new Intent(AdminActivity.this, AdminCategoriesUser.class);
                startActivity(intentToUser);
            }
        });

        btnScheduleM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToUser = new Intent(AdminActivity.this, AdminSchedulesUser.class);
                startActivity(intentToUser);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
    }
}