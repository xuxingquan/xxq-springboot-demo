package com.xxq.common.util;

import lombok.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    private static DateTimeFormatter YMDHSM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm");

    /**
     * 日期时间转换  Date --> LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(@NonNull Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);//instant.atZone(zoneId).toLocalDateTime();
    }

    public static String defaultFormat(@NonNull Date date){
        LocalDateTime dateTime = toLocalDateTime(date);
        return dateTime.format(YMDHSM_FORMATTER);
    }

    /**
     * 日期时间转换  LocalDateTime --> Date
     * @param dateTime
     * @return
     */
    public static Date toDate(@NonNull LocalDateTime dateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = dateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
}
