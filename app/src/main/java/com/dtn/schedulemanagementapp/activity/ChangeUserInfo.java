package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.database.UserController;
import com.dtn.schedulemanagementapp.fragment.ProfileFragment;
import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

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

        UserController userController = new UserController(ChangeUserInfo.this);
        DBHelper dbHelper = DBHelper.getInstance(ChangeUserInfo.this);

        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        String username = prefs.getString("key_username", "");
        User user = userController.getUserByUsername(username);

        edtNewUsername.setText(user.getUsername());
        edtNewFullName.setText(user.getFullName());
//        edtNewBirthDay.setText(user.getBirthDate());
        edtNewEmail.setText(user.getEmail());

        String userName = edtNewFullName.getText().toString();
        String passWord = edtNewPassword.getText().toString();
        String confirm = edtConfirmPassword.getText().toString();
//        String birthDay = CalendarUtils.DateToString(edtNewBirthDay.getText(), "yyyy-MM-dd");

        String fullName = edtNewFullName.getText().toString();
        String email = edtNewEmail.getText().toString();

        btnUpdateInfo.setOnClickListener(view -> {
            if(!passWord.equals("")){
                if(passWord.equals(confirm)){
                    user.setUsername(userName);
                    user.setPassword(passWord);
                    user.setFullName(fullName);
                    user.setEmail(email);
                    userController.updateUser(user);
                    Toast.makeText(this, "Changed info success!!", Toast.LENGTH_SHORT).show();
                    Intent intentToProfile = new Intent(this, ProfileFragment.class);
                    startActivity(intentToProfile);
                } else
                    Toast.makeText(this, "Password doesn't match!!", Toast.LENGTH_SHORT).show();
            }
            user.setUsername(userName);
            user.setFullName(fullName);
            user.setEmail(email);
            userController.updateUser(user);
            Toast.makeText(this, "Changed info success!!", Toast.LENGTH_SHORT).show();
            Intent intentToProfile = new Intent(this, MainActivity.class);
            startActivity(intentToProfile);
        });
    }
}