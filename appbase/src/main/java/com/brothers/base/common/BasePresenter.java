package com.brothers.base.common;

import android.content.Context;

import com.brothers.base.Intrface.IBasePresenter;
import com.brothers.base.Intrface.IBaseView;
import com.brothers.base.request.BaseResult;

/**
 * Created by zhangjiqun on 2018/1/3.
 */

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter {

    public abstract void onRefresh();

    public abstract void onLoadMore();

    protected V mView;

    public BasePresenter(V view) {
        mView = view;
    }

    @Override
    public void clearData() {
        mView.clearData();
    }

    @Override
    public void showLoading() {
        if (needShowLoadingPage()) {
            mView.showLoading();
        }
    }

    @Override
    public void refreshFinish() {
    }

    @Override
    public void loadMoreFinish() {

    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public boolean needShowLoadingPage() {
        return true;
    }
}
