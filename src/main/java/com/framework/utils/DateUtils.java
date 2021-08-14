package com.framework.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private DateUtils(){}

    public static LocalDate currentDate(){
        return LocalDate.now(ZoneId.of("Asia/Seoul"));
    }

    public static LocalDateTime currentDateTime(){
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public static String currentDateFormatString(){
        return LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DATE_FORMAT);
    }

    public static String currentDateTimeFormatString(){
        return LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DATETIME_FORMAT);
    }

    public static String dateToFormatString(LocalDate date){
        return date.format(DATE_FORMAT);
    }

    public static String dateTimeToFormatString(LocalDateTime dateTime){
        return dateTime.format(DATETIME_FORMAT);
    }

    public static LocalDateTime TimestampToLDT(Timestamp timestamp){
        return timestamp.toLocalDateTime();
    }

    public static Timestamp LDTToTimestamp(LocalDateTime ldt){
        return Timestamp.valueOf(dateTimeToFormatString(ldt));
    }

}
