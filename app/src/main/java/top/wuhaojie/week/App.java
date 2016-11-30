package top.wuhaojie.week;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wuhaojie on 2016/11/30 9:21.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("data.realm")
                .build();
        Realm.setDefaultConfiguration(configuration);
        Fresco.initialize(this);
    }
}
