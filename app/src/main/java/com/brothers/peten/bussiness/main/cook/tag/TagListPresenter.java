package com.brothers.peten.bussiness.main.cook.tag;

import com.brothers.base.request.BaseResult;

public class TagListPresenter extends TagListContract.Presenter {

    private TagListModle mModle;

    public TagListPresenter(TagListContract.View view) {
        super(view);
    }


    @Override
    public void setData(String url, BaseResult data) {
        if (false == data.isSucceed()) {
            mView.showError(data.getMessage(), "");
            return;
        }
        mView.setData(((TagListResult) data).result);
    }

    public void setCid(String id) {
        getModel().setCid(id);
    }

    @Override
    public TagListModle getModel() {
        if (null == mModle) {
            mModle = new TagListModle(this);
        }

        return mModle;
    }
}
