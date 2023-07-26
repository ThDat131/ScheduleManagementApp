package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.database.UserController;
import com.dtn.schedulemanagementapp.fragment.ProfileFragment;
import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ChangeUserInfo extends AppCompatActivity {

    EditText edtNewUsername, edtNewPassword, edtConfirmPassword, edtNewFullName, edtNewBirthDay, edtNewEmail;
    Button btnUpdateInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);

        edtNewUsername = findViewById(R.id.txtNewUsername);
        edtNewPassword = findViewById(R.id.txtNewPassword);
        edtConfirmPassword = findViewById(R.id.txtConfirmPassWord);
        edtNewFullName = findViewById(R.id.txtNewFullName);
        edtNewBirthDay = findViewById(R.id.txtNewBirthdate);
        edtNewEmail = findViewById(R.id.txtNewEmail);
        btnUpdateInfo = findViewById(R.id.btnUpdateInfo);

        edtNewBirthDay.setFocusable(false);
        UserController userController = new UserController(ChangeUserInfo.this);
        DBHelper dbHelper = DBHelper.getInstance(ChangeUserInfo.this);

        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        String username = prefs.getString("key_username", "");
        User user = userController.getUserByUsername(username);

        edtNewUsername.setText(user.getUsername());
        edtNewUsername.setFocusable(false);



        btnUpdateInfo.setOnClickListener(view -> {

            String userName = edtNewUsername.getText().toString();
            String passWord = edtNewPassword.getText().toString();
            String confirm = edtConfirmPassword.getText().toString();
            String birthDay = edtNewBirthDay.getText().toString();

            String fullName = edtNewFullName.getText().toString();
            String email = edtNewEmail.getText().toString();
            if(!passWord.equals("")){
                if(passWord.equals(confirm)){
                    user.setUsername(userName);
                    user.setPassword(passWord);
                    user.setFullName(fullName);
                    user.setEmail(email);
                    user.setBirthDate(CalendarUtils.StringToDate(birthDay, "dd/MM/yyyy"));
                    if (userController.updateUser(user) > 0){
                        Toast.makeText(this, "Changed info success!!", Toast.LENGTH_SHORT).show();
                        Intent intentToProfile = new Intent(ChangeUserInfo.this, MainActivity.class);
                        startActivity(intentToProfile);
                    }
                    else Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(this, "Password doesn't match!!", Toast.LENGTH_SHORT).show();
            }
            else {
                user.setUsername(userName);
                user.setFullName(fullName);
                user.setEmail(email);
                user.setBirthDate(CalendarUtils.StringToDate(birthDay, "dd/MM/yyyy"));
//            userController.updateUser(user);
//            Toast.makeText(this, "Changed info success!!", Toast.LENGTH_SHORT).show();
                if (userController.updateUser(user) > 0){
                    Toast.makeText(this, "Changed info success!!", Toast.LENGTH_SHORT).show();
                    Intent intentToProfile = new Intent(ChangeUserInfo.this, MainActivity.class);
                    startActivity(intentToProfile);
                }
                else Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();

            }

        });

        edtNewBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ChangeUserInfo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                        edtNewBirthDay.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                } , year, month, day);

                datePickerDialog.show();
            }
        });
    }
}