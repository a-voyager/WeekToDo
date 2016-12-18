package top.wuhaojie.week.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;

import top.wuhaojie.week.R;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    private int mCurrIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
}
