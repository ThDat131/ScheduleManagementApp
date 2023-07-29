package com.dtn.schedulemanagementapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.NewScheduleActivity;
import com.dtn.schedulemanagementapp.database.ScheduleController;
import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.util.ArrayList;

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
        holder.lblScheduleStartDate.setText(CalendarUtils.StringToString(schedule.getStartDate(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss"));
        holder.lblScheduleEndDate.setText(CalendarUtils.StringToString(schedule.getEndDate(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, NewScheduleActivity.class);
                i.putExtra("ScheduleID", schedule.getId());
                context.startActivity(i);
            }
        });
        holder.itemView.findViewById(R.id.btnDeleteSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Do you want to delete this schedule ?");
                builder.setTitle("Alert !");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    if(sCtrl.deleteSchedule(schedule) > 0) {
                        scheduleArrayList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        Toast.makeText(context, "Delete schedule successfully", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(context, "Something has wrong", Toast.LENGTH_SHORT).show();

                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
