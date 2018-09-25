package com.brothers.base.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.brothers.base.Intrface.IBaseListView;
import com.brothers.base.request.BaseData;

/**
 * Created by zhangjiqun on 2018/1/3.
 */

public abstract class BaseListFragment extends BaseFragment implements IBaseListView {

    protected abstract void initData();

    protected abstract void initView();

    private BaseListViewHolder mBaseListViewHolder;

    @Override
    protected int getContentView() {
        return getBaseListViewHolder().getContentView();
    }

    protected BaseListViewHolder getBaseListViewHolder() {
        if (null == mBaseListViewHolder) {
            mBaseListViewHolder = new BaseListViewHolder(this);
        }
        return mBaseListViewHolder;
    }

    @Override
    protected void init() {
        getBaseListViewHolder().init();
        initView();
        initData();
    }

    protected boolean isRefresh() {
        return getBaseListViewHolder().isReFresh();
    }

    protected RecyclerView getRecyclerView() {
        return getBaseListViewHolder().getRecyclerView();
    }

    protected void switchLayoutManager() {
        getBaseListViewHolder().switchLayoutManager();
    }

    protected void updateAdapter() {
        getBaseListViewHolder().updateAdapter();
    }

    protected void addTopView(View view) {
        getBaseListViewHolder().addTopView(view);
    }

    protected void addBottomView(View view) {
        getBaseListViewHolder().addBottomView(view);
    }

    protected void setEnableRefresh(boolean enable) {
        getBaseListViewHolder().setEnableRefresh(enable);
    }

    protected void setEnableLoadMore(boolean enable) {
        getBaseListViewHolder().setEnableLoadMore(enable);
    }

    @Override
    public void refreshFinish() {
        getBaseListViewHolder().refreshFinish();
    }

    @Override
    public void loadMoreFinish() {
        getBaseListViewHolder().loadMoreFinish();
    }

    @Override
    public void clearData() {
        getBaseListViewHolder().clearData();
    }
}
