package com.brothers.appcommon.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.brothers.base.ui.base.BaseListActivity;

public abstract class CommonListActivity extends BaseListActivity {

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }
}
