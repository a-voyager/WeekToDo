package top.wuhaojie.week.dagger;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/12 23:33
 * Version: 1.0
 */
@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    public AppCompatActivity getActivity() {
        return mActivity;
    }

    @Provides
    public Context getContext() {
        return mActivity;
    }

    @Provides
    public FragmentManager fragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

}
