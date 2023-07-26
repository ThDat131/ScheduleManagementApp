package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.dtn.schedulemanagementapp.models.Sound;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewAlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Sound> soundList = new ArrayList<Sound>();
    Reminder reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);

        Intent i = getIntent();
        int scheID = i.getIntExtra("ScheduleID", 1);

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
            edtAlarmTime.setText(reminder.getTimeRemind());
            spinSoundName.setSelection(reminder.getSoundId());
//            reminder.setSoundId(spinSoundName.getSelectedItem().getId());
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminder = new Reminder(edtAlarmName.getText().toString(),
                        edtAlarmTime.getText().toString(),
                        scheID,
//                        Integer.parseInt(spinSoundName.getSelectedItem().toString().substring(0,1)));
                        1);
                if(check!=0) {
                    rCtrl.editReminder(reminder);
                }else {
                    rCtrl.addNewReminder(reminder);
                }
                Toast.makeText(NewAlarmActivity.this, "Add remind successfully!", Toast.LENGTH_SHORT).show();
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
}