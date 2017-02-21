package top.wuhaojie.week.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

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
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_list;
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);
    }
}
