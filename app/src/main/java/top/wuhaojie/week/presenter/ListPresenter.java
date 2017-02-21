package top.wuhaojie.week.presenter;

import android.content.Context;

import javax.inject.Inject;

import top.wuhaojie.week.base.BaseView;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/21 21:00
 * Version: 1.0
 */

public class ListPresenter implements ListHolder.Presenter {

    private Context mContext;
    private ListHolder.View mView;

    @Inject
    public ListPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void bindView(BaseView view) {
        mView = (ListHolder.View) view;
    }
}
