package com.brothers.peten.bussiness.main.cook.detail;

import com.brothers.base.request.BaseRequest;
import com.brothers.peten.base.AppBaseListModel;
import com.brothers.peten.common.Urls;

import java.util.HashMap;
import java.util.Map;

public class DetailModle extends AppBaseListModel {

    private String mId;

    public DetailModle(DetailPresenter presenter) {
        super(presenter);
    }

    @Override
    public BaseRequest getRequest() {
        Map<String, String> map = new HashMap<>();
        map.put("id", mId);

        return new BaseRequest(mPresenter.getContext(), DetailResult.class)
                .setParams(map)
                .setUrl(Urls.getMenuQueryUrl());
    }

    public void setId(String id) {
        this.mId = id;
    }

    @Override
    protected String getMethod() {
        return METHOD.GET;
    }
}
