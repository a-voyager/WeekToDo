package top.wuhaojie.week.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.data.AlarmHelper;
import top.wuhaojie.week.utils.PreferenceUtils;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean autoNotify = PreferenceUtils.getInstance(context).getBooleanParam(Constants.CONFIG_KEY.AUTO_NOTIFY, false);
        if (autoNotify) {
            AlarmHelper.startNotifyAlarm(context);
        }
    }
}
