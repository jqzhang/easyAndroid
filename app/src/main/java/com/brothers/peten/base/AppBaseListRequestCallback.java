package com.brothers.peten.base;

import com.brothers.base.Intrface.IListPresenter;
import com.brothers.base.common.BaseListRequestCallback;
import com.brothers.peten.common.router.Router;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjiqun on 2018/1/26.
 */

public class AppBaseListRequestCallback<P extends IListPresenter, T extends AppResult> extends BaseListRequestCallback<P, T> {

    private AppBaseListModel mModel;

    public AppBaseListRequestCallback(P presenter, AppBaseListModel model) {
        super(presenter);
        mModel = model;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(T data) {
        if (data.isSucceed()) {
            mModel.mergeData(data);
        } else {
            onError(data);
        }
    }

    @Override
    public void onError(T data) {
    }

    @Override
    public void onFinish(boolean isRefresh) {
        super.onFinish(isRefresh);
    }
}
