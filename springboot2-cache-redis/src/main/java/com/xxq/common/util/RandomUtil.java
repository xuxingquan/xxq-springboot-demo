package com.xxq.common.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RandomUtil {
    public static String random(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MMddHHmmssSSS");
        String nowFormatStr = now.format(pattern);
        return nowFormatStr+ RandomStringUtils.randomNumeric(4);
    }
}
