package com.brothers.peten.bussiness.main.cook.detail;

import com.brothers.base.request.BaseResult;

public class DetailPresenter extends DetailContract.Presenter {

    private DetailModle mModle;

    public DetailPresenter(DetailContract.View view) {
        super(view);
    }


    @Override
    public void setData(String url, BaseResult data) {
        if (false == data.isSucceed()) {
            mView.showError(data.getMessage(), "");
            return;
        }
        mView.setData(((DetailResult) data).result);
    }

    public void setId(String id) {
        getModel().setId(id);
    }

    @Override
    public DetailModle getModel() {
        if (null == mModle) {
            mModle = new DetailModle(this);
        }

        return mModle;
    }
}
