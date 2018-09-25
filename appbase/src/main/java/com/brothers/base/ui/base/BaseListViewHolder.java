package com.brothers.base.ui.base;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.brothers.base.Intrface.IBaseListView;
import com.brothers.base.Intrface.NoProguard;
import com.brothers.base.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

public class BaseListViewHolder implements NoProguard {

    private IBaseListView mIBaseListView;

    private TwinklingRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout llTop;
    private LinearLayout llBottom;
    private RelativeLayout rlContentParent;

    private boolean mIsRefresh = true;

    public BaseListViewHolder(IBaseListView intreface) {
        mIBaseListView = intreface;
    }

    public int getContentView() {
        return R.layout.base_list_view;
    }

    public void init() {
        rlContentParent = mIBaseListView.getRootView().findViewById(R.id.rlContentParent);
        refreshLayout = mIBaseListView.getRootView().findViewById(R.id.refreshLayout);
        recyclerView = mIBaseListView.getRootView().findViewById(R.id.recyclerView);
        llTop = mIBaseListView.getRootView().findViewById(R.id.llTop);
        llBottom = mIBaseListView.getRootView().findViewById(R.id.llBottom);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (null == mIBaseListView.getPresenter()) {
                    return;
                }
                mIsRefresh = true;
                mIBaseListView.getPresenter().onRefresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (null == mIBaseListView.getPresenter()) {
                    return;
                }
                mIsRefresh = false;
                mIBaseListView.getPresenter().onLoadMore();
            }
        });
        if (BaseApplication.getInstance().getRefreshHeaderView() != null) {
            refreshLayout.setHeaderView(BaseApplication.getInstance().getRefreshHeaderView());
        }

        recyclerView.setLayoutManager(mIBaseListView.getLayoutManager());
        recyclerView.setAdapter(mIBaseListView.getAdapter());
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void switchLayoutManager() {
        recyclerView.setLayoutManager(mIBaseListView.getLayoutManager());
        recyclerView.scrollToPosition(0);
    }

    public void setContentBackground(Drawable backgroundDrawable) {
        rlContentParent.setBackground(backgroundDrawable);
    }

    public boolean isReFresh() {
        return mIsRefresh;
    }

    public void updateAdapter() {
        recyclerView.setAdapter(mIBaseListView.getAdapter());
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
        refreshLayout.finishRefreshing();
    }

    public void loadMoreFinish() {
        refreshLayout.finishLoadmore();
    }

    public void clearData() {
        if (null == mIBaseListView) {
            return;
        }
        if (null == mIBaseListView.getAdapter()) {
            return;
        }
        mIBaseListView.getAdapter().clearData();
    }
}
