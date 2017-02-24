package top.wuhaojie.week.presenter;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import top.wuhaojie.week.base.BaseView;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.data.DataDao;
import top.wuhaojie.week.data.InstrumentHelper;
import top.wuhaojie.week.entities.TaskDetailEntity;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/21 21:00
 * Version: 1.0
 */

public class ListPresenter implements ListHolder.Presenter {

    private static final String TAG = "ListPresenter";
    private Context mContext;
    private ListHolder.View mView;

    @Inject
    DataDao mDataDao;

    @Inject
    public ListPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void bindView(BaseView view) {
        mView = (ListHolder.View) view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        handleIntent(mView.intent());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onItemClick(int position, TaskDetailEntity entity) {
        Intent intent = InstrumentHelper.toEditActivity(mContext, position, entity);
        mView.finishActivity();
        mView.startActivityAndForResult(intent, Constants.EDIT_ACTIVITY_REQUEST_CODE);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (TextUtils.isEmpty(query)) return;
            doSearch(query);
        }
    }

    private void doSearch(String query) {
        List<TaskDetailEntity> list = mDataDao.search(query);
        int size = list.size();
        mView.hideNoResults();
        if (size == 0) {
            mView.updateToolbarTitle("查找完成");
            mView.showNoResults();
            return;
        }
        mView.updateToolbarTitle("找到 " + size + " 条记录");
        mView.updateList(list);
        Log.d(TAG, "doSearch: " + list);
    }
}
