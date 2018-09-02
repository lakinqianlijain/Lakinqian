package com.qlj.lakinqiandemo.utils;

import java.util.Formatter;
import java.util.Locale;

public class FormatUtil {

    public static String getTimeString(int milliSeconds) {
        long seconds = (milliSeconds % 60000L) / 1000L;
        long minutes = (milliSeconds % 3600000L) / 60000L;
        long hours = (milliSeconds % 86400000L) / 3600000L;
        long days = (milliSeconds % (86400000L * 7L)) / 86400000L;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(0);
        Formatter formatter = new Formatter(stringBuilder, Locale.getDefault());
        return days > 0
                ? formatter.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds).toString()
                : hours > 0
                ? formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
                : formatter.format("%02d:%02d", minutes, seconds).toString();
    }
}
