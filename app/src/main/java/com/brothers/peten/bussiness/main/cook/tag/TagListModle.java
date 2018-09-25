package com.brothers.peten.bussiness.main.cook.tag;

import com.brothers.base.request.BaseRequest;
import com.brothers.peten.base.AppBaseListModel;
import com.brothers.peten.common.Urls;

import java.util.HashMap;
import java.util.Map;

public class TagListModle extends AppBaseListModel {

    private String mCid;

    public TagListModle(TagListPresenter presenter) {
        super(presenter);
    }

    public void setCid(String id) {
        this.mCid = id;
    }

    @Override
    protected String getMethod() {
        return METHOD.GET;
    }

    @Override
    public BaseRequest getRequest() {
        Map<String, String> map = new HashMap<>();
        map.put("cid", mCid);

        return new BaseRequest(mPresenter.getContext(), TagListResult.class)
                .setParams(map)
                .setUrl(Urls.getCookTagUrl());
    }
}
