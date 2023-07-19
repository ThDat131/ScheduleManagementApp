package com.dtn.schedulemanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

public class UserController {
    SQLiteDatabase database;
    DBHelper helper;

    public UserController(Context context){
        helper = DBHelper.getInstance(context);
        database = helper.getWritableDatabase();
    }

    public long addNewUser(User user){
        String userBirthdate = CalendarUtils.DateToString(user.getBirthDate(), "yyyy-MM-dd");
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_COL_USERNAME, user.getUsername());
        values.put(DBHelper.USER_COL_PASSWORD, user.getPassword());
        values.put(DBHelper.USER_COL_FULL_NAME, user.getFullName());
        values.put(DBHelper.USER_COL_BIRTHDATE, userBirthdate);
        values.put(DBHelper.USER_COL_EMAIL, user.getEmail());
        values.put(DBHelper.USER_COL_ROLE, user.getRole());
        return database.insert(DBHelper.TABLE_USER, null, values);
    }

}
