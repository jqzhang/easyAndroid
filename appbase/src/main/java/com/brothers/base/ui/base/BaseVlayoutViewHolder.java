package com.brothers.base.ui.base;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.brothers.base.Intrface.IBaseVlayoutListView;
import com.brothers.base.Intrface.NoProguard;
import com.brothers.base.R;
import com.brothers.base.Intrface.IBaseVlayoutListView;
import com.brothers.base.Intrface.NoProguard;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

public class BaseVlayoutViewHolder implements NoProguard {

    private IBaseVlayoutListView mIBaseVlayoutListView;

    private TwinklingRefreshLayout refreshLayout;

    private RecyclerView recyclerView;

    private LinearLayout llTop;

    private LinearLayout llBottom;

    private RelativeLayout rlContentParent;

    private VirtualLayoutManager mLayoutManager;

    private RecyclerView.RecycledViewPool mViewPool;

    private DelegateAdapter mAdapter;

    private boolean mIsRefresh = true;

    public BaseVlayoutViewHolder(IBaseVlayoutListView intreface) {
        mIBaseVlayoutListView = intreface;
    }

    protected int getContentView() {
        return R.layout.base_list_view;
    }

    public void init() {
        refreshLayout = mIBaseVlayoutListView.getRootView().findViewById(R.id.refreshLayout);
        recyclerView = mIBaseVlayoutListView.getRootView().findViewById(R.id.recyclerView);
        llTop = mIBaseVlayoutListView.getRootView().findViewById(R.id.llTop);
        llBottom = mIBaseVlayoutListView.getRootView().findViewById(R.id.llBottom);
        rlContentParent = mIBaseVlayoutListView.getRootView().findViewById(R.id.rlContentParent);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (null == mIBaseVlayoutListView.getPresenter()) {
                    return;
                }
                mIsRefresh = true;
                mIBaseVlayoutListView.getPresenter().onRefresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (null == mIBaseVlayoutListView.getPresenter()) {
                    return;
                }
                mIsRefresh = false;
                mIBaseVlayoutListView.getPresenter().onLoadMore();
            }
        });
        if (BaseApplication.getInstance().getRefreshHeaderView() != null) {
            refreshLayout.setHeaderView(BaseApplication.getInstance().getRefreshHeaderView());
        }

        initRecyclerView();
    }

    protected void initRecyclerView() {
        if (null == recyclerView) {
            return;
        }
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(getAdapter());
        recyclerView.setRecycledViewPool(getRecycledViewPool());
    }

    protected void resetRecyclerView() {
        mAdapter = null;
        mLayoutManager = null;
        initRecyclerView();
    }

    public DelegateAdapter getAdapter() {
        if (null == mAdapter) {
            mAdapter = new DelegateAdapter(getLayoutManager(), false);
        }
        return mAdapter;
    }

    protected void addAdapter(DelegateAdapter.Adapter adapter) {
        getAdapter().addAdapter(adapter);
        getAdapter().notifyDataSetChanged();
    }

    protected void addAdapters(List<DelegateAdapter.Adapter> adapters) {
        getAdapter().addAdapters(adapters);
        getAdapter().notifyDataSetChanged();
    }

    public VirtualLayoutManager getLayoutManager() {
        if (null == mLayoutManager) {
            mLayoutManager = new VirtualLayoutManager(mIBaseVlayoutListView.getAppActivity());
            mLayoutManager.setRecycleOffset(1000);
        }
        return mLayoutManager;
    }

    //final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    protected RecyclerView.RecycledViewPool getRecycledViewPool() {
        if (null == mViewPool) {
            mViewPool = new RecyclerView.RecycledViewPool();
        }
        return mViewPool;
    }

    public void setContentBackground(Drawable backgroundDrawable) {
        rlContentParent.setBackground(backgroundDrawable);
    }

    public boolean isReFresh() {
        return mIsRefresh;
    }

    public void switchLayoutManager() {
        recyclerView.setLayoutManager(getLayoutManager());
    }

    public void updateAdapter() {
        recyclerView.setAdapter(getAdapter());
    }

    public void addTopView(View view) {
        llTop.addView(view);
    }

    public void addBottomView(View view) {
        llBottom.addView(view);
    }

    public void setEnableRefresh(boolean enable) {
        refreshLayout.setEnableRefresh(enable);
    }

    public void setEnableLoadMore(boolean enable) {
        refreshLayout.setEnableLoadmore(enable);
    }

    public void refreshFinish() {
        if (null == refreshLayout) {
            return;
        }
        refreshLayout.finishRefreshing();
    }

    public void loadMoreFinish() {
        if (null == refreshLayout) {
            return;
        }
        refreshLayout.finishLoadmore();
    }

    public void finishLoading() {
        if (refreshLayout != null) {
            refreshLayout.finishLoadmore();
            refreshLayout.finishRefreshing();
        }
    }

    public void clearData() {
        // 初始化 RecyclerView
        resetRecyclerView();
    }
}
