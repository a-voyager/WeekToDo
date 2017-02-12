package top.wuhaojie.week;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.utils.ImageLoader;
import top.wuhaojie.week.utils.GlideImageLoader;

/**
 * Created by wuhaojie on 2016/11/30 9:21.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
//        LeakCanary.install(this);
        // Normal app init code...

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        CrashReport.initCrashReport(getApplicationContext(), "684db223b4", true, strategy);
        strategy.setAppChannel("Test");
//        Bugly.init(getApplicationContext(), "684db223b4", true, strategy);


        File file = null;
        try {
            file = new File(Constants.ExternalStorageDirectory, Constants.DATABASE_FILE_PATH_FOLDER);
            file.mkdirs();
        } catch (Exception e) {

        }

        Realm.init(this);
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder()
                .name(Constants.DATABASE_FILE_PATH_FILE_NAME);
        // custom file not work on Android 7.0
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N && file != null)
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N && file != null)
                builder.directory(file);
        } catch (Exception e) {
            Toast.makeText(this, "为了得到更佳的用户体验, 请在设置中授予本应用相关权限", Toast.LENGTH_LONG).show();
        }
        Realm.setDefaultConfiguration(builder.build());
//        Fresco.initialize(this);
        ImageLoader.init(GlideImageLoader.create(this));

    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        ImageLoader.shutdown();
    }
}
