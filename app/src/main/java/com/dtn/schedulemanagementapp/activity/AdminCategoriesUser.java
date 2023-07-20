package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.adapter.UserAdapter;
import com.dtn.schedulemanagementapp.database.UserController;
import com.dtn.schedulemanagementapp.models.User;

import java.util.ArrayList;

public class AdminCategoriesUser extends AppCompatActivity {

    SearchView svUser;
    RecyclerView rcvUser;
    Button btnAddNewUser;
    UserAdapter userAdapter;
    ArrayList<User> userArrayList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_categories_user);

        svUser = findViewById(R.id.svUser);
        rcvUser = findViewById(R.id.rcvUser);
        btnAddNewUser = findViewById(R.id.btnAddNewUser);

        UserController userController = new UserController(AdminCategoriesUser.this);
        userArrayList = userController.getUsers();

        userAdapter = new UserAdapter( userArrayList,AdminCategoriesUser.this);
        rcvUser.setLayoutManager(new LinearLayoutManager(AdminCategoriesUser.this));
        rcvUser.setAdapter(userAdapter);
    }
}