package top.wuhaojie.week.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatDelegate;

import top.wuhaojie.week.R;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.data.AlarmHelper;
import top.wuhaojie.week.utils.PreferenceUtils;
import top.wuhaojie.week.views.SettingsActivity;

/**
 * Created by wuhaojie on 2016/12/11 14:05.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);


        SettingsActivity activity = (SettingsActivity) getActivity();

        boolean autoNight = PreferenceUtils.getInstance(getActivity()).getBooleanParam(Constants.CONFIG_KEY.AUTO_SWITCH_NIGHT_MODE, true);
        if (autoNight) {
            findPreference(Constants.CONFIG_KEY.NIGHT_MODE).setEnabled(false);
        } else {
            findPreference(Constants.CONFIG_KEY.NIGHT_MODE).setEnabled(true);
        }


        findPreference(Constants.CONFIG_KEY.SHOW_WEEK_TASK).setOnPreferenceChangeListener((preference1, newValue) -> {
            boolean b = (boolean) newValue;
            preference1.setSummary(b ? "主界面任务列表仅显示本周任务" : "主界面任务列表显示所有任务");
            return true;
        });

        findPreference(Constants.CONFIG_KEY.SHOW_AS_LIST).setOnPreferenceChangeListener((preference, newValue) -> {
            String s = (String) newValue;
            if (s.equals("list")) {
                preference.setSummary("列表形式展示任务列表");
            } else {
                preference.setSummary("网格形式展示任务列表");
            }
            return true;
        });


        findPreference(Constants.CONFIG_KEY.SHOW_PRIORITY).setOnPreferenceChangeListener((preference, newValue) -> {
            boolean b = (boolean) newValue;
            if (b)
                preference.setSummary("在任务卡片中将显示优先级");
            else
                preference.setSummary("在任务卡片中将不会显示优先级");
            return true;
        });


        findPreference(Constants.CONFIG_KEY.AUTO_SWITCH_NIGHT_MODE).setOnPreferenceChangeListener((preference, newValue) -> {
            boolean b = (boolean) newValue;
            Preference nightPreference = findPreference(Constants.CONFIG_KEY.NIGHT_MODE);
            if (b) {
                nightPreference.setEnabled(false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                activity.switchNightMode(false, true);

            } else {
                nightPreference.setEnabled(true);
                boolean toNight = PreferenceUtils.getInstance(getActivity()).getBooleanParam(Constants.CONFIG_KEY.NIGHT_MODE, false);
                if (toNight)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                activity.switchNightMode(toNight);
            }
            return true;
        });

        findPreference(Constants.CONFIG_KEY.NIGHT_MODE).setOnPreferenceChangeListener((preference, newValue) -> {
            boolean b = (boolean) newValue;
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                activity.switchNightMode(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                activity.switchNightMode(false);
            }
            return true;
        });

        // 备份内容
        findPreference(Constants.CONFIG_KEY.BACKUP).setOnPreferenceClickListener(preference -> {
            activity.backupClick();
            return true;
        });

        // 还原内容
        findPreference(Constants.CONFIG_KEY.RECOVERY).setOnPreferenceClickListener(preference -> {
            activity.recoveryClick();
            return true;
        });

        // 智能提醒完成任务
        findPreference(Constants.CONFIG_KEY.AUTO_NOTIFY).setOnPreferenceChangeListener((p, n) -> {
            boolean b = (boolean) n;
            if (b) {
                AlarmHelper.startNotifyAlarm(activity);
            } else {
                AlarmHelper.cancelNotifyAlarm(activity);
            }
            return true;
        });


    }
}
