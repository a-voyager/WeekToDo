package top.wuhaojie.week.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.wuhaojie.week.R;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.data.ImageFactory;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.entities.TaskState;
import top.wuhaojie.week.utils.SnackBarUtils;

public class NewActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.cl)
    CoordinatorLayout mCl;
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.sdv_bg)
    SimpleDraweeView mSdvBg;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    private int mCurrIndex;
    private List<String> mBgImgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEtContent.requestFocus();
        mBgImgs = ImageFactory.createBgImgs();
        mCurrIndex = new Random(System.currentTimeMillis()).nextInt(mBgImgs.size());
        loadBgImgWithIndex(mCurrIndex);
        String date = new SimpleDateFormat("yyyy/mm/dd").format(new Date());
        mTvDate.setText(date);
    }

    public void loadBgImgWithIndex(int i) {
        String s = mBgImgs.get(i);
        mSdvBg.setImageURI(s);
        mCurrIndex = i;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_choose_icon:
                showIconChooseDialog();
                break;
        }
        return true;
    }

    private void showIconChooseDialog() {

        ChoosePaperColorDialog.newInstance(mCurrIndex).show(getSupportFragmentManager(), "IconChooseDialog");


    }

    @OnClick(R.id.fab)
    public void onClick() {
        String title = mEtTitle.getText().toString().trim();
        String content = mEtContent.getText().toString().trim();

        if (TextUtils.isEmpty(content)) {
            SnackBarUtils.show(mCl, "请填写内容后保存哦~");
            return;
        }
        if (TextUtils.isEmpty(title)) {
            title = content.substring(0, Math.min(5, content.length()));
        }

        int dayOfWeek = getIntent().getIntExtra(Constants.INTENT_EXTRA_DAY_OF_WEEK, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        TaskDetailEntity taskDetailEntity = new TaskDetailEntity(dayOfWeek);
        taskDetailEntity.setTitle(title);
        taskDetailEntity.setContent(content);
        taskDetailEntity.setState(TaskState.DEFAULT);
        taskDetailEntity.setTimeStamp(System.currentTimeMillis());
        taskDetailEntity.setIcon(mBgImgs.get(mCurrIndex));

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.INTENT_BUNDLE_NEW_TASK_DETAIL, taskDetailEntity);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }


}
