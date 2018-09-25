package com.brothers.base.common;

import android.content.Context;

import com.brothers.base.Intrface.IBasePresenter;

public abstract class BaseModel<P extends IBasePresenter> {

    public final static class METHOD {
        public final static String POST = "POST";
        public final static String GET = "GET";
        public final static String POST_BODY = "POST_BODY";
    }

    protected P mPresenter;

    private boolean hasMore = true;

    public BaseModel(P presenter) {
        mPresenter = presenter;
    }

    public void onRefresh() {
        mPresenter.showLoading();
        mPresenter.clearData();
    }


    protected void setHasMore(boolean has) {
        hasMore = has;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void onLoadMore() {
    }

    protected String getMethod() {
        return METHOD.POST;
    }

    protected Context getContext() {
        return mPresenter.getContext();
    }
}
