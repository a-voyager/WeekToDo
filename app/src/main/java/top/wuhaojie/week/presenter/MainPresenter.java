package top.wuhaojie.week.presenter;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import top.wuhaojie.week.R;
import top.wuhaojie.week.adpter.MainPageAdapter;
import top.wuhaojie.week.base.BaseView;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.data.AlarmHelper;
import top.wuhaojie.week.data.DataDao;
import top.wuhaojie.week.data.InstrumentHelper;
import top.wuhaojie.week.entities.MainPageItem;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.entities.TaskState;
import top.wuhaojie.week.fragments.PageFragment;
import top.wuhaojie.week.utils.DateUtils;
import top.wuhaojie.week.utils.PreferenceUtils;
import top.wuhaojie.week.views.NewActivity;
import top.wuhaojie.week.views.SettingsActivity;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/13 12:02
 * Version: 1.0
 */

public class MainPresenter implements MainHolder.Presenter {

    private static final String TAG = "MainPresenter";
    private MainHolder.View mView;
    private Context mContext;
    private int mLastClickedItemPosition;

    @Inject
    List<MainPageItem> mItems;
    @Inject
    MainPageAdapter mAdapter;
    @Inject
    DataDao mDataDao;

    @Inject
    public MainPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void bindView(BaseView view) {
        mView = (MainHolder.View) view;
    }


    @Override
    public void onBackPressed() {
        mView.finishActivity();
    }

