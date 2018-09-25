package com.brothers.peten.base;

import com.brothers.appcommon.base.CommonModel;
import com.brothers.appcommon.intrface.ICommonPresenter;
import com.brothers.base.request.BaseRequest;
import com.brothers.base.request.IBaseCallback;
import com.brothers.peten.common.Urls;
import com.brothers.peten.http.UploadResult;

import java.io.File;

public abstract class AppBaseModel<T extends AppResult, P extends ICommonPresenter> extends CommonModel<T, P> {

    public AppBaseModel(P presenter) {
        super(presenter);
    }

    protected abstract BaseRequest getRequest();

    @Override
    protected String getMethod() {
        return METHOD.POST_BODY;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (null == getRequest()) {
            return;
        }
        getRequest().setCallback(getCallback(getRequest().getUrl())).postBody();
    }

    public IBaseCallback<T> getCallback(String url) {
        return new AppBaseCallback<T, P>(mPresenter, url);
    }

    public void uploadFile(File file) {
        new BaseRequest(getContext(), UploadResult.class)
                .setUrl(Urls.getUploadFileUrl())
                .setCallback(getCallback(Urls.getUploadFileUrl()))
                .showLoading(true)
                .uploadFile(file);
    }
}
