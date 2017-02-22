package top.wuhaojie.week.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import javax.inject.Inject;

import top.wuhaojie.week.BuildConfig;
import top.wuhaojie.week.base.BaseView;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/12 23:20
 * Version: 1.0
 */
public class AboutPresenter implements AboutHolder.Presenter {

    private AboutHolder.View mView;
    private Context mContext;

    @Inject
    public AboutPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void bindView(BaseView view) {
        mView = (AboutHolder.View) view;
    }

    @Override
    public void openNetworkOnclick() {
        Uri uri = Uri.parse("https://github.com/a-voyager/WeekToDo");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(it);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mView.back();
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String versionName = "番茄周  " + "v" + BuildConfig.VERSION_NAME;
        if (BuildConfig.DEBUG) versionName += " (调试版)";
        mView.updateVersionView(versionName);
    }
}
