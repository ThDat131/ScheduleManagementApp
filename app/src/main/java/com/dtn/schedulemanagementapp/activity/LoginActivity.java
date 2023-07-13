package com.dtn.schedulemanagementapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        String un = medtUserName.getText().toString();
        String pw = medtPassWord.getText().toString();
//        TextView.OnEditorActionListener listener = new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (R.id.edtUserName == EditorInfo.IME_NULL
//                        && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    example_confirm();//match this behavior to your 'Send' (or Confirm) button
//                }
//                return true;
//            }
//        };
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
                if(userPresent(un, pw) != 0)
                {
                    SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("key_username", un);
                    editor.putBoolean("logged in", true);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Welcome back " + un, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("key", medtUserName.getText().toString());
//                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
    public int userPresent (String un, String pw){
        SQLiteDatabase db = this.openOrCreateDatabase("DBHelper", MODE_PRIVATE, null);
        String sql = "select * from TABLE_USER where username = un and password = pw";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.close();
        db.close();
        return cursor.getCount();
    }
}