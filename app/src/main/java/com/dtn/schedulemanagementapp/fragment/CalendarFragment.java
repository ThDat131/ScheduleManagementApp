package com.dtn.schedulemanagementapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.MainActivity;
import com.dtn.schedulemanagementapp.adapter.ScheduleAdapter;
import com.dtn.schedulemanagementapp.models.Schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    private CalendarView calendaView;
    private RecyclerView rcvSchedules;
    private ScheduleAdapter scheduleAdapter;
    private ArrayList<Schedule>  scheduleArrayList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        scheduleArrayList = new ArrayList<Schedule>();
        scheduleArrayList.add(new Schedule(1, "Go to school", "",
                new Date(2023, 7, 4),
                new Date(2023, 7, 5), 1, 1));
        scheduleArrayList.add(new Schedule(1, "Go to school", "",
                new Date(2023, 7, 4),
                new Date(2023, 7, 5), 1, 1));
        scheduleArrayList.add(new Schedule(1, "Go to school", "",
                new Date(2023, 7, 4),
                new Date(2023, 7, 5), 1, 1));
        scheduleArrayList.add(new Schedule(1, "Go to school", "",
                new Date(2023, 7, 4),
                new Date(2023, 7, 5), 1, 1));
        scheduleArrayList.add(new Schedule(1, "Go to school", "",
                new Date(2023, 7, 4),
                new Date(2023, 7, 5), 1, 1));

        rcvSchedules = view.findViewById(R.id.rcvSchedules);
        scheduleAdapter = new ScheduleAdapter(scheduleArrayList, getContext());

        rcvSchedules.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvSchedules.setAdapter(scheduleAdapter);

//        calendaView = view.findViewById(R.id.calendarView);
//        calendaView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.putExtra("frag",R.id.category);
//                startActivity(intent);
//                ((Activity) getActivity()).overridePendingTransition(0, 0);
//            }
//        });

        return view;
    }

}