package com.brothers.base.Intrface;

import android.content.Context;

import com.brothers.base.common.BaseModel;
import com.brothers.base.request.BaseResult;
import com.brothers.base.common.BaseModel;
import com.brothers.base.request.BaseResult;

public interface IBasePresenter<D extends BaseResult> {
    void setData(String url, D data);

    void clearData();

    void showLoading();

    void refreshFinish();

    void loadMoreFinish();

    Context getContext();

    BaseModel getModel();

    boolean needShowLoadingPage();
}
