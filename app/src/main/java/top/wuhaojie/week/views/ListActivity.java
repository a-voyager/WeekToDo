package top.wuhaojie.week.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import top.wuhaojie.week.R;
import top.wuhaojie.week.base.BaseActivity;
import top.wuhaojie.week.dagger.ActivityModule;
import top.wuhaojie.week.dagger.DaggerActivityComponent;
import top.wuhaojie.week.presenter.ListHolder;
import top.wuhaojie.week.presenter.ListPresenter;

public class ListActivity extends BaseActivity implements ListHolder.View {


    @Inject
    ListPresenter mPresenter;
    @BindView(R.id.rv_list)
    RecyclerView mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.bindView(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_list;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Intent intent() {
        return getIntent();
    }

    @Override
    public void updateToolbarTitle(String s) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(s);
    }
}
