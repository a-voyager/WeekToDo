package top.wuhaojie.week.constant;

import android.os.Environment;

/**
 * Created by wuhaojie on 2016/12/1 13:37.
 */

public interface Constants {
    int NEW_ACTIVITY_REQUEST_CODE = 0;
    int EDIT_ACTIVITY_REQUEST_CODE = 1;

    String INTENT_EXTRA_DAY_OF_WEEK = "INTENT_EXTRA_DAY_OF_WEEK";
    String INTENT_BUNDLE_NEW_TASK_DETAIL = "INTENT_BUNDLE_NEW_TASK_DETAIL";
    String INTENT_EXTRA_EDIT_TASK_DETAIL_ENTITY = "INTENT_EXTRA_EDIT_TASK_DETAIL_ENTITY";
    String INTENT_EXTRA_MODE_OF_NEW_ACT = "INTENT_EXTRA_MODE_OF_NEW_ACT";
    String INTENT_EXTRA_SWITCH_TO_INDEX = "INTENT_EXTRA_SWITCH_TO_INDEX";

    String CHOOSE_PAPER_DIALOG_CHECK_ITEM_BUNDLE_KEY = "CHOOSE_PAPER_DIALOG_CHECK_ITEM_BUNDLE_KEY";

    int HANDLER_WHAT_DELETE_TASK = 0;
    int HANDLER_WHAT_PUT_OFF_TASK = 1;

    String ExternalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    String DATABASE_FILE_PATH_FOLDER = "WeekToDo";
    String DATABASE_FILE_PATH_FILE_NAME = "data.realm";
    String DATABASE_FILE_BACKUP_PATH_FOLDER = "番茄周/备份";
    String DATABASE_FILE_EXPORT_PATH_FOLDER = "番茄周/导出";

    long AUTO_NOTIFY_INTERVAL_TIME = 60 * 60 * 1000;

    int AUTO_NOTIFY_NOTIFICATION_ID = 0;

    interface MODE_OF_NEW_ACT {
        int MODE_EDIT = 5;
        int MODE_CREATE = 6;
        int MODE_QUICK = 7;
    }

    interface CONFIG_KEY {
        String SHOW_WEEK_TASK = "SHOW_WEEK_TASK";
        String SHOW_AS_LIST = "SHOW_AS_LIST";
        String SHOW_PRIORITY = "SHOW_PRIORITY";
        String NIGHT_MODE = "NIGHT_MODE";
        String AUTO_SWITCH_NIGHT_MODE = "AUTO_SWITCH_NIGHT_MODE";
        String BACKUP = "BACKUP";
        String RECOVERY = "RECOVERY";
        String AUTO_NOTIFY = "AUTO_NOTIFY";
    }


}
