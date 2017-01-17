package top.wuhaojie.week.data;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.entities.TaskState;
import top.wuhaojie.week.utils.DateUtils;
import top.wuhaojie.week.utils.FileUtils;

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
        return Observable
                .fromCallable(() -> {
                    List<TaskDetailEntity> allTask = DataDao.getInstance().findAllTask();
                    File desDir = new File(Constants.ExternalStorageDirectory + Constants.DATABASE_FILE_EXPORT_PATH_FOLDER);
                    desDir.mkdirs();
                    File desFile = new File(desDir, System.currentTimeMillis() + ".txt");
                    desFile.createNewFile();
                    FileUtils.ItemStringCreator<TaskDetailEntity> creator = (position, taskDetailEntity) ->
                            DateUtils.formatDateTime(taskDetailEntity.getTimeStamp()) +
                                    "\t星期" +
                                    DateUtils.weekNumberToChinese(taskDetailEntity.getDayOfWeek()) +
                                    '\t' +
                                    (taskDetailEntity.getState() == TaskState.FINISHED ? "已完成" : "待完成") +
                                    "\n标题: " +
                                    taskDetailEntity.getTitle() +
                                    "\n正文: " +
                                    taskDetailEntity.getContent() +
                                    '\n';
                    return FileUtils.exportToFile(desFile, allTask, creator);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
