package com.dtn.schedulemanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dtn.schedulemanagementapp.models.User;

public class UserController {
    SQLiteDatabase database;
    DBHelper helper;

    public UserController(Context context){
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }

    public long add(User user){
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_COL_USERNAME, user.getUsername());
        values.put(DBHelper.USER_COL_PASSWORD, user.getPassword());
        values.put(DBHelper.USER_COL_FULL_NAME, user.getFullName());
        values.put(DBHelper.USER_COL_EMAIL, user.getEmail());
        values.put(DBHelper.USER_COL_ROLE, user.getRole());
        return database.insert(DBHelper.TABLE_USER, null, values);
    }
}
