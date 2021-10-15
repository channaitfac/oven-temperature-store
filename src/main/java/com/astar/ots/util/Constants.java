package com.astar.ots.util;

public interface Constants {

    String FORWARD_SLASH = "/";
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

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
