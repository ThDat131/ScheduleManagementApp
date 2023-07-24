package com.dtn.schedulemanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dtn.schedulemanagementapp.models.Reminder;
import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReminderController {
    SQLiteDatabase database;
    DBHelper helper;


    public ReminderController(Context context){
        helper = DBHelper.getInstance(context);
        database = helper.getWritableDatabase();
    }

    public long addNewReminder(Reminder rmd) {
        String remindDateTime = CalendarUtils.DateToString(rmd.getTimeRemind(), "yyyy-MM-dd hh:mm:ss");
        ContentValues values = new ContentValues();
        values.put(DBHelper.REMINDER_COL_NAME,rmd.getNameRemind());
        values.put(DBHelper.REMINDER_COL_TIME_REMIND,remindDateTime);
        values.put(DBHelper.REMINDER_COL_SCHEDULE_ID,rmd.getScheduleId());
        values.put(DBHelper.REMINDER_COL_SOUND_ID,rmd.getSoundId());
        return database.insert(DBHelper.TABLE_USER, null, values);
    }

    public ArrayList<Reminder> getReminderByScheduleID(int scheduleID) throws ParseException {
        SimpleDateFormat dateSimple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        ArrayList<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * "
                + "FROM " + DBHelper.TABLE_REMINDER
                + " WHERE " + DBHelper.REMINDER_COL_SCHEDULE_ID + " = ?";

        String[] args = {String.valueOf(scheduleID)};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Date scheDate = dateSimple.parse(cursor.getString(2));
            int scheID = cursor.getInt(3);
            int soundID = cursor.getInt(4);

            reminders.add(new Reminder(id, name, scheDate, scheID, soundID));
        }
        return reminders;
    }
}
