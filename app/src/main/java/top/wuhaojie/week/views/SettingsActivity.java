package top.wuhaojie.week.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;

import top.wuhaojie.week.base.BaseActivity;
import top.wuhaojie.week.R;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.data.BackupManager;
import top.wuhaojie.week.fragments.SettingsFragment;
import top.wuhaojie.week.utils.SnackBarUtils;

public class SettingsActivity extends BaseActivity {

    private int mCurrIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_settings, settingsFragment)
                .commit();
        Intent intent = getIntent();
        mCurrIndex = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (intent != null) {
            mCurrIndex = intent.getIntExtra(Constants.INTENT_EXTRA_SWITCH_TO_INDEX, mCurrIndex);
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("请稍后");

    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_settings;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            toMainActivity();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_SWITCH_TO_INDEX, mCurrIndex);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        toMainActivity();
        finish();
//        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void switchNightMode(boolean toNight, boolean auto) {
        if (auto) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
            return;
        }
        if (toNight)
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }


    public void switchNightMode(boolean toNight) {
        switchNightMode(toNight, false);
    }


    private void animateSwitchNightMode(boolean toNight, boolean auto) {


        View decorView = getWindow().getDecorView();
        ObjectAnimator animator = ObjectAnimator.ofFloat(decorView, "alpha", 1, 0);
        animator.setDuration(600);
        animator.addListener(new Animator.AnimatorListener() {


            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animation.removeAllListeners();
                if (auto) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                    return;
                }
                if (toNight)
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }


    private int option = 0;

    public void backupClick() {
        option = 0;
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setSingleChoiceItems(new String[]{"备份数据", "导出文本"}, 0, (d, which) -> {
                    option = which;
                })
                .setTitle("请选择操作")
                .setPositiveButton("确认", (d, which) -> {
                    switch (option) {
                        case 0:
                            backupDataBase();
                            break;
                        case 1:
                            exportToFile();
                            break;
                    }
                })
                .create();
        dialog.show();


    }

    private void exportToFile() {
        mProgressDialog.show();
        BackupManager.export()
                .subscribe(b -> {
                    mProgressDialog.dismiss();
                    SnackBarUtils.show(SettingsActivity.this, b ? "导出完成" : "导出失败");
                });
    }

    private void recoveryDataBase() {
        mProgressDialog.show();
        BackupManager.recovery()
                .subscribe(b -> {
                    mProgressDialog.dismiss();
                    SnackBarUtils.show(SettingsActivity.this, b ? "还原成功" : "还原失败");
                    if (b)
                        System.exit(0);
                });
    }

    private void backupDataBase() {
        mProgressDialog.show();
        BackupManager
                .backup()
                .subscribe(b -> {
                    mProgressDialog.dismiss();
                    SnackBarUtils.show(SettingsActivity.this, b ? "备份完成" : "备份失败");
                });
    }

    public void recoveryClick() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("警告")
                .setMessage("还原内容将覆盖当前所有内容, 您确定要继续进行此操作? 还原成功后, 应用将自动关闭")
                .setPositiveButton("继续", (d, which) -> recoveryDataBase())
                .setNegativeButton("取消", (d, which) -> d.dismiss())
                .create();
        dialog.show();
    }


    private ProgressDialog mProgressDialog;


}
