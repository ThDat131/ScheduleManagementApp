package com.dtn.schedulemanagementapp.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dtn.schedulemanagementapp.models.Schedule;
import com.dtn.schedulemanagementapp.models.stats.ScheduleStatsByCategory;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScheduleController {
    SQLiteDatabase database;
    DBHelper helper;

    public ScheduleController(Context context){
        helper = DBHelper.getInstance(context);
        database = helper.getWritableDatabase();
    }

    public long addNewSchedule(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SCHEDULE_COL_NAME,schedule.getName());
        values.put(DBHelper.SCHEDULE_COL_NOTE,schedule.getNote());
        values.put(DBHelper.SCHEDULE_COL_START_DATE,schedule.getStartDate());
        values.put(DBHelper.SCHEDULE_COL_END_DATE,schedule.getEndDate());
        values.put(DBHelper.SCHEDULE_COL_CATE_ID,schedule.getCateId());
        values.put(DBHelper.SCHEDULE_COL_USERNAME,schedule.getUserId());
        return database.insert(DBHelper.TABLE_SCHEDULE, null, values);
    }

    public ArrayList<Schedule> getSchedulesByUsername(String username) {
        ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.TABLE_SCHEDULE + " WHERE " + DBHelper.SCHEDULE_COL_USERNAME + " =?";
        String[] args = {username};
        Cursor cursor = database.rawQuery(query, args);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String note = cursor.getString(2);
            String startDate = cursor.getString(3);
            String endDate = cursor.getString(4);
            int cateId = cursor.getInt(5);
            String usrname = cursor.getString(6);

            Schedule schedule = new Schedule(id, name, note, startDate, endDate, usrname, cateId);
            scheduleArrayList.add(schedule);
        }
        return scheduleArrayList;

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
                + " WHERE " + DBHelper.SCHEDULE_COL_START_DATE + " >= ? AND " + DBHelper.SCHEDULE_COL_END_DATE + " < ?";

        String[] args = {dateStringStart, dateStringEnd};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        while(cursor.moveToNext()) {
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

    public ArrayList<ScheduleStatsByCategory> StatsByCategory(String username) {

        ArrayList<ScheduleStatsByCategory> scheduleNum = new ArrayList<>();
        String query = "SELECT c." + DBHelper.CATEGORY_COL_NAME + ", COUNT(*) AS qty" +
                " FROM " + DBHelper.TABLE_SCHEDULE + " s" +
                " INNER JOIN " +  DBHelper.TABLE_USER + " u, " + DBHelper.TABLE_CATEGORY + " c" +
                " ON s." + DBHelper.SCHEDULE_COL_USERNAME +  " = u." + DBHelper.USER_COL_USERNAME + " AND s." + DBHelper.SCHEDULE_COL_CATE_ID + " = c." + DBHelper.CATEGORY_COL_ID +
                " WHERE u." + DBHelper.USER_COL_USERNAME + "= 'admin'" +
                " AND u." + DBHelper.USER_COL_USERNAME + " = ?" +
                " GROUP BY c." + DBHelper.CATEGORY_COL_ID;

        String []args = {username};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            int qty = cursor.getInt(1);

            scheduleNum.add(new ScheduleStatsByCategory(name, qty));
        }
        return scheduleNum;
    }

    public ArrayList<ScheduleStatsByCategory> StatsByDate(String username, String startDate, String endDate) {

        ArrayList<ScheduleStatsByCategory> scheduleNum = new ArrayList<>();
        String query = "SELECT s." + DBHelper.SCHEDULE_COL_START_DATE + ", COUNT(*) AS qty" +
                " FROM " + DBHelper.TABLE_SCHEDULE + " s" +
                " INNER JOIN " +  DBHelper.TABLE_USER + " u" +
                " ON s." + DBHelper.SCHEDULE_COL_USERNAME +  " = u." + DBHelper.USER_COL_USERNAME +
                " WHERE u." + DBHelper.USER_COL_USERNAME + "= ? AND " + DBHelper.SCHEDULE_COL_START_DATE + " >= ? AND  s." + DBHelper.SCHEDULE_COL_END_DATE + "< ?" +
                " GROUP BY s." + DBHelper.SCHEDULE_COL_START_DATE;

        String []args = {username, startDate, endDate};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, args);

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            int qty = cursor.getInt(1);

            scheduleNum.add(new ScheduleStatsByCategory(name, qty));
        }
        return scheduleNum;
    }

    public boolean isCoincideDate(String startDate, String endDate) {
        String query = "SELECT " + DBHelper.SCHEDULE_COL_ID +
                " FROM " + DBHelper.TABLE_SCHEDULE +
                " WHERE " + DBHelper.SCHEDULE_COL_START_DATE + " < ? AND " + DBHelper.SCHEDULE_COL_END_DATE + " > ?" +
                " OR " + DBHelper.SCHEDULE_COL_START_DATE + " BETWEEN ? AND ?" +
                " OR " + DBHelper.SCHEDULE_COL_END_DATE + " BETWEEN ? AND ?";
        String[] args = {startDate, endDate, startDate, endDate, startDate, endDate};
        Cursor c = database.rawQuery(query, args);
        if (c != null && c.getCount() > 0){
            return false;
        }
        return true;
    }
}
