package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
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
import com.dtn.schedulemanagementapp.database.CategoryController;
import com.dtn.schedulemanagementapp.database.ReminderController;
import com.dtn.schedulemanagementapp.database.ScheduleController;
import com.dtn.schedulemanagementapp.models.Category;
import com.dtn.schedulemanagementapp.models.Reminder;
import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.utils.AlarmReceiver;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewScheduleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<Category> cates = new ArrayList<>();
    Schedule schedule;
    ArrayList<Reminder> alarms = new ArrayList<>();
    ArrayList<LinearLayout> rows = new ArrayList<>();
    LinearLayout thisLayout;
    ReminderController rCtrl;
    ScheduleController sCtrl;

    Button btnAddAlarm;
    EditText editTextEvName;
    EditText editTextStartDate;
    EditText editTextStartTime;
    EditText editTextEndDate;
    EditText editTextEndTime;
    EditText editTextDescription;
    ImageButton buttonHelp;
    ImageButton buttonHelp2;
    Spinner spinCates;
    int check = 0;
    String username;
    ArrayAdapter ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);

        Intent i = getIntent();
        check = i.getIntExtra("ScheduleID",0);

        Toolbar topToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        ActionBar actionBar = getSupportActionBar();;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Schedule");
        thisLayout = findViewById(R.id.listAlarm);

        sCtrl = new ScheduleController(this);
        rCtrl = new ReminderController(this);
        CategoryController cCtrl = new CategoryController(this);

        spinCates = findViewById(R.id.SpinnerCate);
        spinCates.setOnItemSelectedListener(this);

        SharedPreferences prefs = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        username = prefs.getString("key_username", "admin");

        cates = cCtrl.getCategoriesByUser(username);
        ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cates);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCates.setAdapter(ad);

        btnAddAlarm = findViewById(R.id.buttonAddAlarm);
        editTextEvName = findViewById(R.id.editTextEvName);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextStartTime = findViewById(R.id.editTextStartTime);
        editTextEndDate = findViewById(R.id.editTextEndDate);
        editTextEndTime = findViewById(R.id.editTextEndTime);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonHelp = findViewById(R.id.helpButton);
        buttonHelp2 = findViewById(R.id.helpButton2);

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

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextEvName.getText().toString().isEmpty() ||
                    editTextStartDate.getText().toString().isEmpty() ||
                    editTextEndDate.getText().toString().isEmpty() ||
                    editTextStartTime.getText().toString().isEmpty() ||
                    editTextEndTime.getText().toString().isEmpty() ) {
                    Toast.makeText(NewScheduleActivity.this, getString(R.string.input_required), Toast.LENGTH_SHORT).show();
                    return;
                }
                String start = CalendarUtils.StringToString(editTextStartDate.getText().toString() + " " + editTextStartTime.getText().toString() + ":00", "dd/MM/yyyy HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                String end = CalendarUtils.StringToString(editTextEndDate.getText().toString() + " " + editTextEndTime.getText().toString() + ":00", "dd/MM/yyyy HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                schedule = new Schedule(editTextEvName.getText().toString(),
                        editTextDescription.getText().toString(),
                        start,
                        end,
                        username,
                        ((Category) spinCates.getSelectedItem()).getId());
                if (!sCtrl.isCoincideDate(-1, start, end)) {
                    long id = sCtrl.addNewSchedule(schedule);
                    if (id != -1){
                        Toast.makeText(NewScheduleActivity.this, "Add schedule successfully", Toast.LENGTH_SHORT).show();
                        check = (int) id;
                        editTextEvName.setEnabled(false);
                        spinCates.setEnabled(false);
                        editTextStartDate.setEnabled(false);
                        editTextStartTime.setEnabled(false);
                        editTextEndDate.setEnabled(false);
                        editTextEndTime.setEnabled(false);
                        btnAddAlarm.setEnabled(false);
                        editTextDescription.setEnabled(false);
                        buttonHelp.setVisibility(View.GONE);
                        buttonHelp2.setVisibility(View.VISIBLE);
                    }
                    else Toast.makeText(NewScheduleActivity.this, "Something has wrong", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(NewScheduleActivity.this, "Dates are conflict please try again", Toast.LENGTH_SHORT).show();
            }
        });


        buttonHelp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEvName.setEnabled(true);
                spinCates.setEnabled(true);
                editTextStartDate.setEnabled(true);
                editTextStartTime.setEnabled(true);
                editTextEndDate.setEnabled(true);
                editTextEndTime.setEnabled(true);
                btnAddAlarm.setEnabled(true);
                editTextDescription.setEnabled(true);
                buttonHelp.setVisibility(View.VISIBLE);
                buttonHelp2.setVisibility(View.GONE);
            }
        });

        topToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewScheduleActivity.this, NewAlarmActivity.class);
                schedule = sCtrl.getScheduleByScheduleID(check);
                i.putExtra("schedule", schedule);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(check!=0) {
            Schedule scheduleEdit = sCtrl.getScheduleByScheduleID(check);
            spinCates.setEnabled(true);
            String start = CalendarUtils.StringToString(scheduleEdit.getStartDate(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss");
            String end = CalendarUtils.StringToString(scheduleEdit.getEndDate(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss");
            editTextEvName.setEnabled(true);
            editTextEvName.setText(scheduleEdit.getName());
            spinCates.setEnabled(true);
            for (Category cate: cates) {
                if (cate.getId() == scheduleEdit.getCateId()) {
                    spinCates.setSelection(cates.indexOf(cate));
                }
            }
            editTextStartDate.setEnabled(true);
            editTextStartDate.setText(start.substring(0, 10));
            editTextStartTime.setEnabled(true);
            editTextStartTime.setText(start.substring(11, 16));
            editTextEndDate.setEnabled(true);
            editTextEndDate.setText(end.substring(0, 10));
            editTextEndTime.setEnabled(true);
            editTextEndTime.setText(scheduleEdit.getEndDate().substring(11, 16));
            btnAddAlarm.setEnabled(true);
//                fetchAlarms();
            editTextDescription.setEnabled(true);
            editTextDescription.setText(scheduleEdit.getNote());
            buttonHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editTextEvName.getText().toString().isEmpty() ||
                            editTextStartDate.getText().toString().isEmpty() ||
                            editTextEndDate.getText().toString().isEmpty() ||
                            editTextStartTime.getText().toString().isEmpty() ||
                            editTextEndTime.getText().toString().isEmpty() ) {
                        Toast.makeText(NewScheduleActivity.this, getString(R.string.input_required), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String start = CalendarUtils.StringToString(editTextStartDate.getText().toString(), "dd/MM/yyyy", "yyyy-MM-dd") + " " + editTextStartTime.getText().toString() + ":00";
                    String end = CalendarUtils.StringToString(editTextEndDate.getText().toString(), "dd/MM/yyyy", "yyyy-MM-dd") + " " + editTextEndTime.getText().toString() + ":00";
                    Schedule scheduleEdited = new Schedule(
                            check,
                            editTextEvName.getText().toString(),
                            editTextDescription.getText().toString(),
                            start,
                            end,
                            username,
                            ((Category) spinCates.getSelectedItem()).getId());
                    if (!sCtrl.isCoincideDate(check, start, end)) {
                        if(sCtrl.updateSchedule(scheduleEdited) > 0) {
                            Toast.makeText(NewScheduleActivity.this, "Update schedule successfully", Toast.LENGTH_SHORT).show();

                            editTextEvName.setEnabled(false);
                            spinCates.setEnabled(false);
                            editTextStartDate.setEnabled(false);
                            editTextStartTime.setEnabled(false);
                            editTextEndDate.setEnabled(false);
                            editTextEndTime.setEnabled(false);
                            btnAddAlarm.setEnabled(false);
                            editTextDescription.setEnabled(false);
                            buttonHelp.setVisibility(View.GONE);
                            buttonHelp2.setVisibility(View.VISIBLE);
                        }
                        else Toast.makeText(NewScheduleActivity.this, "Something has wrong", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(NewScheduleActivity.this, "Dates are conflict please try again", Toast.LENGTH_SHORT).show();
                }
            });


            buttonHelp.setVisibility(View.GONE);
            buttonHelp2.setVisibility(View.VISIBLE);
            editTextEvName.setEnabled(false);
            spinCates.setEnabled(false);
            editTextStartDate.setEnabled(false);
            editTextStartTime.setEnabled(false);
            editTextEndDate.setEnabled(false);
            editTextEndTime.setEnabled(false);
            btnAddAlarm.setEnabled(false);
            editTextDescription.setEnabled(false);
        }
        else {
            buttonHelp.setVisibility(View.VISIBLE);
            buttonHelp2.setVisibility(View.GONE);
        }

        fetchAlarms();
    }

    private void fetchAlarms(){
        thisLayout.removeAllViews();
        alarms.clear();
        rows.clear();
        alarms = rCtrl.getReminderByScheduleID(check);

        for (int i = 0; i < alarms.size(); i++) {
            TextView alarmText = new TextView(this);
            Button btnDelete = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(convertPixelsToDp(50,this),0,convertPixelsToDp(50,this),0);
            params.weight = 2;
            params.gravity = Gravity.START;

            LinearLayout row = new LinearLayout(NewScheduleActivity.this);
            row.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            ));

            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            rowParams.setMargins(0, 0 ,20, 0);

            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            btnParams.gravity = Gravity.END;
            btnParams.weight = 1;

            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(rowParams);
            row.setTag(i);

            btnDelete.setLayoutParams(btnParams);

            btnDelete.setText("Delete");
            btnDelete.setTextSize(18);
            btnDelete.setWidth(0);

            alarmText.setWidth(0);
            alarmText.setLayoutParams(params);
            alarmText.setTextSize(18);
            alarmText.setPadding(10, 10, 10, 10);
            alarmText.setFocusable(false);
            alarmText.setText(alarms.get(i).toString());

            row.addView(alarmText);
            row.addView(btnDelete);
            rows.add(row);

            int finalI = i;
            alarmText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int alarmID = alarms.get(finalI).getId();
                    schedule = sCtrl.getScheduleByScheduleID(check);
                    Intent i = new Intent(NewScheduleActivity.this, NewAlarmActivity.class);
                    i.putExtra("AlarmID", alarmID );
                    i.putExtra("schedule", schedule);
                    startActivity(i);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickIndex = rows.indexOf(row);
                    if(clickIndex >= 0 && clickIndex < alarms.size()) {
                        int alarmID = alarms.get(clickIndex).getId();
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setMessage("Do you want to delete this alarm ?");
                        builder.setTitle("Alert !");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                            if(rCtrl.deleteReminder(alarmID) > 0) {
                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                Intent intent = new Intent(NewScheduleActivity.this, AlarmReceiver.class);
                                intent.putExtra("reminder", alarms.get(clickIndex));
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(NewScheduleActivity.this, alarms.get(clickIndex).getId(), intent, PendingIntent.FLAG_IMMUTABLE);
                                alarmManager.cancel(pendingIntent);

                                alarms.remove(clickIndex);
                                thisLayout.removeViewAt(clickIndex);
                                rows.remove(row);

                                Toast.makeText(NewScheduleActivity.this, "Delete alarm successfully", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(NewScheduleActivity.this, "Something has wrong", Toast.LENGTH_SHORT).show();

                        });
                        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                            dialog.cancel();
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else Toast.makeText(NewScheduleActivity.this, "Sai sai ở đâu vậy ta", Toast.LENGTH_SHORT).show();

                }
            });
            thisLayout.addView(row);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewScheduleActivity.this, MainActivity.class);
        intent.putExtra("fragment", "schedule");
        startActivity(intent);
    }
}