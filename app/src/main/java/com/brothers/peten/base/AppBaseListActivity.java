package com.brothers.peten.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.brothers.appcommon.base.CommonListActivity;

public abstract class AppBaseListActivity extends CommonListActivity {

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }
}
