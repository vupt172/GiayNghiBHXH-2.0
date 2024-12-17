package com.vupt.application.utils;


import com.vupt.application.constant.DatetimePattern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {

    public static String format(DatetimePattern dtp, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dtp.getPattern());
        return sdf.format(date);
    }

    public static Date parse(DatetimePattern dtp, String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(dtp.getPattern());
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
    public static String getDateNow(String pattern) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }
    public static String getDateTimeNow(String pattern){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    public static String includeTimeToString(String name) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHMMss");
        String formattedDateTime = dateTime.format(formatter);
        return String.format(name + "_%s", formattedDateTime);
    }

}
