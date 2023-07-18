package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewUserActivity extends AppCompatActivity {

    TextView txtUsername;
    TextView txtPassword;
    TextView txtFullName;
    TextView txtBirthdate;
    TextView txtEmail;
    Spinner spnRole;
    Button btnAdd;

    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add New User");

        Mapping();
        initRoleOption();
        txtBirthdate.setFocusable(false);
        txtBirthdate.setText(getDateToday());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (addUser()) {
                    Toast.makeText(NewUserActivity.this, "New User Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewUserActivity.this, AdminUserActivity.class);
                    startActivity(intent);
                }
                else Toast.makeText(NewUserActivity.this, "Something has wrong", Toast.LENGTH_SHORT).show();


            }
        });

        txtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtBirthdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                } , year, month, day);

                datePickerDialog.show();

            }
        });

    }

    private void Mapping() {
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtFullName = findViewById(R.id.txtFullName);
        txtBirthdate = findViewById(R.id.txtBirthdate);
        txtEmail = findViewById(R.id.txtEmail);
        spnRole = findViewById(R.id.spnRole);
        btnAdd = findViewById(R.id.btnAdd);
    }

    private boolean addUser()  {

        User user = new User();
        String username;
        String password;
        String fullName;
        Date birthdate;
        String email;
        int role;

        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();
        fullName = txtFullName.getText().toString();
        birthdate = CalendarUtils.StringToDate(txtBirthdate.getText().toString());
        email = txtEmail.getText().toString();
        if (spnRole.getSelectedItem().toString().equals("Admin"))
            role = 1;
        else if (spnRole.getSelectedItem().toString().equals("User"))
            role = 0;
        else role = 0;

        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setBirthDate(birthdate);
        user.setEmail(email);
        user.setRole(role);

        DBHelper dbHelper = DBHelper.getInstance(NewUserActivity.this);
        if (dbHelper.addUser(user) != 0) {
            return true;

        }
        return false;

    }

    private void initRoleOption() {
        String[] roles = {"Admin", "User"};
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(NewUserActivity.this,
                android.R.layout.simple_spinner_dropdown_item, roles );

        spnRole.setAdapter(roleAdapter);

        spnRole.setSelection(1);
    }

    private String getDateToday() {
        LocalDate today = LocalDate.now();

        String dateFormatted = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return dateFormatted;
    }


}
