package com.brothers.base.common;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.brothers.base.ui.base.BaseFragment;

import java.util.List;

/**
 * Created by zhangjiqun on 2018/1/4.
 */

public abstract class BaseFragmentAdapter<T extends BaseFragment> extends FragmentPagerAdapter {

    protected abstract List<T> getFragmentList();

    public BaseFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public T getItem(int position) {
        if (null == getFragmentList() || position >= getFragmentList().size()) {
            return null;
        }
        return getFragmentList().get(position);
    }

    @Override
    public int getCount() {
        return ((null == getFragmentList()) ? 0 : getFragmentList().size());
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void setFragments(List<T> list) {
        if (null == getFragmentList()) {
            return;
        }
        getFragmentList().addAll(list);
        notifyDataSetChanged();
    }

    public void clearFragments() {
        if (null == getFragmentList()) {
            return;
        }
        getFragmentList().clear();
        notifyDataSetChanged();
    }
}
