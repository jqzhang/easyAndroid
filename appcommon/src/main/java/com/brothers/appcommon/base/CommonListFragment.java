package com.brothers.appcommon.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.brothers.base.ui.base.BaseListFragment;

public abstract class CommonListFragment extends BaseListFragment {
    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }
}
