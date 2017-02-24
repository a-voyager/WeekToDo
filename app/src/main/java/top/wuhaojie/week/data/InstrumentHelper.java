package top.wuhaojie.week.data;

import android.content.Context;
import android.content.Intent;

import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.views.NewActivity;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/24 16:57
 * Version: 1.0
 */

public class InstrumentHelper {

    private InstrumentHelper() {
    }

    public static Intent toEditActivity(Context context, int position, TaskDetailEntity entity) {
        Intent intent = new Intent(context, NewActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_EDIT_TASK_DETAIL_ENTITY, entity.cloneObj());
        intent.putExtra(Constants.INTENT_EXTRA_MODE_OF_NEW_ACT, Constants.MODE_OF_NEW_ACT.MODE_EDIT);
        return intent;
    }

}
