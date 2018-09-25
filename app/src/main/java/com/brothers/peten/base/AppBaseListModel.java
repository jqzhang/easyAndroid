package com.brothers.peten.base;

import com.brothers.appcommon.base.CommonListModel;
import com.brothers.appcommon.intrface.ICommonListPresenter;
import com.brothers.base.request.IBaseCallback;

import java.util.HashMap;
import java.util.Map;

public abstract class AppBaseListModel<P extends ICommonListPresenter, D extends AppResult> extends CommonListModel<P, D> {

    public AppBaseListModel(P presenter) {
        super(presenter);
    }

    @Override
    protected String getMaxKey() {
        return "pageNo";
    }

    @Override
    public Map<String, String> getExtraParams() {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10");

        return params;
    }

    public IBaseCallback<D> getCallback() {
        return new AppBaseListRequestCallback<>(mPresenter, this);
    }

    @Override
    protected String getMethod() {
        return METHOD.POST_BODY;
    }
}
