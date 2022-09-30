package com.solvd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Optional;

public class DateFormatter {
    private static final Logger LOGGER = LogManager.getLogger(DateFormatter.class);
    public static String getCurrentTime() {
        return OffsetDateTime.now().toString();

    }

    public static OffsetDateTime getCurrentTime(String time) {
        return OffsetDateTime.parse(time);

    }
}
