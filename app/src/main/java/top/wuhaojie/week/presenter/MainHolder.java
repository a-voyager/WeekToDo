package top.wuhaojie.week.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import top.wuhaojie.week.adpter.MainPageAdapter;
import top.wuhaojie.week.base.BasePresenter;
import top.wuhaojie.week.base.BaseView;
import top.wuhaojie.week.entities.TaskDetailEntity;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/13 12:01
 * Version: 1.0
 */

public interface MainHolder {

    interface View extends BaseView {

        int getCurrentViewPagerItem();

        void startActivityAndForResult(Intent intent, int newActivityRequestCode);

        void finishActivity();

        void setViewPagerAdapter(MainPageAdapter adapter);

        Intent getActivityIntent();

        void setViewPagerCurrentItem(int currIndex, boolean b);

        void showAction(String message, String action, android.view.View.OnClickListener listener);

        void showDialog(int position, TaskDetailEntity entity);

    }

    interface Presenter extends BasePresenter {
        void onBackPressed();

        void onFabClick();

        boolean onOptionsItemSelected(MenuItem item);

        void onCreate(Bundle savedInstanceState);

        void onDestroy();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onListTaskItemLongClick(int position, TaskDetailEntity entity);

        void dialogActionFlagTask(int position, TaskDetailEntity entity);

        void onListTaskItemClick(int position, TaskDetailEntity entity);

        void dialogActionEditTask(int position, TaskDetailEntity entity);

        void dialogActionDeleteTask(int position, TaskDetailEntity entity);

        void dialogActionPutOffTask(int position, TaskDetailEntity entity);
    }
}
