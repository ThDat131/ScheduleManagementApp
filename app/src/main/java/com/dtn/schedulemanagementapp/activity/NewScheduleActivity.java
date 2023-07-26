package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.adapter.ScheduleAdapter;
import com.dtn.schedulemanagementapp.database.ReminderController;
import com.dtn.schedulemanagementapp.database.ScheduleController;
import com.dtn.schedulemanagementapp.models.Reminder;
import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class NewScheduleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] cates = {"Home", "Work", "Holidays", "Tested"};
    Schedule schedule;
    ArrayList<Reminder> alarms = new ArrayList<>();
    LinearLayout thisLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);

        Toolbar topToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        ActionBar actionBar = getSupportActionBar();;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Schedule");
        thisLayout = findViewById(R.id.listAlarm);

        ScheduleController sCtrl = new ScheduleController(this);
        ReminderController rCtrl = new ReminderController(this);

        Spinner spinCates = findViewById(R.id.SpinnerCate);
        spinCates.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cates);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCates.setAdapter(ad);

        Button btnAddAlarm = findViewById(R.id.buttonAddAlarm);
        EditText editTextEvName = findViewById(R.id.editTextEvName);
        EditText editTextStartDate = findViewById(R.id.editTextStartDate);
        EditText editTextStartTime = findViewById(R.id.editTextStartTime);
        EditText editTextEndDate = findViewById(R.id.editTextEndDate);
        EditText editTextEndTime = findViewById(R.id.editTextEndTime);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        ImageButton buttonHelp = findViewById(R.id.helpButton);

        editTextEvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getSupportActionBar().setTitle(editTextEvName.getText().toString());
            }
        });
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                        editTextStartDate.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                        editTextEndDate.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        editTextStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(NewScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        Time time = null;
                        try {
                            time = new Time(formatter.parse(hour+":"+min).getTime());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        editTextStartTime.setText(time.toString().substring(0,5));
                    }
                }, hour, min, true);
                timePickerDialog.show();
            }
        });

        editTextEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(NewScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        Time time = null;
                        try {
                            time = new Time(formatter.parse(hour+":"+min).getTime());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        editTextEndTime.setText(time.toString().substring(0,5));
                    }
                }, hour, min, true);
                timePickerDialog.show();
            }
        });

        btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent i = new Intent(NewScheduleActivity.this, NewAlarmActivity.class);
//                        schedule = new Schedule(editTextEvName.getText().toString(),
//                                editTextDescription.getText().toString(),
//                                CalendarUtils.StringToDate(editTextStartDate.getText().toString(),"dd/MM/yyyy"),
//                                CalendarUtils.StringToDate(editTextEndDate.getText().toString(),"dd/MM/yyyy"),
//                                getIntent().getExtras().getString("username","admin"),
//                                spinCates.getId());
//                        sCtrl.addNewUser(schedule);
//                        i.putExtra("scheID", schedule.getId());
                        startActivity(i);
                    }
        });

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = CalendarUtils.StringToString(editTextStartDate.getText().toString() + " " + editTextStartTime.getText().toString() + ":00", "dd/MM/yyyy HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                String end = CalendarUtils.StringToString(editTextEndDate.getText().toString() + " " + editTextEndTime.getText().toString() + ":00", "dd/MM/yyyy HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                schedule = new Schedule(editTextEvName.getText().toString(),
                        editTextDescription.getText().toString(),
                        start,
                        end,
//                        getIntent().getExtras().getString("username","admin"),
                        "admin",
                        spinCates.getId());
                sCtrl.addNewSchedule(schedule);
                if (spinCates.isEnabled()==true) {
                    buttonHelp.setImageResource(R.drawable.pen_solid);
                    editTextEvName.setEnabled(false);
                    spinCates.setEnabled(false);
                    editTextStartDate.setEnabled(false);
                    editTextStartTime.setEnabled(false);
                    editTextEndDate.setEnabled(false);
                    editTextEndTime.setEnabled(false);
                    btnAddAlarm.setEnabled(false);
                    editTextDescription.setEnabled(false);

                } else if (spinCates.isEnabled()==false) {
                    buttonHelp.setImageResource(R.drawable.baseline_check_24);
                    editTextEvName.setEnabled(true);
                    spinCates.setEnabled(true);
                    editTextStartDate.setEnabled(true);
                    editTextStartTime.setEnabled(true);
                    editTextEndDate.setEnabled(true);
                    editTextEndTime.setEnabled(true);
                    btnAddAlarm.setEnabled(true);
                    editTextDescription.setEnabled(true);
                }
            }
        });

        Intent i = getIntent();
        int check = i.getIntExtra("ScheduleID",0);
        if(check!=0) {
            try {
                Schedule scheduleEdit = sCtrl.getScheduleByScheduleID(check);
                buttonHelp.setImageResource(R.drawable.baseline_check_24);
                String start = CalendarUtils.StringToString(scheduleEdit.getStartDate(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss");
                String end = CalendarUtils.StringToString(scheduleEdit.getEndDate(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss");
                editTextEvName.setEnabled(true);
                editTextEvName.setText(scheduleEdit.getName());
                spinCates.setEnabled(true);
                editTextStartDate.setEnabled(true);
                editTextStartDate.setText(start.substring(0, 10));
                editTextStartTime.setEnabled(true);
                editTextStartTime.setText(start.substring(11, 16));
                editTextEndDate.setEnabled(true);
                editTextEndDate.setText(scheduleEdit.getEndDate().substring(0, 10));
                editTextEndTime.setEnabled(true);
                editTextEndTime.setText(scheduleEdit.getEndDate().substring(11, 16));
                btnAddAlarm.setEnabled(true);
                fetchAlarms();
                editTextDescription.setEnabled(true);
                editTextDescription.setText(scheduleEdit.getNote());
                alarms = rCtrl.getReminderByScheduleID(check);
                buttonHelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String start = CalendarUtils.StringToDate(editTextStartDate.getText().toString(), "yyyy-MM-dd") + " " + editTextStartTime.getText().toString() + ":00";
                        String end = CalendarUtils.StringToDate(editTextEndDate.getText().toString(), "yyyy-MM-dd") + " " + editTextEndTime.getText().toString() + ":00";
                        Schedule scheduleEdited = new Schedule(editTextEvName.getText().toString(),
                                editTextDescription.getText().toString(),
                                start,
                                end,
//                        getIntent().getExtras().getString("username","admin"),
                                "admin",
                                spinCates.getId());
                        sCtrl.updateSchedule(scheduleEdited);
                    }
                });
            } catch (ParseException e) {
                Toast.makeText(this,"Welp!",Toast.LENGTH_LONG);
            }
        }
        else {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAlarms();
    }

    private void fetchAlarms(){
        for (int i = 0; i < alarms.size(); i++) {
            TextView alarmText = new TextView(this);
            Switch alarmSwitch = new Switch(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(convertPixelsToDp(50,this),0,convertPixelsToDp(50,this),0);
            alarmText.setLayoutParams(params);
            alarmSwitch.setLayoutParams(params);
            alarmSwitch.setChecked(true);
            alarmText.setFocusable(false);
            alarmText.setText(alarms.get(i).toString());
            int finalI = i;
            alarmText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int alarmID = alarms.get(finalI).getId();
                    Intent i = new Intent(NewScheduleActivity.this, NewAlarmActivity.class);
                    i.putExtra("AlarmID", alarmID );
                    startActivity(i);
                }
            });
            thisLayout.addView(alarmText);
        }
    }

    public static int convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));
        return dp;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}