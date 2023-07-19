package com.dtn.schedulemanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public static final String TABLE_CATEGORY = "Category";
    public static final String TABLE_REMINDER = "Reminder";
    public static final String TABLE_SCHEDULE = "Schedule";
    public static final String TABLE_SOUND = "Sound";
    public static final String TABLE_USER = "User";

    // Category

    public static final String CATEGORY_COL_ID = "id";
    public static final String CATEGORY_COL_NAME = "name";
    public static final String CATEGORY_COL_COLOR = "color";
    public static final String CATEGORY_COL_USERNAME = "username";

    // Reminder

    public static final String REMINDER_COL_ID = "id";
    public static final String REMINDER_COL_NAME = "name";
    public static final String REMINDER_COL_TIME_REMIND= "timeRemind";
    public static final String REMINDER_COL_SCHEDULE_ID = "scheduleId";
    public static final String REMINDER_COL_SOUND_ID = "soundId";

    // Schedule

    public static final String SCHEDULE_COL_ID = "id";
    public static final String SCHEDULE_COL_NAME = "name";
    public static final String SCHEDULE_COL_NOTE = "note";
    public static final String SCHEDULE_COL_START_DATE= "startDate";
    public static final String SCHEDULE_COL_END_DATE = "endDate";
    public static final String SCHEDULE_COL_CATE_ID = "cateId";
    public static final String SCHEDULE_COL_USERNAME = "username";

    // SOUND

    public static final String SOUND_COL_ID = "id";
    public static final String SOUND_COL_NAME = "name";
    public static final String SOUND_COL_URL_SOUND = "urlSound";


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

    public ArrayList<Schedule> getScheduleByDate(Date dateSchedule) throws ParseException {
        SimpleDateFormat dateSimple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateSchedule);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dateNext = calendar.getTime();

        String dateStringStart = dateSimple.format(dateSchedule);
        String dateStringEnd = dateSimple.format(dateNext);
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
        String query = "SELECT * "
                + "FROM " + TABLE_SCHEDULE
                + " WHERE " + SCHEDULE_COL_START_DATE + " >= ? AND " + SCHEDULE_COL_END_DATE + " < ?";

        String[] args = {dateStringStart, dateStringEnd};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String note = cursor.getString(2);
            Date startDate = dateSimple.parse(cursor.getString(3));
            Date endDate = dateSimple.parse(cursor.getString(4));
            int cateId = cursor.getInt(5);
            String username = cursor.getString(6);

            schedules.add(new Schedule(id, name, note, startDate, endDate, username, cateId));

        }
        return schedules;
    }


}
