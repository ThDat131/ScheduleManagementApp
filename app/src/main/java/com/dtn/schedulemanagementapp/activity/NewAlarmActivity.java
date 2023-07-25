package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.database.SoundController;
import com.dtn.schedulemanagementapp.models.Sound;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewAlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ArrayList<Sound> soundList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);

        SoundController sCtrl = new SoundController(this);
        soundList = sCtrl.getSounds();

        Button btnDone = findViewById(R.id.doneButton);
        EditText edtAlarmName = findViewById(R.id.editTextAlarmName);
        EditText edtAlarmTime = findViewById(R.id.editTextAlarmTime);
        Spinner spinSoundName = findViewById(R.id.editTextSoundName);
        spinSoundName.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter sounds = new ArrayAdapter(this, android.R.layout.simple_spinner_item, soundList);
        spinSoundName.setAdapter(sounds);

        edtAlarmTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(NewAlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                            Time time = Time.valueOf(hour+":"+min);
                            edtAlarmTime.setText("HH:mm");
                    }
            }, hour, min, true);
                timePickerDialog.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}