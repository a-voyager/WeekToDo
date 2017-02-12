package top.wuhaojie.week.dagger;

import android.app.Activity;
import android.content.Context;

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

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    public Activity getActivity() {
        return mActivity;
    }

    @Provides
    public Context getContext() {
        return mActivity;
    }

}
