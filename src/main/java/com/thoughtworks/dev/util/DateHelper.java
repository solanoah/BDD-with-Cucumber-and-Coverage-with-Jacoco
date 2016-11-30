package com.thoughtworks.dev.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    /**
     * Date format for display
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mma");

    /**
     * Create a Date time based on input hour
     * @param hour Hour of the day
     * @return Time for a given hour
     */
    public static Date createTime(int hour) {

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, hour);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        return now.getTime();
    }
}
