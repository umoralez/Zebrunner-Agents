package com.solvd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.OffsetDateTime;

public class DateFormatter {
    private static final Logger LOGGER = LogManager.getLogger(DateFormatter.class);
    public static String getCurrentTime() {
        return OffsetDateTime.now().toString();

    }
}
