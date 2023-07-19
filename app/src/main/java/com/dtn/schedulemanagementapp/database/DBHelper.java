package com.dtn.schedulemanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "schedule.db";
    private static final int DATABASE_VERSION = 1;

     // Tables name

    private static final String TABLE_CATEGORY = "Category";
    private static final String TABLE_REMINDER = "Reminder";
    public static final String TABLE_SCHEDULE = "Schedule";
    private static final String TABLE_SOUND = "Sound";
    public static final String TABLE_USER = "User";

    // Category

    private static final String CATEGORY_COL_ID = "id";
    private static final String CATEGORY_COL_NAME = "name";
    private static final String CATEGORY_COL_COLOR = "color";
    private static final String CATEGORY_COL_USERNAME = "username";

    // Reminder

    private static final String REMINDER_COL_ID = "id";
    private static final String REMINDER_COL_NAME = "name";
    private static final String REMINDER_COL_TIME_REMIND= "timeRemind";
    private static final String REMINDER_COL_SCHEDULE_ID = "scheduleId";
    private static final String REMINDER_COL_SOUND_ID = "soundId";

    // Schedule

    public static final String SCHEDULE_COL_ID = "id";
    public static final String SCHEDULE_COL_NAME = "name";
    public static final String SCHEDULE_COL_NOTE = "note";
    public static final String SCHEDULE_COL_START_DATE= "startDate";
    public static final String SCHEDULE_COL_END_DATE = "endDate";
    public static final String SCHEDULE_COL_CATE_ID = "cateId";
    public static final String SCHEDULE_COL_USERNAME = "userId";

    // SOUND

    private static final String SOUND_COL_ID = "id";
    private static final String SOUND_COL_NAME = "name";
    private static final String SOUND_COL_URL_SOUND = "urlSound";


    // USER

    public static final String USER_COL_USERNAME = "username";
    public static final String USER_COL_PASSWORD = "password";
    public static final String USER_COL_FULL_NAME = "fullName";
    public static final String USER_COL_BIRTHDATE = "birthDate";
    public static final String USER_COL_EMAIL = "email";
    public static final String USER_COL_ROLE = "role";

    private static DBHelper sInstance;

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null)
            sInstance = new DBHelper(context.getApplicationContext());
        return sInstance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + USER_COL_USERNAME + " VARCHAR(50) PRIMARY KEY,"
                + USER_COL_PASSWORD + " VARCHAR(50) NOT NULL,"
                + USER_COL_FULL_NAME + " VARCHAR(50) NOT NULL,"
                + USER_COL_BIRTHDATE + " DATE NOT NULL,"
                + USER_COL_EMAIL + " VARCHAR(50),"
                + USER_COL_ROLE + " INTEGER"
                + ");";

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + CATEGORY_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CATEGORY_COL_NAME + " VARCHAR(50) NOT NULL,"
                + CATEGORY_COL_COLOR + " VARCHAR(7),"
                + CATEGORY_COL_USERNAME + " VARCHAR(50) NOT NULL,"
                + "FOREIGN KEY (" + CATEGORY_COL_USERNAME + ") REFERENCES " + TABLE_USER + "(" + USER_COL_USERNAME + ")" + " ON DELETE CASCADE"
                + ");";

        String CREATE_SCHEDULE_TABLE = "CREATE TABLE " + TABLE_SCHEDULE + "("
                + SCHEDULE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SCHEDULE_COL_NAME + " VARCHAR(50) NOT NULL,"
                + SCHEDULE_COL_NOTE + " VARCHAR(200),"
                + SCHEDULE_COL_START_DATE + " DATETIME NOT NULL,"
                + SCHEDULE_COL_END_DATE + " DATETIME NOT NULL,"
                + SCHEDULE_COL_CATE_ID + " INTEGER NOT NULL,"
                + SCHEDULE_COL_USERNAME + " VARCHAR(50) NOT NULL,"
                + "FOREIGN KEY (" + SCHEDULE_COL_USERNAME + ") REFERENCES " + TABLE_USER + "(" + USER_COL_USERNAME + ")" + " ON DELETE CASCADE" + ","
                + "FOREIGN KEY (" + SCHEDULE_COL_CATE_ID + ") REFERENCES " + TABLE_CATEGORY + "(" + CATEGORY_COL_ID + ")"
                + ");";

        String CREATE_SOUND_TABLE = "CREATE TABLE " + TABLE_SOUND + "("
                + SOUND_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SOUND_COL_NAME + " VARCHAR(50) NOT NULL,"
                + SOUND_COL_URL_SOUND + " VARCHAR(200) NOT NULL"
                + ");";

        String CREATE_REMINDER_TABLE = "CREATE TABLE " + TABLE_REMINDER + "("
                + REMINDER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + REMINDER_COL_NAME + " VARCHAR(50),"
                + REMINDER_COL_TIME_REMIND + " DATETIME NOT NULL,"
                + REMINDER_COL_SCHEDULE_ID + " INTEGER NOT NULL,"
                + REMINDER_COL_SOUND_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + REMINDER_COL_SCHEDULE_ID + ") REFERENCES " + TABLE_SCHEDULE + "(" + SCHEDULE_COL_ID + "),"
                + "FOREIGN KEY (" + REMINDER_COL_SOUND_ID + ") REFERENCES " + TABLE_SOUND + "(" + SOUND_COL_ID + ")"
                + ");";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_SCHEDULE_TABLE);
        db.execSQL(CREATE_SOUND_TABLE);
        db.execSQL(CREATE_REMINDER_TABLE);

        String INSERT_USER = "INSERT INTO " + TABLE_USER + " ("
                + USER_COL_USERNAME + ","
                + USER_COL_PASSWORD + ","
                + USER_COL_FULL_NAME + ","
                + USER_COL_BIRTHDATE + ","
                + USER_COL_ROLE + ")"
                + "VALUES ('admin', 'Admin@123', 'Admin', '2002-01-13', 1);";

        String INSERT_CATEGORY = "INSERT INTO " + TABLE_CATEGORY + " ("
                + CATEGORY_COL_NAME + ","
                + CATEGORY_COL_USERNAME + ")"
                + "VALUES ('Study', 'admin'), ('Work', 'admin'), ('Family', 'admin');";

        String INSERT_SCHEDULE = "INSERT INTO " + TABLE_SCHEDULE + " ("
                + SCHEDULE_COL_NAME + ","
                + SCHEDULE_COL_START_DATE + ","
                + SCHEDULE_COL_END_DATE + ","
                + SCHEDULE_COL_CATE_ID + ","
                + SCHEDULE_COL_USERNAME + ")"
                + "VALUES ('Data structures and algorithm', '2023-07-30 07:00:00', '2023-07-30 11:00:00', 1, 'admin')," +
                    "('Mobile Programming', '2023-07-25 07:00:00', '2023-07-25 11:00:00', 1, 'admin')," +
                    "('Web Design', '2023-07-26 07:00:00', '2023-07-26 11:00:00', 1, 'admin')," +
                    "('Go to shopping', '2023-07-23 18:00:00', '2023-07-23 19:00:00', 3, 'admin');";
        db.execSQL(INSERT_USER);
        db.execSQL(INSERT_CATEGORY);
        db.execSQL(INSERT_SCHEDULE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public ArrayList<User> getUsers() {
        SimpleDateFormat dateSimple = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
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

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_COL_USERNAME, user.getUsername());
        values.put(USER_COL_PASSWORD, user.getPassword());
        values.put(USER_COL_FULL_NAME, user.getFullName());
        values.put(USER_COL_BIRTHDATE, userBirthdate);
        values.put(USER_COL_EMAIL, user.getEmail());
        values.put(USER_COL_ROLE, user.getRole());

        return db.insert(TABLE_USER, null, values);

    }

    public long updateUser(User user) {
        String userBirthdate = CalendarUtils.DateToString(user.getBirthDate(), "yyyy-MM-dd");

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_COL_USERNAME, user.getUsername());
        values.put(USER_COL_PASSWORD, user.getPassword());
        values.put(USER_COL_FULL_NAME, user.getFullName());
        values.put(USER_COL_BIRTHDATE, userBirthdate);
        values.put(USER_COL_EMAIL, user.getEmail());
        values.put(USER_COL_ROLE, user.getRole());

        return db.update(TABLE_USER, values, USER_COL_USERNAME + " = " + "'" + user.getUsername() + "'", null);
    }

    public long deleteUser(String username) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(TABLE_USER, USER_COL_USERNAME + " = " + "'" + username + "'", null);
    }
    public int userPresent (String un, String pw){
        String sql = "SELECT *"
                + " FROM " + TABLE_USER
                + " WHERE "+  USER_COL_USERNAME + " = ?"
                + " AND " + USER_COL_PASSWORD + " = ?";
        String[] args = {un, pw};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        return cursor.getCount();
    }

    public User getUserByUsername(String userName) {
        String username = "", password = "", fullName = "", birthDate ="", email ="", role = "";
        Date date = new Date();
        int roleNum = 0;
        String query = "SELECT * FROM " + TABLE_USER +
                " WHERE " + USER_COL_USERNAME  + " = ?";
        SQLiteDatabase db = getReadableDatabase();
        String[] args = {userName};
        Cursor cursor = db.rawQuery(query, args);

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
