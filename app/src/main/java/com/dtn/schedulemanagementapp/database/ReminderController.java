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
        ContentValues values = new ContentValues();
        values.put(DBHelper.REMINDER_COL_NAME,rmd.getNameRemind());
        values.put(DBHelper.REMINDER_COL_TIME_REMIND,rmd.getTimeRemind());
        values.put(DBHelper.REMINDER_COL_SCHEDULE_ID,rmd.getScheduleId());
        values.put(DBHelper.REMINDER_COL_SOUND_ID,rmd.getSoundId());
        return database.insert(DBHelper.TABLE_REMINDER, null, values);
    }

    public long editReminder(Reminder rmd) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.REMINDER_COL_NAME,rmd.getNameRemind());
        values.put(DBHelper.REMINDER_COL_TIME_REMIND,rmd.getTimeRemind());
        values.put(DBHelper.REMINDER_COL_SCHEDULE_ID,rmd.getScheduleId());
        values.put(DBHelper.REMINDER_COL_SOUND_ID,rmd.getSoundId());
        String[] args = {rmd.getId() + ""};
        return database.update(DBHelper.TABLE_REMINDER, values, DBHelper.REMINDER_COL_ID + "= ?", args);
    }

    public long deleteReminder(int rmdId) {
        String[] args = {String.valueOf(rmdId)};
        return database.delete(DBHelper.TABLE_REMINDER, DBHelper.REMINDER_COL_ID + "= ?", args);
    }

    public ArrayList<Reminder> getReminderByScheduleID(int scheduleID) {
//        SimpleDateFormat dateSimple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

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
            String scheDate = cursor.getString(2);
            int scheID = cursor.getInt(3);
            int soundID = cursor.getInt(4);

            reminders.add(new Reminder(id, name, scheDate, scheID, soundID));
        }
        return reminders;
    }

    public Reminder getRemindByRemindID(int remindID) {
        String query = "SELECT * "
                + "FROM " + DBHelper.TABLE_REMINDER
                + " WHERE " + DBHelper.REMINDER_COL_ID + " = ?";

        String[] args = {remindID + ""};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String scheDate = cursor.getString(2);
            int scheID = cursor.getInt(3);
            int soundID = cursor.getInt(4);
            return new Reminder(id, name, scheDate, scheID, soundID);
        }
        return null;
    }
}
