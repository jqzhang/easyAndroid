package com.brothers.peten.bussiness.main;

import com.brothers.base.ui.tablayout.listener.CustomTabEntity;

/**
 * Created by zhangjiqun on 2018/1/3.
 */

public class TabEntity implements CustomTabEntity {

    private String mTitle;
    private int mUnselectedIcon;
    private int mSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        mTitle = title;
        mSelectedIcon = selectedIcon;
        mUnselectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return mTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return mSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return mUnselectedIcon;
    }
}
