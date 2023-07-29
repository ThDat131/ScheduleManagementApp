package com.dtn.schedulemanagementapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.database.ReminderController;
import com.dtn.schedulemanagementapp.database.SoundController;
import com.dtn.schedulemanagementapp.models.Reminder;
import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.models.Sound;
import com.dtn.schedulemanagementapp.utils.AlarmReceiver;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class NewAlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Schedule schedule;
    ArrayList<Sound> soundList = new ArrayList<Sound>();
    Reminder reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);

        getSupportActionBar().setTitle("Alarm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        schedule = (Schedule) i.getSerializableExtra("schedule");

        ReminderController rCtrl = new ReminderController(this);
//        SoundController sCtrl = new SoundController(this);
//        soundList = sCtrl.getSounds();

        Button btnDone = findViewById(R.id.doneButton);
        EditText edtAlarmName = findViewById(R.id.editTextAlarmName);
        EditText edtAlarmTime = findViewById(R.id.editTextAlarmTime);
        Spinner spinSoundName = findViewById(R.id.editTextSoundName);
        spinSoundName.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter sounds = new ArrayAdapter(this, android.R.layout.simple_spinner_item, soundList);
        spinSoundName.setAdapter(sounds);

        edtAlarmTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewAlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        Time time = null;
                        try {
                            time = new Time(formatter.parse(hour + ":" + min).getTime());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        edtAlarmTime.setText(time.toString().substring(0, 5));
                    }
                }, hour, min, true);
                timePickerDialog.show();
            }
        });

        int check = getIntent().getIntExtra("AlarmID", 0);
        if (check!=0){
            reminder = rCtrl.getRemindByRemindID(check);
            edtAlarmName.setText(reminder.getNameRemind());
            edtAlarmTime.setText(reminder.getTimeRemind().substring(11, 16));
            spinSoundName.setSelection(reminder.getSoundId());
//            reminder.setSoundId(spinSoundName.getSelectedItem().getId());
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtAlarmName.getText().toString().isEmpty() ||
                    edtAlarmTime.getText().toString().isEmpty()) {
                    Toast.makeText(NewAlarmActivity.this, getString(R.string.input_required), Toast.LENGTH_SHORT).show();
                    return;
                }

                String timeAlarm = schedule.getStartDate().substring(0, 10) + " " + edtAlarmTime.getText().toString() + ":00";
                reminder = new Reminder(
                        edtAlarmName.getText().toString(),
                        timeAlarm,
                        schedule.getId());
                if(check!=0) {
                    reminder.setId(check);
                    if (rCtrl.editReminder(reminder) > 0) {
                        Toast.makeText(NewAlarmActivity.this, "Change remind successfully!", Toast.LENGTH_SHORT).show();
                        Intent intentBack = new Intent(NewAlarmActivity.this, NewScheduleActivity.class);
                        i.putExtra("ScheduleID", schedule.getId());
                        startActivity(intentBack);
                    }
                    else Toast.makeText(NewAlarmActivity.this, "Something has wrong", Toast.LENGTH_SHORT).show();

                }else {
                    long id = rCtrl.addNewReminder(reminder);
                    if (id != -1) {
                        String time = reminder.getTimeRemind();
                        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.systemDefault());
                        Calendar timeCalendar = Calendar.getInstance(timeZone);
                        Date date = CalendarUtils.StringToDateTime(time, "yyyy-MM-dd HH:mm:ss");
                        timeCalendar.setTime(date);

                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(NewAlarmActivity.this, AlarmReceiver.class);
                        intent.putExtra("reminder", reminder);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(NewAlarmActivity.this, reminder.getId(), intent, PendingIntent.FLAG_IMMUTABLE);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeCalendar.getTimeInMillis(), pendingIntent);

                        Toast.makeText(NewAlarmActivity.this, "Add remind successfully!", Toast.LENGTH_SHORT).show();

                        Intent intentBack = new Intent(NewAlarmActivity.this, NewScheduleActivity.class);
                        intentBack.putExtra("ScheduleID", schedule.getId());
                        startActivity(intentBack);
                    }
                    else Toast.makeText(NewAlarmActivity.this, "Something has wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "Yikes!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(this, NewScheduleActivity.class);
            i.putExtra("ScheduleID", schedule.getId());
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}