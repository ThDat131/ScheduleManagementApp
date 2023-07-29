package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.adapter.UserAdapter;
import com.dtn.schedulemanagementapp.database.UserController;
import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.user_interface.IOnUserItemClickListener;

import java.util.ArrayList;

public class AdminSchedulesUser extends AppCompatActivity {
    SearchView svUser;
    RecyclerView rcvUser;
    UserAdapter userAdapter;
    ArrayList<User> userArrayList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedules_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Choose user to show schedules");

        svUser = findViewById(R.id.svUser);
        rcvUser = findViewById(R.id.rcvUser);

        UserController userController = new UserController(AdminSchedulesUser.this);
        userArrayList = userController.getUsers();

        userAdapter = new UserAdapter( userArrayList,AdminSchedulesUser.this);

        userAdapter.SetOnUserItemClickListener(new IOnUserItemClickListener() {
            @Override
            public void onUserItemClick(User user) {
                Intent intent = new Intent(AdminSchedulesUser.this, AdminSchedulesActivity.class);
                intent.putExtra("selected_user", user);
                startActivity(intent);
            }
        });
        rcvUser.setLayoutManager(new LinearLayoutManager(AdminSchedulesUser.this));
        rcvUser.setAdapter(userAdapter);
    }
}