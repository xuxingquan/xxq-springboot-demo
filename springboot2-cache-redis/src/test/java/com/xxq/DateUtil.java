package com.xxq;

import java.text.DecimalFormat;
import java.util.Objects;

public class DateUtil {
    private static ThreadLocal<DecimalFormat> threadLocal = ThreadLocal.withInitial(() -> new DecimalFormat("#.0"));
    public static String format(Float srcNumber) {
        return (Objects.isNull(srcNumber) || srcNumber.compareTo(0.0f) == 0) ? "0.0" : threadLocal.get().format(srcNumber);
    }
}
