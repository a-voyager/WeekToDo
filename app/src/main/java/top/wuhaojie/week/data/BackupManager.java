package top.wuhaojie.week.data;

import java.io.File;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.views.FileUtils;

/**
 * Created by wuhaojie on 2017/1/15 19:22.
 */

public class BackupManager {

    private BackupManager() {
    }

    public static Observable<Boolean> backup() {
        File src = new File(Constants.ExternalStorageDirectory + Constants.DATABASE_FILE_PATH_FOLDER, Constants.DATABASE_FILE_PATH_FILE_NAME);
        File desDir = new File(Constants.ExternalStorageDirectory + Constants.DATABASE_FILE_BACKUP_PATH_FOLDER);
        desDir.mkdirs();
        File des = new File(desDir, src.getName());

        return Observable
                .fromCallable(() -> FileUtils.copyFile(src, des))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static Observable<Boolean> export() {
        // TODO: 2017/1/15 增加导出功能
        return null;
    }


    public static Observable<Boolean> recovery() {
        File des = new File(Constants.ExternalStorageDirectory + Constants.DATABASE_FILE_PATH_FOLDER, Constants.DATABASE_FILE_PATH_FILE_NAME);
        File src = new File(Constants.ExternalStorageDirectory + Constants.DATABASE_FILE_BACKUP_PATH_FOLDER, des.getName());
        return Observable
                .fromCallable(() -> FileUtils.copyFile(src, des))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
