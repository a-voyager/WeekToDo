package top.wuhaojie.week.views;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.wuhaojie.week.R;
import top.wuhaojie.week.adpter.MainPageAdapter;
import top.wuhaojie.week.entities.MainPageItem;
import top.wuhaojie.week.fragments.PageFragment;
import top.wuhaojie.week.utils.SnackBarUtils;

public class MainActivity extends AppCompatActivity {

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

        List<MainPageItem> items = new ArrayList<>();

        items.add(new MainPageItem("周日", new PageFragment()));
        items.add(new MainPageItem("周一", new PageFragment()));
        items.add(new MainPageItem("周二", new PageFragment()));
        items.add(new MainPageItem("周三", new PageFragment()));
        items.add(new MainPageItem("周四", new PageFragment()));
        items.add(new MainPageItem("周五", new PageFragment()));
        items.add(new MainPageItem("周六", new PageFragment()));

        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), items);

        mTab.setupWithViewPager(mVp);
        mVp.setAdapter(adapter);


    }

    @OnClick(R.id.fab)
    public void onClick() {
        SnackBarUtils.show(mClMain, "Hello");
    }
}
