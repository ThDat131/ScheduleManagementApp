package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.adapter.ScheduleAdapter;
import com.dtn.schedulemanagementapp.database.ScheduleController;
import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminSchedulesActivity extends AppCompatActivity {
    private RecyclerView rcvSchedules;
    private FloatingActionButton btnNewSchedule;
    ScheduleController schCtrl;
    ScheduleAdapter scheduleAdapter;
    ArrayList<Schedule> scheduleArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedules);

        User currUser = new User();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selected_user")) {
            currUser = (User) intent.getSerializableExtra("selected_user");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Schedules of " + currUser.getUsername());

        rcvSchedules = findViewById(R.id.rcvSche);
        btnNewSchedule = findViewById(R.id.btnAddNewSchedule);

        schCtrl = new ScheduleController(AdminSchedulesActivity.this);

        scheduleArrayList = schCtrl.getSchedulesByUsername(currUser.getUsername());
        scheduleAdapter = new ScheduleAdapter(scheduleArrayList, AdminSchedulesActivity.this);
        rcvSchedules.setLayoutManager(new LinearLayoutManager(AdminSchedulesActivity.this));
        rcvSchedules.setAdapter(scheduleAdapter);

        btnNewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminSchedulesActivity.this, NewScheduleActivity.class);
                startActivity(i);
            }
        });
    }
}