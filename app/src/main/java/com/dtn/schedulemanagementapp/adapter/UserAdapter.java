package com.dtn.schedulemanagementapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.MainActivity;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.models.User;

import java.util.ArrayList;

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

                            DBHelper dbHelper = DBHelper.getInstance(v.getContext());
                            User user = dbHelper.getUserByUsername(username);
                            Toast.makeText(v.getContext(), user.toString(), Toast.LENGTH_SHORT).show();

                            return true;
                        }
                        else if (item.getItemId() == R.id.iDelete) {
                            return false;
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
}
