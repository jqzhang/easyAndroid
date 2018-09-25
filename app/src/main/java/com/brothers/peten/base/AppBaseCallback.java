package com.brothers.peten.base;

import com.brothers.appcommon.intrface.ICommonCallback;
import com.brothers.appcommon.intrface.ICommonPresenter;
import com.brothers.peten.common.router.Router;

import java.util.HashMap;
import java.util.Map;

public class AppBaseCallback<T extends AppResult, P extends ICommonPresenter> implements ICommonCallback<T> {

    private P mPresenter;

    private String mUrl;

    public AppBaseCallback(P presenter, String url) {
        mPresenter = presenter;
        mUrl = url;
    }

    @Override
    public void onSuccess(T data) {
        mPresenter.setData(mUrl, data);
    }

    @Override
    public void onError(T data) {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish(boolean isRefresh) {

    }
}
