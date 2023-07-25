package com.dtn.schedulemanagementapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.NewScheduleActivity;
import com.dtn.schedulemanagementapp.database.ScheduleController;
import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private ArrayList<Schedule> scheduleArrayList;
    private Context context;

    public ScheduleAdapter (ArrayList<Schedule> scheduleArrayList, Context context) {
        this.scheduleArrayList = scheduleArrayList;
        this.context = context;
    }

    public void setData(ArrayList<Schedule> scheduleArrayList) {
        this.scheduleArrayList = scheduleArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_schedule_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = scheduleArrayList.get(position);
        ScheduleController sCtrl = new ScheduleController(this.context);
        holder.lblScheduleName.setText(schedule.getName());
        holder.lblScheduleStartDate.setText(schedule.getStartDate());
        holder.lblScheduleEndDate.setText(schedule.getEndDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, NewScheduleActivity.class);
                i.putExtra("ScheduleID", schedule.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (scheduleArrayList != null)
            return scheduleArrayList.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lblScheduleName;
        public TextView lblScheduleStartDate;
        public TextView lblScheduleEndDate;


        public ViewHolder(View itemView) {
            super(itemView);
            lblScheduleName = itemView.findViewById(R.id.lblScheduleName);
            lblScheduleStartDate = itemView.findViewById(R.id.lblScheduleStartDate);
            lblScheduleEndDate = itemView.findViewById(R.id.lblScheduleEndDate);
        }
    }
}
