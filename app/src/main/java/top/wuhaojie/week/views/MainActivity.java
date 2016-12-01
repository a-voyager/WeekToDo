package top.wuhaojie.week.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

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
import top.wuhaojie.week.fragments.PageFragment;

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
    //    public volatile int mVpCurrIndex;

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


        // 坑: mVp.getCurrentItem() 不能获得第一页和最后一页的Index

        DataDao dataDao = DataDao.getInstance();
        RealmResults<TaskDetailEntity> allTask = dataDao.findAllTask();
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

        Intent intent = new Intent(this, NewActivity.class);
        startActivityForResult(intent, Constants.NEW_ACTIVITY_REQUEST_CODE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
