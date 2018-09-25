package com.brothers.base.common;

import com.brothers.base.Intrface.IBaseListView;
import com.brothers.base.Intrface.IListPresenter;
import com.brothers.base.Intrface.IListView;
import com.brothers.base.request.BaseResult;

/**
 * Created by zhangjiqun on 2018/1/3.
 */

public abstract class BaseListPresenter<V extends IListView> extends BasePresenter<V> implements IListPresenter {


    public BaseListPresenter(V view) {
        super(view);
    }

    public void loadFirstPage() {
        if (getModel() instanceof  BaseListModel) {
            ((BaseListModel) getModel()).loadFirstPage();
        } else {
            getModel().onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        mView.clearData();
        if (needShowLoadingPage()) {
            mView.showLoading();
        }
        loadFirstPage();
    }

    @Override
    public void onLoadMore() {
        getModel().onLoadMore();
    }

    @Override
    public void refreshFinish() {
        if (mView instanceof IListView) {
            mView.refreshFinish();
        }
    }

    @Override
    public void loadMoreFinish() {
        if (mView instanceof IListView) {
            mView.loadMoreFinish();
        }
    }

    @Override
    public boolean needShowLoadingPage() {
        return true;
    }
}
