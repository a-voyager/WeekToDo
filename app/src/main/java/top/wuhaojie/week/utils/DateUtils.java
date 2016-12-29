package top.wuhaojie.week.utils;

import java.util.Calendar;

/**
 * Created by wuhaojie on 2016/12/10 19:53.
 */

public class DateUtils {


    private DateUtils() {
    }

    public static long getFirstSundayTimeMillisOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int firstDayOfWeek = calendar.getFirstDayOfWeek();

        int daysFromSunday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        long secondOfToday = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);

        long millisFromSunday = (daysFromSunday * 24 * 60 * 60 + secondOfToday) * 1000;

        return System.currentTimeMillis() - millisFromSunday;
    }

    public static int calNextDayDayOfWeek(int currDayOfWeek) {
        int i = (currDayOfWeek + 1) % 8;
        if (i == 0) i = 1;
        return i;
    }


}
