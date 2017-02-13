package top.wuhaojie.week.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import top.wuhaojie.week.R;
import top.wuhaojie.week.base.BaseView;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.entities.MainPageItem;
import top.wuhaojie.week.views.NewActivity;
import top.wuhaojie.week.views.SettingsActivity;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/13 12:02
 * Version: 1.0
 */

public class MainPresenter implements MainHolder.Presenter {

    private static final String TAG = "MainPresenter";
    private MainHolder.View mView;
    private Context mContext;

    @Inject
    List<MainPageItem> mItems;

    @Inject
    public MainPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void bindView(BaseView view) {
        mView = (MainHolder.View) view;
    }


    @Override
    public void onBackPressed() {
        mView.finishActivity();
    }

    @Override
    public void onFabClick() {
        int i = mView.getCurrentViewPagerItem();
        Intent intent = new Intent(mContext, NewActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_DAY_OF_WEEK, i + 1);
        intent.putExtra(Constants.INTENT_EXTRA_MODE_OF_NEW_ACT, Constants.MODE_OF_NEW_ACT.MODE_CREATE);
        mView.startActivityAndForResult(intent, Constants.NEW_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(mContext, SettingsActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_SWITCH_TO_INDEX, mView.getCurrentViewPagerItem());
                mContext.startActivity(intent);
                mView.finishActivity();
                break;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: " + mItems.toString());
    }

}
