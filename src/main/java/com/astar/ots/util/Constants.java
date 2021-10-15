package com.astar.ots.util;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public interface Constants {

    String FORWARD_SLASH = "/";
    String SG_TIME_ZONE = "Asia/Singapore";
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
    DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);

    interface RestUrls {
        String OVENS = "ovens";
        String TEMPERATURE = "temperature";
        String OVEN_TEMPERATURES = "oven-temperatures";
    }

    interface ErrorMessages {
        String CANNOT_FOUND_OVEN = "Can not found any Oven record(s)";
        String INVALID_PARAMETER = "Invalid parameter(s)";
        String CANNOT_FOUND_OVEN_FOR_ID = "Can not found any Oven for given Id";
    }
}
