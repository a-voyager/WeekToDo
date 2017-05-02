package top.wuhaojie.week.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import java.util.Calendar;

import io.realm.RealmResults;
import top.wuhaojie.week.R;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.data.DataDao;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.utils.DateUtils;
import top.wuhaojie.week.views.MainActivity;

public class NotifyService extends Service {
    public NotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        int code = super.onStartCommand(intent, flags, startId);
        long sundayTimeMillisOfWeek = DateUtils.getFirstSundayTimeMillisOfWeek();
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        RealmResults<TaskDetailEntity> results = DataDao.getInstance().findUnFinishedTasks(day, sundayTimeMillisOfWeek);
        if (results.size() == 0) return code;
        StringBuilder sb = new StringBuilder();
        sb.append("包含: ");
        for (int i = 0; i < results.size() - 1; i++) {
            sb.append(results.get(i).getTitle());
            sb.append(", ");
        }
        sb.append(results.get(results.size() - 1).getTitle());
        String content = sb.toString();


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        android.support.v4.app.NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.icon_red, "查看", pendingIntent);
        Notification notification = new NotificationCompat
                .Builder(this)
                .setAutoCancel(true)
                .addAction(action)
                .setContentTitle("您今日还有未完成的任务")
                .setContentText(content)
                .setSmallIcon(R.drawable.icon_red)
                .build();
        manager.notify(Constants.AUTO_NOTIFY_NOTIFICATION_ID, notification);

        return code;
    }
}
