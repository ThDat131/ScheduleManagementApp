package com.dtn.schedulemanagementapp.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScheduleController {
    SQLiteDatabase database;
    DBHelper helper;

    public ScheduleController(Context context) {
        helper = DBHelper.getInstance(context);
        database = helper.getWritableDatabase();
    }

    public long addNewSchedule(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SCHEDULE_COL_NAME, schedule.getName());
        values.put(DBHelper.SCHEDULE_COL_NOTE, schedule.getNote());
        values.put(DBHelper.SCHEDULE_COL_START_DATE, schedule.getStartDate());
        values.put(DBHelper.SCHEDULE_COL_END_DATE, schedule.getEndDate());
        values.put(DBHelper.SCHEDULE_COL_CATE_ID, schedule.getCateId());
        values.put(DBHelper.SCHEDULE_COL_USERNAME, schedule.getUserId());
        return database.insert(DBHelper.TABLE_SCHEDULE, null, values);
    }

    public long updateSchedule(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SCHEDULE_COL_NAME, schedule.getName());
        values.put(DBHelper.SCHEDULE_COL_NOTE, schedule.getNote());
        values.put(DBHelper.SCHEDULE_COL_START_DATE, schedule.getStartDate());
        values.put(DBHelper.SCHEDULE_COL_END_DATE, schedule.getEndDate());
        values.put(DBHelper.SCHEDULE_COL_CATE_ID, schedule.getCateId());
        values.put(DBHelper.SCHEDULE_COL_USERNAME, schedule.getUserId());
        String[] args = {schedule.getId() + ""};
        return database.update(DBHelper.TABLE_SCHEDULE, values,DBHelper.SCHEDULE_COL_ID, args);
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
                + "FROM " + DBHelper.TABLE_SCHEDULE
                + " WHERE " + DBHelper.SCHEDULE_COL_START_DATE + " >= ? OR " + DBHelper.SCHEDULE_COL_END_DATE + " < ?";

        String[] args = {dateStringStart, dateStringEnd};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String note = cursor.getString(2);
            String startDate = cursor.getString(3);
            String endDate = cursor.getString(4);
            int cateId = cursor.getInt(5);
            String username = cursor.getString(6);

            schedules.add(new Schedule(id, name, note, startDate, endDate, username, cateId));

        }
        return schedules;
    }

    public Schedule getScheduleByScheduleID(int scheduleID) throws ParseException {
        String query = "SELECT * "
                + "FROM " + DBHelper.TABLE_SCHEDULE
                + " WHERE " + DBHelper.SCHEDULE_COL_ID + " >= ?";

        String[] args = {scheduleID + ""};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String note = cursor.getString(2);
            String startDate = cursor.getString(3);
            String endDate = cursor.getString(4);
            int cateId = cursor.getInt(5);
            String username = cursor.getString(6);
            return new Schedule(id, name, note, startDate, endDate, username, cateId);
        }
        return null;
    }
}
