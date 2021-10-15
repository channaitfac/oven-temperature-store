package com.astar.ots.util;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;

public class Util {

    public static Date getDate(String date) throws ParseException {
        return Constants.SIMPLE_DATE_FORMAT.parse(date);
    }

    public static Date removeMilliseconds(Date date) {

        try {
            return getDate(date.toInstant().atZone(ZoneId.of(Constants.SG_TIME_ZONE)).format(Constants.DATETIME_FORMATTER));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
