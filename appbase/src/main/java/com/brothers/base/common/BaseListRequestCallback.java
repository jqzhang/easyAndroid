package com.brothers.base.common;

import com.brothers.base.Intrface.IBaseListView;
import com.brothers.base.Intrface.IListPresenter;
import com.brothers.base.request.BaseResult;
import com.brothers.base.request.IBaseCallback;
import com.brothers.base.Intrface.IListPresenter;

/**
 * Created by zhangjiqun on 2018/1/26.
 */

public abstract class BaseListRequestCallback<P extends IListPresenter, T extends BaseResult> implements IBaseCallback<T> {

    protected IListPresenter mPresenter;

    public BaseListRequestCallback(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(T data) {
        if (!data.isSucceed()) {
            onError(data);
        }
    }

    @Override
    public void onError(T data) {
    }

    @Override
    public void onFinish(boolean isRefresh) {
        if (isRefresh) {
            mPresenter.refreshFinish();
        } else {
            mPresenter.loadMoreFinish();
        }
    }
}
