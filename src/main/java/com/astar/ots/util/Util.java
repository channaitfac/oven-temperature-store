package com.astar.ots.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);

    public static Date getDate(String date) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(date);
    }
}
