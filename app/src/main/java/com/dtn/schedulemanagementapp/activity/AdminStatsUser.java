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

public class AdminStatsUser extends AppCompatActivity {

    SearchView svUser;
    RecyclerView rcvUser;
    UserAdapter userAdapter;
    ArrayList<User> userArrayList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_categories_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Choose user to show statistic");

        svUser = findViewById(R.id.svUser);
        rcvUser = findViewById(R.id.rcvUser);

        UserController userController = new UserController(AdminStatsUser.this);
        userArrayList = userController.getUsers();

        userAdapter = new UserAdapter( userArrayList,AdminStatsUser.this);

        userAdapter.SetOnUserItemClickListener(new IOnUserItemClickListener() {
            @Override
            public void onUserItemClick(User user) {
                Intent intent = new Intent(AdminStatsUser.this, AdminStatsActivity.class);
                intent.putExtra("selected_user", user);
                startActivity(intent);
            }
        });
        rcvUser.setLayoutManager(new LinearLayoutManager(AdminStatsUser.this));
        rcvUser.setAdapter(userAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminStatsUser.this, AdminActivity.class);
        startActivity(intent);
    }

}