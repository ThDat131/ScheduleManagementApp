package com.dtn.schedulemanagementapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.adapter.UserAdapter;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.database.UserController;
import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.user_interface.IOnUserItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class AdminUserActivity extends AppCompatActivity {

    SearchView svUser;
    RecyclerView rcvUser;
    FloatingActionButton btnAddNewUser;
    UserAdapter userAdapter;
    ArrayList<User> userArrayList = new ArrayList<User>();

    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Management");

        svUser = findViewById(R.id.svUser);
        rcvUser = findViewById(R.id.rcvUser);
        btnAddNewUser = findViewById(R.id.btnAddNewUser);

        UserController userController = new UserController(AdminUserActivity.this);
        userArrayList = userController.getUsers();

        userAdapter = new UserAdapter( userArrayList,AdminUserActivity.this);

        userAdapter.SetOnUserItemClickListener(new IOnUserItemClickListener() {
            @Override
            public void onUserItemClick(User user) {
                int pos = userAdapter.getPosUser(user);
                if (pos != -1) {
                    RecyclerView.ViewHolder viewHolder =  rcvUser.findViewHolderForAdapterPosition(pos);
                    showPopup(viewHolder.itemView, user);
                }
            }
        });
        rcvUser.setLayoutManager(new LinearLayoutManager(AdminUserActivity.this));
        rcvUser.setAdapter(userAdapter);

        btnAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminUserActivity.this, NewUserActivity.class);
                startActivity(intent);
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

    public void showPopup(View view, User user) {
        PopupMenu popupMenu = new PopupMenu(AdminUserActivity.this, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.iEdit) {

                    Intent intent = new Intent(view.getContext(), NewUserActivity.class);
                    intent.putExtra("selected_user", user);
                    startActivity(intent);

                    return true;
                }
                else if (item.getItemId() == R.id.iDelete) {
                    userController = new UserController(view.getContext());
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Delete")
                            .setMessage("Are you sure to delete this user.\nAll schedules, categories of this user also be deleted")
                            .setIcon(R.drawable.trash_solid)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(userController.deleteUser(user.getUsername()) != 0) {
                                        int pos = userAdapter.getPosUser(user);
                                        userAdapter.deleteItem(pos);
                                        Toast.makeText(view.getContext(), "Delete user " + user.getUsername() + " successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(view.getContext(), "Something has wrong", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create().show();


                    return true;
                }
                return false;
            }

        });
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.user_menu, popupMenu.getMenu());
        popupMenu.show();
    }


}