package com.dtn.schedulemanagementapp.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.PropertyPermission;

public class CalendarUtils {
    public static Date StringToDate(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate parsedDate = LocalDate.parse(date, formatter);
        return Date.from(parsedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String DateToString(Date date, String format) {
        String dateString = "";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dateString = localDate.format(formatter);

        } catch (DateTimeParseException ex) {
            Log.d("format", ex.toString());
        }
        return dateString;
    }

    public static String StringToString(String date, String originalFormat, String newFormat) {
        String dateString = "";
        SimpleDateFormat originalDateFormat = new SimpleDateFormat(originalFormat);
        SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat);
        try {
            Date d = originalDateFormat.parse(date);
            return newDateFormat.format(d);

        }catch (Exception ex) {
            Log.d("format", ex.toString());
        }
        return null;
    }


}
