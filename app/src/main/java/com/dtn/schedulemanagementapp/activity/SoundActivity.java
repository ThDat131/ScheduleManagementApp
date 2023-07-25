package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.fragment.ProfileFragment;

public class SoundActivity extends AppCompatActivity {

    ImageView imgBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        imgBackButton = (ImageView) findViewById(R.id.imgBackButton);

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToProfile = new Intent(SoundActivity.this, MainActivity.class);
                startActivity(intentToProfile);
            }
        });
    }
}