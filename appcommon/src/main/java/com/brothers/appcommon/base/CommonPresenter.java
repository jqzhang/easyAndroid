package com.brothers.appcommon.base;

import com.brothers.appcommon.intrface.ICommonPresenter;
import com.brothers.base.Intrface.IBaseView;
import com.brothers.base.common.BasePresenter;

public abstract class CommonPresenter<V extends IBaseView> extends BasePresenter<V> implements ICommonPresenter {

    public CommonPresenter(V view) {
        super(view);
    }

    @Override
    public void onLoadMore() {
        getModel().onLoadMore();
    }

    @Override
    public void onRefresh() {
        if (needShowLoadingPage()) {
            mView.showLoading();
        }
        getModel().onRefresh();
    }

    @Override
    public boolean needShowLoadingPage() {
        return true;
    }
}
