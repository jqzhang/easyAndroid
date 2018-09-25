package com.brothers.appcommon.base;

import com.brothers.base.Intrface.IBasePresenter;
import com.brothers.base.request.IBaseCallback;

public class CommonCallback<T extends CommonResult, P extends IBasePresenter> implements IBaseCallback<T> {

    private P mPresenter;

    private String mUrl;

    public CommonCallback(P presenter, String url) {
        mPresenter = presenter;
        mUrl = url;
    }

    @Override
    public void onSuccess(T data) {
        mPresenter.setData(mUrl, data);
    }

    @Override
    public void onError(T data) {
        mPresenter.setData(mUrl, data);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish(boolean isRefresh) {

    }
}
