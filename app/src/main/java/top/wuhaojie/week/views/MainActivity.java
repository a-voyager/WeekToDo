package top.wuhaojie.week.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;
import top.wuhaojie.week.R;
import top.wuhaojie.week.adpter.MainPageAdapter;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.data.DataDao;
import top.wuhaojie.week.data.PageFactory;
import top.wuhaojie.week.entities.MainPageItem;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.entities.TaskState;
import top.wuhaojie.week.fragments.PageFragment;
import top.wuhaojie.week.utils.PreferenceUtils;
import top.wuhaojie.week.utils.SnackBarUtils;

public class MainActivity extends AppCompatActivity implements PageFragment.OnPageFragmentInteractionListener {

    public static final String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mItems = PageFactory.createPages();

        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), mItems);

        mTab.setupWithViewPager(mVp);
        mVp.setAdapter(adapter);

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        mVp.setCurrentItem(dayOfWeek - 1, true);


        // 坑: mVp.getCurrentItem() 某些时候不能获得第一页和最后一页的Index

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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
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
            deleteTaskWithDelay(position, entity);

            SnackBarUtils.showAction(mClMain, "即将删除", "撤销", v1 -> mHandler.removeMessages(Constants.HANDLER_WHAT_DELETE_TASK));

        });
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    private void switchTaskState(int position, TaskDetailEntity entity, int newState) {
        PageFragment fragment = (PageFragment) mItems.get(mVp.getCurrentItem()).getFragment();

        DataDao dao = DataDao.getInstance();
        dao.switchTaksState(entity, newState);

        fragment.getAdapter().notifyItemChanged(position);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            deleteTask(msg.arg1, (TaskDetailEntity) msg.obj);
        }
    };

    private void deleteTaskWithDelay(int position, TaskDetailEntity entity) {
        Message message = new Message();
        message.what = Constants.HANDLER_WHAT_DELETE_TASK;
        message.obj = entity;
        message.arg1 = position;
        mHandler.sendMessageDelayed(message, 2000);
    }

    private void deleteTask(int position, TaskDetailEntity entity) {
        PageFragment fragment = (PageFragment) mItems.get(mVp.getCurrentItem()).getFragment();
        DataDao dao = DataDao.getInstance();
        fragment.deleteTask(position);
        dao.deleteTask(entity);
    }
}
