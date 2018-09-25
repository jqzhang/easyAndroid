package com.brothers.peten.bussiness.main.cook;

import com.brothers.base.common.BaseModel;
import com.brothers.base.request.BaseResult;

public class CookPresenter extends CookContract.Presenter {

    public CookPresenter(CookContract.View view) {
        super(view);
    }


    @Override
    public void setData(String url, BaseResult data) {
        if (false == data.isSucceed()) {
            mView.showError(data.getMessage(), "");
            return;
        }
        mView.setData(((CookResult) data).result);
    }

    @Override
    public BaseModel getModel() {
        return new CookModle(this);
    }
}
