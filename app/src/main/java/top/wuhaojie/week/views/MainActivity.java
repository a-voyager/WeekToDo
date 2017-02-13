package top.wuhaojie.week.views;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import top.wuhaojie.week.R;
import top.wuhaojie.week.adpter.MainPageAdapter;
import top.wuhaojie.week.base.BaseActivity;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.dagger.ActivityModule;
import top.wuhaojie.week.dagger.DaggerActivityComponent;
import top.wuhaojie.week.data.AlarmHelper;
import top.wuhaojie.week.data.DataDao;
import top.wuhaojie.week.data.PageFactory;
import top.wuhaojie.week.entities.MainPageItem;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.entities.TaskState;
import top.wuhaojie.week.fragments.PageFragment;
import top.wuhaojie.week.presenter.MainHolder;
import top.wuhaojie.week.presenter.MainPresenter;
import top.wuhaojie.week.utils.DateUtils;
import top.wuhaojie.week.utils.PreferenceUtils;
import top.wuhaojie.week.utils.SnackBarUtils;

public class MainActivity extends BaseActivity implements PageFragment.OnPageFragmentInteractionListener, MainHolder.View {

    public static final String TAG = "MainActivity";
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.cl_main)
    CoordinatorLayout mClMain;
    private List<MainPageItem> mItems;
    private int mLastClickedItemPosition;

    @Inject
    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.bindView(this);

        mItems = PageFactory.createPages();

        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), mItems);

        mTab.setupWithViewPager(mVp);
        mVp.setAdapter(adapter);


        int currIndex;
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        currIndex = dayOfWeek - 1;
        Intent intent = getIntent();
        if (intent != null) {
            currIndex = intent.getIntExtra(Constants.INTENT_EXTRA_SWITCH_TO_INDEX, currIndex);
        }
        mVp.setCurrentItem(currIndex, true);


        // 坑: mVp.getCurrentItem() 某些时候不能获得第一页和最后一页的Index

        for (int i = 0; i < 7; i++) {
            ((PageFragment) mItems.get(i).getFragment()).clearTasks();
        }


        DataDao dataDao = DataDao.getInstance();
        RealmResults<TaskDetailEntity> allTask = null;
        if (PreferenceUtils.getInstance(this).getBooleanParam(Constants.CONFIG_KEY.SHOW_WEEK_TASK))
            allTask = dataDao.findAllTaskOfThisWeekFromSunday();
        else
            allTask = dataDao.findAllTask();
        for (TaskDetailEntity t : allTask) {
            int day = t.getDayOfWeek();
            PageFragment fragment = (PageFragment) mItems.get(day - 1).getFragment();
            fragment.insertTask(t);
        }


        // 夜间模式
        if (PreferenceUtils.getInstance(this).getBooleanParam(Constants.CONFIG_KEY.AUTO_SWITCH_NIGHT_MODE, true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        } else {
            boolean isNight = PreferenceUtils.getInstance(this).getBooleanParam(Constants.CONFIG_KEY.NIGHT_MODE, false);
            if (isNight)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // 智能提醒未完成任务
        if (PreferenceUtils.getInstance(this).getBooleanParam(Constants.CONFIG_KEY.AUTO_NOTIFY, false))
            AlarmHelper.startNotifyAlarm(this);

        // 清除通知
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(Constants.AUTO_NOTIFY_NOTIFICATION_ID);

    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mPresenter.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mItems = null;
        DataDao.getInstance().close();
    }

    @OnClick(R.id.fab)
    public void onClick() {
//        DataDao dao = DataDao.getInstance();
//        int i = mVp.getCurrentItem();
//        dao.insertTask(new TaskDetailEntity(i + 1));
//        Log.d(TAG, "insert：" + (i + 1));

        int i = mVp.getCurrentItem();
        Intent intent = new Intent(this, NewActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_DAY_OF_WEEK, i + 1);
        intent.putExtra(Constants.INTENT_EXTRA_MODE_OF_NEW_ACT, Constants.MODE_OF_NEW_ACT.MODE_CREATE);
        startActivityForResult(intent, Constants.NEW_ACTIVITY_REQUEST_CODE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_SWITCH_TO_INDEX, mVp.getCurrentItem());
                startActivity(intent);
                finish();
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        Bundle bundle = data.getExtras();
        TaskDetailEntity task = (TaskDetailEntity) bundle.getSerializable(Constants.INTENT_BUNDLE_NEW_TASK_DETAIL);
        PageFragment fragment = (PageFragment) mItems.get(mVp.getCurrentItem()).getFragment();
        DataDao dao = DataDao.getInstance();

        if (requestCode == Constants.NEW_ACTIVITY_REQUEST_CODE) {
            fragment.insertTask(task);
            dao.insertTask(task);
        } else if (requestCode == Constants.EDIT_ACTIVITY_REQUEST_CODE) {
            TaskDetailEntity oldTask = fragment.deleteTask(mLastClickedItemPosition);
            fragment.insertTask(task);
            dao.editTask(oldTask, task);
        }


    }

    @Override
    public void toEditActivity(int position, TaskDetailEntity entity) {
        Intent intent = new Intent(this, NewActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_EDIT_TASK_DETAIL_ENTITY, entity.cloneObj());
        intent.putExtra(Constants.INTENT_EXTRA_MODE_OF_NEW_ACT, Constants.MODE_OF_NEW_ACT.MODE_EDIT);
        startActivityForResult(intent, Constants.EDIT_ACTIVITY_REQUEST_CODE);
        mLastClickedItemPosition = position;
    }


    @Override
    public void showContextMenu(final int position, final TaskDetailEntity entity) {
        BottomSheetDialog bottomSheetDialog = new FixedBottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.dl_task_item_menu, mClMain, false);

        TextView tvFlagText = (TextView) view.findViewById(R.id.tv_flag_task);

        if (entity.getState() == TaskState.DEFAULT) {
            tvFlagText.setText("标记为已完成");
        } else {
            tvFlagText.setText("标记为未完成");
        }

        view.findViewById(R.id.ll_action_flag_task).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            int newState = TaskState.DEFAULT;
            if (entity.getState() == TaskState.DEFAULT) {
                newState = TaskState.FINISHED;
            } else {
                newState = TaskState.DEFAULT;
            }
            switchTaskState(position, entity, newState);
        });


        view.findViewById(R.id.ll_action_edit).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            toEditActivity(position, entity);
        });
        view.findViewById(R.id.ll_action_delete).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Subscription subscription = deleteTaskWithDelay(position, entity);

            SnackBarUtils.showAction(mClMain, "即将删除", "撤销", v1 -> subscription.unsubscribe());

        });

        view.findViewById(R.id.ll_action_put_off).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Subscription subscription = putOffTaskOneDayWithDelay(position, entity);

            SnackBarUtils.showAction(mClMain, "该任务即将推延一天", "撤销", v12 -> subscription.unsubscribe());
        });


        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setOwnerActivity(this);
        bottomSheetDialog.show();
    }

    private void switchTaskState(int position, TaskDetailEntity entity, int newState) {
        PageFragment fragment = (PageFragment) mItems.get(mVp.getCurrentItem()).getFragment();

        DataDao dao = DataDao.getInstance();
        dao.switchTaksState(entity, newState);

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

        PageFragment fragment = (PageFragment) mItems.get(mVp.getCurrentItem()).getFragment();
        TaskDetailEntity oldEntity = fragment.deleteTask(position);
        TaskDetailEntity newEntity = oldEntity.cloneObj();
        newEntity.setDayOfWeek(DateUtils.calNextDayDayOfWeek(oldEntity.getDayOfWeek()));
        ((PageFragment) mItems.get(newEntity.getDayOfWeek() - 1).getFragment()).insertTask(newEntity);
        DataDao dao = DataDao.getInstance();
        dao.insertTask(newEntity);
        dao.deleteTask(oldEntity);

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
        PageFragment fragment = (PageFragment) mItems.get(mVp.getCurrentItem()).getFragment();
        DataDao dao = DataDao.getInstance();
        fragment.deleteTask(position);
        dao.deleteTask(entity);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    protected void initInjector() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    public void back() {
        finish();
    }
}
