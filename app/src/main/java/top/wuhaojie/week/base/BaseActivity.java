package top.wuhaojie.week.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.wuhaojie.week.R;

/**
 * Created by wuhaojie on 17-2-7.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        initInjector();
    }

    protected void initInjector(){}


}
