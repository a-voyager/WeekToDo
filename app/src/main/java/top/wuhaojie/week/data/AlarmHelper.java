package top.wuhaojie.week.data;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.service.NotifyService;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by wuhaojie on 17-2-10.
 */

public final class AlarmHelper {

    private AlarmHelper() {
    }


    public static void startNotifyAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, new Intent(context, NotifyService.class), PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Constants.AUTO_NOTIFY_INTERVAL_TIME, pendingIntent);
    }

    public static void cancelNotifyAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, new Intent(context, NotifyService.class), PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

}
