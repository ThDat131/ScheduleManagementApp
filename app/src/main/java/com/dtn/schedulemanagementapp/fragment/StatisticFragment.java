package com.dtn.schedulemanagementapp.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.database.ScheduleControlller;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */



public class StatisticFragment extends Fragment {

    TextView txtFrom;
    TextView txtTo;
    BarChart barChart;
    BarChart barChart1;

    ArrayList barArrayList;

    ArrayList barArrayList1;

    ArrayList labelBar;
    ArrayList labelBar1;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_statistic, container, false);

        txtFrom = v.findViewById(R.id.txtFrom);
        txtTo = v.findViewById(R.id.txtTo);
        barChart = v.findViewById(R.id.barChart);
        barChart1 = v.findViewById(R.id.barChart1);

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

        return v;


    }

    public void getData1(){

        if (txtFrom.getText().toString().isEmpty() || txtTo.getText().toString().isEmpty()) {
            LocalDate today = LocalDate.now();
            String dateFormatted = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            txtFrom.setText("01/07/2023");
            txtTo.setText("01/08/2023");

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
        ScheduleControlller scheduleControlller = new ScheduleControlller(this.getContext());
        datas = scheduleControlller.StatsByDate("admin", StringStartDate, StringEndDate);
        int index = 0;

        for (ScheduleStatsByCategory data : datas) {
            barArrayList1.add(new BarEntry(index, data.getQty(), data.getName()));
            labelBar1.add(CalendarUtils.StringToString(data.getName(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy"));
            index++;

        }
    }

    public void getData(){
        barArrayList = new ArrayList<>();
        labelBar = new ArrayList<String>();
        ArrayList<ScheduleStatsByCategory> datas = new ArrayList<>();
        ScheduleControlller scheduleControlller = new ScheduleControlller(this.getContext());
        datas = scheduleControlller.StatsByCategory("admin");
        int index = 0;

        for (ScheduleStatsByCategory data : datas) {
            barArrayList.add(new BarEntry(index, data.getQty(), data.getName()));
            labelBar.add(data.getName());
            index++;

        }
    }
}