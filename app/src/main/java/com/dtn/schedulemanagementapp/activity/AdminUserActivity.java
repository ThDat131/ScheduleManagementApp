package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.adapter.UserAdapter;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.models.User;

import java.util.ArrayList;
import java.util.Date;

public class AdminUserActivity extends AppCompatActivity {

    SearchView svUser;
    RecyclerView rcvUser;
    Button btnAddNewUser;
    UserAdapter userAdapter;
    ArrayList<User> userArrayList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Management");

        svUser = findViewById(R.id.svUser);
        rcvUser = findViewById(R.id.rcvUser);
        btnAddNewUser = findViewById(R.id.btnAddNewUser);

        userAdapter = new UserAdapter( userArrayList,AdminUserActivity.this);
        rcvUser.setLayoutManager(new LinearLayoutManager(AdminUserActivity.this));
        rcvUser.setAdapter(userAdapter);

        DBHelper dbHelper = DBHelper.getInstance(AdminUserActivity.this);
        userAdapter.setData(dbHelper.getUsers());

        btnAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminUserActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });


    }


}