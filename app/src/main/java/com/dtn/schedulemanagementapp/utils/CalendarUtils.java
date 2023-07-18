package com.dtn.schedulemanagementapp.utils;

import android.util.Log;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.PropertyPermission;

public class CalendarUtils {
    public static Date StringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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


}
