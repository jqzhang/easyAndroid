package com.brothers.base.common;

import com.brothers.base.Intrface.IListPresenter;
import com.brothers.base.request.BaseRequest;
import com.brothers.base.request.IBaseCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjiqun on 2018/1/3.
 */

public abstract class BaseListModel<P extends IListPresenter> extends BaseModel<P> {

    protected abstract String getFirstId();

    protected abstract String getMaxId();

    protected abstract String getMaxKey();

    public abstract BaseRequest getRequest();

    protected abstract IBaseCallback getCallback();

    public BaseListModel(P presenter) {
        super(presenter);
    }

    public void loadFirstPage() {
        Map<String, String> params = new HashMap<>();
        params.put(getMaxKey(), getFirstId());
        if (getExtraParams() != null) {
            params.putAll(getExtraParams());
        }
        BaseRequest request = getRequest()
                .appendParams(params)
                .setCallback(getCallback());

        if (getMethod().equals(METHOD.POST)) {
            request.post();
        } else if (getMethod().equals(METHOD.POST_BODY)) {
            request.postBody();
        } else if (getMethod().equals(METHOD.GET)) {
            request.get();
        }
    }

    @Override
    public void onLoadMore() {
        Map<String, String> params = new HashMap<>();
        params.put(getMaxKey(), getMaxId());
        if (getExtraParams() != null) {
            params.putAll(getExtraParams());
        }

        BaseRequest request = getRequest()
                .isMore()
                .appendParams(params)
                .setCallback(getCallback());

        if (getMethod().equals(METHOD.POST)) {
            request.post();
        } else if (getMethod().equals(METHOD.POST_BODY)) {
            request.postBody();
        } else if (getMethod().equals(METHOD.GET)) {
            request.get();
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        loadFirstPage();
    }

    public Map<String, String> getExtraParams() {
        return null;
    }
}
