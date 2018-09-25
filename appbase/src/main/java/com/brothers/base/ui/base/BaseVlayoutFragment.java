package com.brothers.base.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.brothers.base.Intrface.IBaseVlayoutListView;
import com.brothers.base.common.BasePresenter;
import com.brothers.base.request.BaseData;

import java.util.List;

/**
 * Created by zhangjiqun on 2018/1/3.
 */

public abstract class BaseVlayoutFragment extends BaseFragment implements IBaseVlayoutListView {

    protected abstract void initData();

    protected abstract void initView();

    private BaseVlayoutViewHolder mBaseVlayoutListViewHolder;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getContentView() {
        return getBaseVlayoutListViewHolder().getContentView();
    }

    @Override
    protected void init() {
        getBaseVlayoutListViewHolder().init();
        initView();
        initData();
    }

    protected BaseVlayoutViewHolder getBaseVlayoutListViewHolder() {
        if (null == mBaseVlayoutListViewHolder) {
            mBaseVlayoutListViewHolder = new BaseVlayoutViewHolder(this);
        }

        return mBaseVlayoutListViewHolder;
    }

    protected void resetRecyclerView() {
        getBaseVlayoutListViewHolder().resetRecyclerView();
    }

    protected void addAdapter(DelegateAdapter.Adapter adapter) {
        getBaseVlayoutListViewHolder().addAdapter(adapter);
    }

    protected boolean isRefresh() {
        return getBaseVlayoutListViewHolder().isReFresh();
    }

    protected void addAdapters(List<DelegateAdapter.Adapter> adapters) {
        getBaseVlayoutListViewHolder().addAdapters(adapters);
    }

    //final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    protected RecyclerView.RecycledViewPool getRecycledViewPool() {
        return getBaseVlayoutListViewHolder().getRecycledViewPool();
    }

    protected void switchLayoutManager() {
        getBaseVlayoutListViewHolder().switchLayoutManager();
    }

    protected void updateAdapter() {
        getBaseVlayoutListViewHolder().updateAdapter();
    }

    protected void addTopView(View view) {
        getBaseVlayoutListViewHolder().addTopView(view);
    }

    protected void addBottomView(View view) {
        getBaseVlayoutListViewHolder().addBottomView(view);
    }

    protected void setEnableRefresh(boolean enable) {
        getBaseVlayoutListViewHolder().setEnableRefresh(enable);
    }

    protected void setEnableLoadMore(boolean enable) {
        getBaseVlayoutListViewHolder().setEnableLoadMore(enable);
    }

    @Override
    public void refreshFinish() {
        getBaseVlayoutListViewHolder().refreshFinish();
    }

    @Override
    public void loadMoreFinish() {
        getBaseVlayoutListViewHolder().loadMoreFinish();
    }

    @Override
    public void clearData() {
        // 初始化 RecyclerView
        getBaseVlayoutListViewHolder().clearData();
    }

    @Override
    public DelegateAdapter getAdapter() {
        return getBaseVlayoutListViewHolder().getAdapter();
    }

    @Override
    public VirtualLayoutManager getLayoutManager() {
        return getBaseVlayoutListViewHolder().getLayoutManager();
    }
}
