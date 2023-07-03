package com.dtn.schedulemanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.dtn.schedulemanagementapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView botNav;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new CalendarFragment());

        binding.botNav.setOnItemSelectedListener(item -> {
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