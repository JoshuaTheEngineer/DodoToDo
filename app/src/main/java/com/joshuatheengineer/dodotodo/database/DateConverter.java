package com.joshuatheengineer.dodotodo.database;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Helps with Date conversion with SQLite database
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timeStamp) {
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date) {
        return  date == null ? null : date.getTime();
    }
}
