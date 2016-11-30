package top.wuhaojie.week.views;

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
import top.wuhaojie.week.R;
import top.wuhaojie.week.adpter.MainPageAdapter;
import top.wuhaojie.week.data.DataDao;
import top.wuhaojie.week.data.PageFactory;
import top.wuhaojie.week.entities.MainPageItem;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.fragments.PageFragment;

public class MainActivity extends AppCompatActivity implements PageFragment.OnPageFragmentInteractionListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        List<MainPageItem> items = PageFactory.createPages();

        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), items);

        mTab.setupWithViewPager(mVp);
        mVp.setAdapter(adapter);

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        mVp.setCurrentItem(dayOfWeek - 1, true);


    }

    @OnClick(R.id.fab)
    public void onClick() {
//        SnackBarUtils.show(mClMain, "Hello");
//
        DataDao dao = DataDao.getInstance();
        dao.insertTask(new TaskDetailEntity());
//
//
//        RealmResults<TaskDetailEntity> results = dao.findAllTask(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
//
//        Toast.makeText(MainActivity.this, "完成: " + results, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public int getCurrIndex() {
        return mVp.getCurrentItem();
    }
}
