package com.xxq.common.util;

import lombok.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    private static DateTimeFormatter YMDHSM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm");

    public static LocalDateTime toLocalDateTime(@NonNull Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static String format(@NonNull Date date){
        LocalDateTime dateTime = toLocalDateTime(date);
        return dateTime.format(YMDHSM_FORMATTER);
    }

}
