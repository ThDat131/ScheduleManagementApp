package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.adapter.ScheduleAdapter;
import com.dtn.schedulemanagementapp.database.ScheduleController;
import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.models.stats.ScheduleStatsByCategory;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdminStatsActivity extends AppCompatActivity {

    TextView txtFrom;
    TextView txtTo;
    BarChart barChart;
    BarChart barChart1;
    Button btnStats;

    ArrayList barArrayList = new ArrayList<>();

    ArrayList barArrayList1 = new ArrayList<>();

    ArrayList labelBar = new ArrayList<>();
    ArrayList labelBar1 = new ArrayList<>();

    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_stats);

        User currUser = new User();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selected_user")) {
            currUser = (User) intent.getSerializableExtra("selected_user");
            username = currUser.getUsername();

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Statistic of " + username);

        txtFrom = findViewById(R.id.txtFrom);
        txtTo = findViewById(R.id.txtTo);
        barChart = findViewById(R.id.barChart);
        barChart1 = findViewById(R.id.barChart1);
        btnStats = findViewById(R.id.btnStats);

        txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                        txtFrom.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                } , year, month, day);

                datePickerDialog.show();
            }
        });

        txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                        txtTo.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                } , year, month, day);

                datePickerDialog.show();
            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();

                String[] labelsArr = new String[labelBar.size()];
                labelBar.toArray(labelsArr);


                BarDataSet barDataSet = new BarDataSet(barArrayList, "Schedule by Category");

                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(16);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsArr));
                xAxis.setLabelCount(1);
                YAxis yAxisL = barChart.getAxisLeft();
                yAxisL.setLabelCount(1);

                YAxis yAxisR = barChart.getAxisRight();
                yAxisR.setEnabled(false);

                BarData barData = new BarData(barDataSet);

                barChart.setData(barData);

                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                barDataSet.setValueTextColor(Color.BLACK);

                barDataSet.setValueTextSize(16);

                barChart.getDescription().setEnabled(false);

                // chart 2

                getData1();

                String[] labelsArr1 = new String[labelBar1.size()];
                labelBar1.toArray(labelsArr1);

                BarDataSet barDataSet1 = new BarDataSet(barArrayList1, "Schedule by date");

                XAxis xAxis1 = barChart1.getXAxis();
                xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis1.setTextSize(16);
                xAxis1.setValueFormatter(new IndexAxisValueFormatter(labelsArr1));
                xAxis1.setLabelCount(1);
                YAxis yAxisL1 = barChart1.getAxisLeft();
                yAxisL1.setLabelCount(1);

                YAxis yAxisR1 = barChart1.getAxisRight();
                yAxisR1.setEnabled(false);

                BarData barData1 = new BarData(barDataSet1);

                barChart1.setData(barData1);

                barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                barDataSet1.setValueTextColor(Color.BLACK);

                barDataSet1.setValueTextSize(16);

                barChart1.getDescription().setEnabled(false);

                barChart.animateXY(1000, 1000);
                barChart1.animateXY(1000, 1000);
                barChart.invalidate();
                barChart1.invalidate();


            }
        });
    }

    public void getData1(){

        if (txtFrom.getText().toString().isEmpty() || txtTo.getText().toString().isEmpty()) {
            LocalDate today = LocalDate.now();
            String dateFormatted = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            txtFrom.setText(dateFormatted);
            txtTo.setText(dateFormatted);
        }

        String startDate = txtFrom.getText().toString();
        String endDate = txtTo.getText().toString();

        Date dateFrom = CalendarUtils.StringToDate(startDate, "dd/MM/yyyy");
        Date dateTo = CalendarUtils.StringToDate(endDate, "dd/MM/yyyy");

        String StringStartDate = CalendarUtils.DateToString(dateFrom, "yyyy-MM-dd");
        String StringEndDate = CalendarUtils.DateToString(dateTo, "yyyy-MM-dd");

        barArrayList1 = new ArrayList<>();
        labelBar1 = new ArrayList<String>();
        ArrayList<ScheduleStatsByCategory> datas = new ArrayList<>();
        ScheduleController scheduleController = new ScheduleController(AdminStatsActivity.this);
        datas = scheduleController.StatsByDate(username, StringStartDate, StringEndDate);
        int index = 0;

        for (ScheduleStatsByCategory data : datas) {
            barArrayList1.add(new BarEntry(index, data.getQty(), data.getName()));
            labelBar1.add(CalendarUtils.StringToString(data.getName(), "yyyy-MM-dd", "dd/MM/yyyy"));
            index++;

        }
    }

    public void getData(){
        barArrayList = new ArrayList<>();
        labelBar = new ArrayList<String>();
        ArrayList<ScheduleStatsByCategory> datas = new ArrayList<>();
        ScheduleController scheduleController = new ScheduleController(AdminStatsActivity.this);
        datas = scheduleController.StatsByCategory(username);
        int index = 0;

        for (ScheduleStatsByCategory data : datas) {
            barArrayList.add(new BarEntry(index, data.getQty(), data.getName()));
            labelBar.add(data.getName());
            index++;

        }
    }
}