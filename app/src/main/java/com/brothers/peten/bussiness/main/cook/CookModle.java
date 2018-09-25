package com.brothers.peten.bussiness.main.cook;

import com.brothers.base.request.BaseRequest;
import com.brothers.peten.base.AppBaseListModel;
import com.brothers.peten.common.Urls;

public class CookModle extends AppBaseListModel<CookContract.Presenter, CookResult> {

    public CookModle(CookPresenter presenter) {
        super(presenter);
    }

    @Override
    protected String getMethod() {
        return METHOD.GET;
    }

    @Override
    public BaseRequest getRequest() {

        return new BaseRequest(mPresenter.getContext(), CookResult.class)
                .setUrl(Urls.getCookCategoryUrl());
    }
}
