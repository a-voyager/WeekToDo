package top.wuhaojie.week.dagger;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import top.wuhaojie.week.views.AboutActivity;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/12 23:32
 * Version: 1.0
 */

@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(AboutActivity aboutActivity);

    Activity activity();

    Context context();

}
