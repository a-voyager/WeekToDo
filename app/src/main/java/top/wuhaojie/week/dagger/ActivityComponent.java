package top.wuhaojie.week.dagger;

import dagger.Component;
import top.wuhaojie.week.adpter.ListAdapter;
import top.wuhaojie.week.adpter.MainPageAdapter;
import top.wuhaojie.week.presenter.MainPresenter;
import top.wuhaojie.week.views.AboutActivity;
import top.wuhaojie.week.views.ListActivity;
import top.wuhaojie.week.views.MainActivity;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/12 23:32
 * Version: 1.0
 */

@Component(modules = {ActivityModule.class, DataModule.class})
public interface ActivityComponent {
    void inject(AboutActivity aboutActivity);

    void inject(MainActivity aboutActivity);

    void inject(MainPresenter presenter);

    void inject(MainPageAdapter adapter);

    void inject(ListActivity listActivity);

    void inject(ListAdapter adapter);

}
