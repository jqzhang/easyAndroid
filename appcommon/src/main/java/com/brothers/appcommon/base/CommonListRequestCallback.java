package com.brothers.appcommon.base;

import com.brothers.base.Intrface.IListPresenter;
import com.brothers.base.common.BaseListRequestCallback;
import com.brothers.base.request.BaseResult;
import com.brothers.base.request.IBaseCallback;

/**
 * Created by zhangjiqun on 2018/1/26.
 */

public class CommonListRequestCallback<P extends IListPresenter, T extends CommonResult> extends BaseListRequestCallback<P, T> {

    private IListPresenter mPresenter;
    private CommonListModel mModel;

    public CommonListRequestCallback(P presenter, CommonListModel model) {
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
        mPresenter.setData(mModel.getRequest().getUrl(), data);
    }

    @Override
    public void onFinish(boolean isRefresh) {
        super.onFinish(isRefresh);
    }
}
