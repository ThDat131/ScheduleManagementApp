package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;

public class NewScheduleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] cates = {"Home", "Work", "Holidays", "Tested"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);

        Toolbar topToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        ActionBar actionBar = getSupportActionBar();;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Spinner spin = findViewById(R.id.SpinnerCate);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cates);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);

        Button btnAlarm = findViewById(R.id.buttonAddAlarm);
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog fbDialogue = new Dialog(NewScheduleActivity.this, android.R.style.Theme_Black_NoTitleBar);
                fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(80, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.fragment_add_alarm);
                fbDialogue.setCancelable(true);
                fbDialogue.show();
            }
        });

        EditText editTextEvName = findViewById(R.id.editTextEvName);
        EditText editTextStartDate = findViewById(R.id.editTextStartDate);
        EditText editTextEndDate = findViewById(R.id.editTextEndDate);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        ImageButton buttonHelp = findViewById(R.id.helpButton);

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEvName.setInputType(0);
                spin.setEnabled(false);
                editTextStartDate.setFocusable(false);
                editTextEndDate.setFocusable(false);
                btnAlarm.setEnabled(false);
                editTextDescription.setFocusable(false);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), cates[i], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}