    @Override
    public void onFabClick() {
        int i = mView.getCurrentViewPagerItem();
        Intent intent = new Intent(mContext, NewActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_DAY_OF_WEEK, i + 1);
        intent.putExtra(Constants.INTENT_EXTRA_MODE_OF_NEW_ACT, Constants.MODE_OF_NEW_ACT.MODE_CREATE);
        mView.startActivityAndForResult(intent, Constants.NEW_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(mContext, SettingsActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_SWITCH_TO_INDEX, mView.getCurrentViewPagerItem());
                mContext.startActivity(intent);
                mView.finishActivity();
                break;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mView.setViewPagerAdapter(mAdapter);

        int currIndex;
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        currIndex = dayOfWeek - 1;
        Intent intent = mView.getActivityIntent();
        if (intent != null) {
            currIndex = intent.getIntExtra(Constants.INTENT_EXTRA_SWITCH_TO_INDEX, currIndex);
        }
        mView.setViewPagerCurrentItem(currIndex, true);


        // 坑: mVp.getCurrentItem() 某些时候不能获得第一页和最后一页的Index

        for (int i = 0; i < 7; i++) {
            ((PageFragment) mItems.get(i).getFragment()).clearTasks();
        }


        RealmResults<TaskDetailEntity> allTask = null;
        if (PreferenceUtils.getInstance(mContext).getBooleanParam(Constants.CONFIG_KEY.SHOW_WEEK_TASK))
            allTask = mDataDao.findAllTaskOfThisWeekFromSunday();
        else
            allTask = mDataDao.findAllTask();
        for (TaskDetailEntity t : allTask) {
            int day = t.getDayOfWeek();
            PageFragment fragment = (PageFragment) mItems.get(day - 1).getFragment();
            fragment.insertTask(t);
        }


        // 夜间模式
        if (PreferenceUtils.getInstance(mContext).getBooleanParam(Constants.CONFIG_KEY.AUTO_SWITCH_NIGHT_MODE, true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        } else {
            boolean isNight = PreferenceUtils.getInstance(mContext).getBooleanParam(Constants.CONFIG_KEY.NIGHT_MODE, false);
            if (isNight)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // 智能提醒未完成任务
        if (PreferenceUtils.getInstance(mContext).getBooleanParam(Constants.CONFIG_KEY.AUTO_NOTIFY, false))
            AlarmHelper.startNotifyAlarm(mContext);
        else
            AlarmHelper.cancelNotifyAlarm(mContext);

        // 清除通知
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(Constants.AUTO_NOTIFY_NOTIFICATION_ID);
    }

    @Override
    public void onDestroy() {
        mItems = null;
        mDataDao.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        Bundle bundle = data.getExtras();
        TaskDetailEntity task = (TaskDetailEntity) bundle.getSerializable(Constants.INTENT_BUNDLE_NEW_TASK_DETAIL);
        PageFragment fragment = (PageFragment) mItems.get(mView.getCurrentViewPagerItem()).getFragment();

        if (requestCode == Constants.NEW_ACTIVITY_REQUEST_CODE) {
            fragment.insertTask(task);
            mDataDao.insertTask(task);
        } else if (requestCode == Constants.EDIT_ACTIVITY_REQUEST_CODE) {
            TaskDetailEntity oldTask = fragment.deleteTask(mLastClickedItemPosition);
            fragment.insertTask(task);
            mDataDao.editTask(oldTask, task);
        }

    }

    @Override
    public void onListTaskItemLongClick(int position, TaskDetailEntity entity) {
        mView.showDialog(position, entity);
    }

    private void toEditActivity(int position, TaskDetailEntity entity) {
        Intent intent = InstrumentHelper.toEditActivity(mContext, position, entity);
        mView.startActivityAndForResult(intent, Constants.EDIT_ACTIVITY_REQUEST_CODE);
        mLastClickedItemPosition = position;
    }

    @Override
    public void dialogActionFlagTask(int position, TaskDetailEntity entity) {
        int newState = TaskState.DEFAULT;
        if (entity.getState() == TaskState.DEFAULT) {
            newState = TaskState.FINISHED;
        } else {
            newState = TaskState.DEFAULT;
        }
        switchTaskState(position, entity, newState);
    }

    @Override
    public void onListTaskItemClick(int position, TaskDetailEntity entity) {
        toEditActivity(position, entity);
    }

    @Override
    public void dialogActionEditTask(int position, TaskDetailEntity entity) {
        toEditActivity(position, entity);
    }

    @Override
    public void dialogActionDeleteTask(int position, TaskDetailEntity entity) {
        Subscription subscription = deleteTaskWithDelay(position, entity);
        mView.showAction("即将删除", "撤销", v1 -> subscription.unsubscribe());
    }

    @Override
    public void dialogActionPutOffTask(int position, TaskDetailEntity entity) {
        Subscription subscription = putOffTaskOneDayWithDelay(position, entity);
        mView.showAction("该任务即将推延一天", "撤销", v12 -> subscription.unsubscribe());
    }


    private void switchTaskState(int position, TaskDetailEntity entity, int newState) {
        PageFragment fragment = (PageFragment) mItems.get(mView.getCurrentViewPagerItem()).getFragment();
        mDataDao.switchTaksState(entity, newState);
        fragment.getAdapter().notifyItemChanged(position);
    }


    private Subscription putOffTaskOneDayWithDelay(int position, TaskDetailEntity entity) {
        return Observable
                .just(1)
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(a -> {
                    putOffTaskOneDay(position, entity);
                });
    }

    private void putOffTaskOneDay(int position, TaskDetailEntity entity) {

        PageFragment fragment = (PageFragment) mItems.get(mView.getCurrentViewPagerItem()).getFragment();
        TaskDetailEntity oldEntity = fragment.deleteTask(position);
        TaskDetailEntity newEntity = oldEntity.cloneObj();
        newEntity.setDayOfWeek(DateUtils.calNextDayDayOfWeek(oldEntity.getDayOfWeek()));
        ((PageFragment) mItems.get(newEntity.getDayOfWeek() - 1).getFragment()).insertTask(newEntity);
        mDataDao.insertTask(newEntity);
        mDataDao.deleteTask(oldEntity);

    }

    private Subscription deleteTaskWithDelay(int position, TaskDetailEntity entity) {
        return Observable.just(1)
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    deleteTask(position, entity);
                });

    }

    private void deleteTask(int position, TaskDetailEntity entity) {
        PageFragment fragment = (PageFragment) mItems.get(mView.getCurrentViewPagerItem()).getFragment();
        DataDao dao = DataDao.getInstance();
        fragment.deleteTask(position);
        dao.deleteTask(entity);
    }

}
