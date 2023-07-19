package com.dtn.schedulemanagementapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.MainActivity;
import com.dtn.schedulemanagementapp.activity.NewUserActivity;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.database.UserController;
import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private ArrayList<User> userArrayList;
    private Context context;

    public UserAdapter (ArrayList<User> userArrayList, Context context) {
        this.userArrayList = userArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_user_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.lblUsername.setText(user.getUsername());
        holder.lblFullName.setText(user.getFullName());
        if (user.getRole() == 1) // 1: admin, 0: user
            holder.imgRole.setImageResource(R.drawable.user_gear_solid);
        else if (user.getRole() == 0)
            holder.imgRole.setImageResource(R.drawable.user_solid);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.user_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.iEdit) {

                            String username = String.valueOf(holder.lblUsername.getText());
                            UserController userController = new UserController(v.getContext());
                            User user = userController.getUserByUsername(username);

                            Intent intent = new Intent(v.getContext(), NewUserActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("selected_user", user);

                            intent.putExtras(bundle);
                            context.startActivity(intent);

                            return true;
                        }
                        else if (item.getItemId() == R.id.iDelete) {

                            String username = String.valueOf(holder.lblUsername.getText());
                            UserController userController = new UserController(v.getContext());
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setTitle("Delete")
                                    .setMessage("Are you sure to delete this user.\nAll schedules, categories of this user also be deleted")
                                    .setIcon(R.drawable.trash_solid)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(userController.deleteUser(username) != 0) {
                                                deleteItem(position);
                                                Toast.makeText(v.getContext(), "Delete user " + username + " successfully", Toast.LENGTH_SHORT).show();
                                            }
                                            else Toast.makeText(v.getContext(), "Something has wrong", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userArrayList != null)
            return userArrayList.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lblUsername;
        public TextView lblFullName;
        public ImageView imgRole;


        public ViewHolder(View itemView) {
            super(itemView);
            lblUsername = itemView.findViewById(R.id.lblUsername);
            lblFullName = itemView.findViewById(R.id.lblFullName);
            imgRole = itemView.findViewById(R.id.imgRole);
        }
    }

    public void setData(ArrayList<User> users) {
        this.userArrayList = users;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        userArrayList.remove(position);
        notifyItemRemoved(position);
    }
}
