package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import com.dtn.schedulemanagementapp.R;
//import com.dtn.schedulemanagementapp.databinding.ActivityMainBinding;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.fragment.CalendarFragment;
import com.dtn.schedulemanagementapp.fragment.CategoryFragment;
import com.dtn.schedulemanagementapp.fragment.ProfileFragment;
import com.dtn.schedulemanagementapp.fragment.ScheduleFragment;
import com.dtn.schedulemanagementapp.fragment.StatisticFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView botNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        createNotificationChannel();

        botNav = findViewById(R.id.botNav);

        // Set màn hình xuất hiện đầu tiên
        botNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.time) {
                replaceFragment(new ScheduleFragment());
            }
            else if (item.getItemId() == R.id.category) {
                replaceFragment(new CategoryFragment());
            }
            else if (item.getItemId() == R.id.calendar){
                replaceFragment(new CalendarFragment());
            }
            else if (item.getItemId() == R.id.stats) {
                replaceFragment(new StatisticFragment());
            }
            else if (item.getItemId() == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });






    }

    @Override
    public void onResume(){
        super.onResume();

        Intent intent = getIntent();
        String fragment = intent.getStringExtra("fragment");
        if (fragment != null) {
            if (fragment.equals("schedule")) {
                botNav.setSelectedItemId(R.id.time);
                replaceFragment(new ScheduleFragment());
            }
        }
        else {
            botNav.setSelectedItemId(R.id.calendar);
            replaceFragment(new CalendarFragment());
        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Calendar Name Placeholder");

//        if (intent != null) {
//            int frag = intent.getExtras().getInt("frag");
//            botNav.setSelectedItemId(frag);
//            if (frag == R.id.time) {
//                replaceFragment(new ScheduleFragment());
//            } else if (frag == R.id.category) {
//                replaceFragment(new Category6Fragment());
//            } else if (frag == R.id.calendar) {
//                replaceFragment(new CalendarFragment());
//            } else if (frag == R.id.stats) {
//                replaceFragment(new StatisticFragment());
//            } else if (frag == R.id.profile) {
//                replaceFragment(new ProfileFragment());
//            }
//        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutMain, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_id);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}