package top.wuhaojie.week.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatDelegate;

import top.wuhaojie.week.R;
import top.wuhaojie.week.constant.Constants;

/**
 * Created by wuhaojie on 2016/12/11 14:05.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

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


        findPreference(Constants.CONFIG_KEY.NIGHT_MODE).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean b = (boolean) newValue;
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;
            }
        });

    }
}
