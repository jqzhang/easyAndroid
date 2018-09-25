package com.brothers.peten.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.brothers.appcommon.base.CommonListFragment;

public abstract class AppBaseListFragment extends CommonListFragment {
    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }
}
