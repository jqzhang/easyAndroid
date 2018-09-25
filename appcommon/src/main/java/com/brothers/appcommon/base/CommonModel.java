package com.brothers.appcommon.base;

import com.brothers.base.common.BaseModel;
import com.brothers.base.request.BaseRequest;
import com.brothers.base.request.IBaseCallback;
import com.brothers.appcommon.intrface.ICommonPresenter;

public abstract class CommonModel<T extends CommonResult, P extends ICommonPresenter> extends BaseModel<P> {

    protected abstract BaseRequest getRequest();

    public CommonModel(P presenter) {
        super(presenter);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        BaseRequest request = getRequest().setCallback(getCallback(getRequest().getUrl()));

        if (getMethod().equals(METHOD.POST)) {
            request.post();
        } else if (getMethod().equals(METHOD.POST_BODY)) {
            request.postBody();
        } else if (getMethod().equals(METHOD.GET)) {
            request.get();
        }
    }

    public IBaseCallback<T> getCallback(String url) {
        return new CommonCallback<T, P>(mPresenter, url);
    }
}
