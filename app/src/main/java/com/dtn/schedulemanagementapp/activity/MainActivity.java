package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.fragment.CalendarFragment;
import com.dtn.schedulemanagementapp.fragment.CategoryFragment;
import com.dtn.schedulemanagementapp.fragment.ProfileFragment;
import com.dtn.schedulemanagementapp.fragment.ScheduleFragment;
import com.dtn.schedulemanagementapp.fragment.StatisticFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView botNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = DBHelper.getInstance(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        botNav = findViewById(R.id.botNav);

        // Set màn hình xuất hiện đầu tiên
        botNav.setSelectedItemId(R.id.calendar);
        replaceFragment(new CalendarFragment());

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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutMain, fragment);
        fragmentTransaction.commit();
    }
}