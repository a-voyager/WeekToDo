package top.wuhaojie.week.views;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import top.wuhaojie.week.R;
import top.wuhaojie.week.base.BaseActivity;
import top.wuhaojie.week.dagger.ActivityModule;
import top.wuhaojie.week.dagger.DaggerActivityComponent;
import top.wuhaojie.week.presenter.AboutHolder;
import top.wuhaojie.week.presenter.AboutPresenter;

public class AboutActivity extends BaseActivity implements AboutHolder.View {

    @Inject
    AboutPresenter mPresenter;

    @BindView(R.id.open_network)
    Button mOpenNetwork;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter.bindView(this);
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void initializeInjector() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_about;
    }

    @OnClick(R.id.open_network)
    public void onClick() {
        mPresenter.openNetworkOnclick();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mPresenter.onOptionsItemSelected(item);
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void updateVersionView(String versionName) {
        mTvVersion.setText(versionName);
    }
}
