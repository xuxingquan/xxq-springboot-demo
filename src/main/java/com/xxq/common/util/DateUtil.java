package com.xxq.common.util;

import lombok.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static LocalDateTime toLocalDateTime(@NonNull Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

}
