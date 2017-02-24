package top.wuhaojie.week.presenter;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import top.wuhaojie.week.base.BasePresenter;
import top.wuhaojie.week.base.BaseView;
import top.wuhaojie.week.entities.TaskDetailEntity;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/21 20:58
 * Version: 1.0
 */

public interface ListHolder {


    interface View extends BaseView {
        Intent intent();

        void updateToolbarTitle(String s);

        void showNoResults();

        void hideNoResults();

        void updateList(List<TaskDetailEntity> list);

        void startActivityAndForResult(Intent intent, int requestCode);

        void finishActivity();
    }

    interface Presenter extends BasePresenter {
        void onCreate(Bundle savedInstanceState);

        void onDestroy();

        void onItemClick(int position, TaskDetailEntity entity);
    }

}
