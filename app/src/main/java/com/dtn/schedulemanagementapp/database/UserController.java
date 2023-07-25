package com.dtn.schedulemanagementapp.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UserController {

    DBHelper dbHelper;
    SQLiteDatabase database;

    public UserController(Context context) {
        dbHelper = DBHelper.getInstance(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<User> getUsers() {
        SimpleDateFormat dateSimple = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM " + DBHelper.TABLE_USER;
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            try {
                String username = cursor.getString(0);
                String password = cursor.getString(1);
                String fullName = cursor.getString(2);
                String b = cursor.getString(3);
                Date birthDate = dateSimple.parse(b);
                String email = cursor.getString(4);
                int role = cursor.getInt(5);
                users.add(new User(username, password, fullName, birthDate, email, role));
            } catch (Exception ex) {
                Log.d("errorLoadUsers", ex.toString());
            }
        }
        return users;
    }

    public long addUser(User user) {
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

    public long updateUser(User user) {
        String userBirthdate = CalendarUtils.DateToString(user.getBirthDate(), "yyyy-MM-dd");

        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_COL_USERNAME, user.getUsername());
        values.put(DBHelper.USER_COL_PASSWORD, user.getPassword());
        values.put(DBHelper.USER_COL_FULL_NAME, user.getFullName());
        values.put(DBHelper.USER_COL_BIRTHDATE, userBirthdate);
        values.put(DBHelper.USER_COL_EMAIL, user.getEmail());
        values.put(DBHelper.USER_COL_ROLE, user.getRole());

        return database.update(DBHelper.TABLE_USER, values, DBHelper.USER_COL_USERNAME + " = " + "'" + user.getUsername() + "'", null);
    }

    public long deleteUser(String username) {

        return database.delete(DBHelper.TABLE_USER, DBHelper.USER_COL_USERNAME + " = " + "'" + username + "'", null);
    }
    public int userPresent (String un, String pw){
        String sql = "SELECT *"
                + " FROM " + DBHelper.TABLE_USER
                + " WHERE "+  DBHelper.USER_COL_USERNAME + " = ?"
                + " AND " + DBHelper.USER_COL_PASSWORD + " = ?";
        String[] args = {un, pw};
        Cursor cursor = database.rawQuery(sql, args);
        return cursor.getCount();
    }


    public User getUserByUsername(String userName) {
        String username = "", password = "", fullName = "", birthDate ="", email ="", role = "";
        Date date = new Date();
        int roleNum = 0;
        String query = "SELECT * FROM " + DBHelper.TABLE_USER +
                " WHERE " + DBHelper.USER_COL_USERNAME  + " = ?";
        String[] args = {userName};
        Cursor cursor = database.rawQuery(query, args);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                username = cursor.getString(0);
                password = cursor.getString(1);
                fullName = cursor.getString(2);
                birthDate = cursor.getString(3);
                email = cursor.getString(4);
                role = cursor.getString(5);

                date = CalendarUtils.StringToDate(birthDate, "yyyy-MM-dd");

                roleNum = Integer.parseInt(role);

            }
            return new User(username, password, fullName, date, email, roleNum);
        }
        return new User();
    }
}
