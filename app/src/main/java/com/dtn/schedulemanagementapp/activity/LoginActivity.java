package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;

public class LoginActivity extends AppCompatActivity {

    Button mbtnLogin;
    EditText medtUserName, medtPassWord;
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mbtnLogin = (Button) findViewById(R.id.btnLogin);
        medtUserName = (EditText) findViewById(R.id.edtUserName);
        medtPassWord = (EditText) findViewById(R.id.edtPassWord);
        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(medtUserName))
                {
                    Toast.makeText(getApplicationContext(),"You did not enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isEmpty(medtPassWord))
                {
                    Toast.makeText(LoginActivity.this,"You did not enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
}