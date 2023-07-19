package com.dtn.schedulemanagementapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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

        DBHelper dbHelper = DBHelper.getInstance(AdminUserActivity.this);
        userArrayList = dbHelper.getUsers();

        userAdapter = new UserAdapter( userArrayList,AdminUserActivity.this);
        rcvUser.setLayoutManager(new LinearLayoutManager(AdminUserActivity.this));
        rcvUser.setAdapter(userAdapter);



        btnAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminUserActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });

        rcvUser.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        svUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });

    }

    private void fileList(String newText) {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : userArrayList) {
            if (user.getUsername().toLowerCase().contains(newText.toLowerCase())) {
                filteredUsers.add(user);
            }
        }

        if (filteredUsers.isEmpty()) {
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
            userAdapter.setData(new ArrayList<>());
        }
        else userAdapter.setData(filteredUsers);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminUserActivity.this, AdminActivity.class);
        startActivity(intent);
    }


}