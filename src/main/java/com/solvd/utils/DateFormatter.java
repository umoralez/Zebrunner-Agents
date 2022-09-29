package com.solvd.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public static String getCurrentTime() {
        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat dateFormatted = new SimpleDateFormat("yyyy - MM - dd’T’HH:mm: ss.SSSXXX");
        return dateFormatted.format(date);
    }
}
