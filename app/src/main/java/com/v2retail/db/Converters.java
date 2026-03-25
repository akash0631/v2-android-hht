package com.v2retail.db;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date fromTimeStamp(Long timeStamp){

        return timeStamp == null ? null : new Date(timeStamp);

    }

    @TypeConverter
    public static Long fromDate(Date date){

        return date == null ? null : date.getTime();

    }
}
