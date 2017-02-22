package top.wuhaojie.week.presenter;

import android.os.Bundle;
import android.view.MenuItem;

import top.wuhaojie.week.base.BasePresenter;
import top.wuhaojie.week.base.BaseView;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/12 23:14
 * Version: 1.0
 */

public interface AboutHolder {

    interface View extends BaseView {
        void back();

        void updateVersionView(String versionName);
    }

    interface Presenter extends BasePresenter {
        void openNetworkOnclick();

        boolean onOptionsItemSelected(MenuItem item);

        void onCreate(Bundle savedInstanceState);
    }

}
