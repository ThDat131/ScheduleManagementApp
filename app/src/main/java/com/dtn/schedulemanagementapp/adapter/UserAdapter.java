package com.dtn.schedulemanagementapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.schedulemanagementapp.R;
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
            holder.imgRole.setImageResource(R.drawable.user_solid);
        else if (user.getRole() == 0)
            holder.imgRole.setImageResource(R.drawable.user_gear_solid);
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
}
