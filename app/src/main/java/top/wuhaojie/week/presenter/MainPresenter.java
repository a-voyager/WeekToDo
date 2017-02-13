package top.wuhaojie.week.presenter;

import android.content.Context;

import javax.inject.Inject;

import top.wuhaojie.week.base.BaseView;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/13 12:02
 * Version: 1.0
 */

public class MainPresenter implements MainHolder.Presenter {

    private MainHolder.View mView;
    private Context mContext;


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
        mView.back();
    }
}
