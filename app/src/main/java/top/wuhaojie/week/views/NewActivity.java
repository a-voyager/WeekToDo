package top.wuhaojie.week.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import top.wuhaojie.week.adpter.ChoosePriorityAdapter;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.data.ImageFactory;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.entities.TaskState;
import top.wuhaojie.week.utils.DensityUtil;
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
    private final List<String> mBgImgs = ImageFactory.createBgImgs();
    @BindView(R.id.ll_priority)
    LinearLayout mLlPriority;
    @BindView(R.id.ll_priority_list)
    LinearLayout mLlPriorityList;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.content_new)
    LinearLayout contentNew;
    @BindView(R.id.rv_choose_priority)
    RecyclerView mRvChoosePriority;
    @BindView(R.id.iv_curr_priority)
    ImageView mIvCurrPriority;
    private String mCurrBgUri;
    private int mCurrPriority;
    private ChoosePriorityAdapter mChoosePriorityAdapter;
    private TaskDetailEntity mEntityFromMain;

    @OnClick(R.id.ll_priority)
    public void onClick() {

//        SnackBarUtils.show(mCl, "选择优先级");

        if (mLlPriorityList.isShown())
            hidePriorityList();
        else
            showPriorityList();

    }

    private void showPriorityList() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mLlContent, "translationY", DensityUtil.dip2px(this, 36F));
        animator.setInterpolator(new FastOutSlowInInterpolator());

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mLlPriorityList.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(
                animator,
                ObjectAnimator.ofFloat(mLlPriorityList, "alpha", 0f, 1, 1)
        );
        set.start();
    }

    private void hidePriorityList() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mLlContent, "translationY", 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mLlPriorityList, "alpha", 1, 1, 0f);
        alpha.setInterpolator(new FastOutSlowInInterpolator());

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mLlPriorityList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(
                alpha,
                animator
        );
        set.start();
    }

    private interface IState {
        void initView(Intent intent, Bundle savedInstanceState);
    }

    private class CreateNew implements IState {

        @Override
        public void initView(Intent intent, Bundle savedInstanceState) {
            mEtContent.requestFocus();
            int i = new Random(System.currentTimeMillis()).nextInt(mBgImgs.size());
            loadBgImgWithIndex(i);
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            mTvDate.setText(date);
            mChoosePriorityAdapter.setCheckItem(0);
        }
    }

    private class EditOld implements IState {

        @Override
        public void initView(Intent intent, Bundle savedInstanceState) {

            mEtTitle.setFocusable(false);
            mEtTitle.setOnTouchListener((v, event) -> {
                mEtTitle.setFocusableInTouchMode(true);
                return false;
            });

            mEtContent.setFocusable(false);
            mEtContent.setOnTouchListener((v, event) -> {
                mEtContent.setFocusableInTouchMode(true);
                return false;
            });

            mEntityFromMain = (TaskDetailEntity) intent.getSerializableExtra(Constants.INTENT_EXTRA_EDIT_TASK_DETAIL_ENTITY);
            intent.putExtra(Constants.INTENT_EXTRA_DAY_OF_WEEK, mEntityFromMain.getDayOfWeek());
            mEtTitle.setText(mEntityFromMain.getTitle());
            mEtContent.setText(mEntityFromMain.getContent());
            loadBgImgWithUri(mEntityFromMain.getIcon());
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date(mEntityFromMain.getTimeStamp()));
            mTvDate.setText(date);
            mIvCurrPriority.setImageResource(ImageFactory.createPriorityIcons()[mEntityFromMain.getPriority()]);
            mChoosePriorityAdapter.setCheckItem(mEntityFromMain.getPriority());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Priority Choose
        mRvChoosePriority.setLayoutManager(new GridLayoutManager(this, 4));
        mChoosePriorityAdapter = new ChoosePriorityAdapter(this);
        mChoosePriorityAdapter.setOnItemClickListener((v, position) -> {
            mCurrPriority = position;
            mIvCurrPriority.setImageResource(ImageFactory.createPriorityIcons()[position]);
            mChoosePriorityAdapter.setCheckItem(position);
            hidePriorityList();
        });
        mRvChoosePriority.setAdapter(mChoosePriorityAdapter);


        Intent intent = getIntent();
        int mode = intent.getIntExtra(Constants.INTENT_EXTRA_MODE_OF_NEW_ACT, Constants.MODE_OF_NEW_ACT.MODE_CREATE);
        IState state;
        if (mode == Constants.MODE_OF_NEW_ACT.MODE_EDIT)
            state = new EditOld();
        else
            state = new CreateNew();
        state.initView(intent, savedInstanceState);


        View decorView = this.getWindow().getDecorView();
//            View decorView = mToolbar;
        decorView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int x = (int) (mFab.getWidth() / 2 + mFab.getX());
                    int y = (int) (mFab.getHeight() / 2 + mFab.getY());
                    Animator animator = ViewAnimationUtils.createCircularReveal(decorView, x, y, 0, decorView.getHeight());
                    animator.setDuration(400);
                    animator.start();
                }
            }
        });


    }


    public void loadBgImgWithIndex(int i) {
        loadBgImgWithUri(mBgImgs.get(i));
    }

    public void loadBgImgWithUri(String uri) {
        mCurrBgUri = uri;
        mSdvBg.setImageURI(uri);
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

        ChoosePaperColorDialog.newInstance(mCurrBgUri).show(getSupportFragmentManager(), "IconChooseDialog");


    }

    @OnClick(R.id.fab)
    public void onClick(View v) {
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
        taskDetailEntity.setIcon(mCurrBgUri);
        taskDetailEntity.setPriority(mCurrPriority);

        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        if (mEntityFromMain == null) {
            // insert a new task
            bundle.putSerializable(Constants.INTENT_BUNDLE_NEW_TASK_DETAIL, taskDetailEntity);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        } else if (!mEntityFromMain.equals(taskDetailEntity)) {
            // edited & content is changed
            bundle.putSerializable(Constants.INTENT_BUNDLE_NEW_TASK_DETAIL, taskDetailEntity);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, intent);
        }

        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }


}
