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
import com.dtn.schedulemanagementapp.user_interface.IOnUserItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private ArrayList<User> userArrayList;
    private Context context;

    public IOnUserItemClickListener clickListener;

    public void SetOnUserItemClickListener(IOnUserItemClickListener listener) {
        this.clickListener = listener;
    }

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
        final User user = userArrayList.get(position);
        holder.lblUsername.setText(user.getUsername());
        holder.lblFullName.setText(user.getFullName());
        if (user.getRole() == 1) // 1: admin, 0: user
            holder.imgRole.setImageResource(R.drawable.user_gear_solid);
        else if (user.getRole() == 0)
            holder.imgRole.setImageResource(R.drawable.user_solid);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onUserItemClick(user);
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

    public int getPosUser(User user) {
        String username = user.getUsername();
        String usernameTemp = "";
        for (int i = 0; i < userArrayList.size(); i++) {
            usernameTemp = userArrayList.get(i).getUsername();
            if (usernameTemp.equals(username)) {
                return i;
            }
        }
        return -1;
    }

}
