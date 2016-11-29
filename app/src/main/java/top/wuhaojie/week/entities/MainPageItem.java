package top.wuhaojie.week.entities;

import android.support.v4.app.Fragment;

/**
 * Created by wuhaojie on 2016/11/29 20:39.
 */

public class MainPageItem {

    private String mTitle;
    private Fragment mFragment;

    public MainPageItem(String title, Fragment fragment) {
        mTitle = title;
        mFragment = fragment;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }
}
