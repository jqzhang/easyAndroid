package com.brothers.appcommon.base;

import com.brothers.base.Intrface.IListPresenter;
import com.brothers.base.common.BaseListModel;
import com.brothers.base.common.BaseListRequestCallback;
import com.brothers.base.request.IBaseCallback;

public abstract class CommonListModel<P extends IListPresenter, D extends CommonResult> extends BaseListModel<P> {

    private final int FIRST_PAGE = 1;

    protected int mPage = FIRST_PAGE;

    public CommonListModel(P presenter) {
        super(presenter);
    }

    @Override
    protected String getFirstId() {
        mPage = FIRST_PAGE;
        return FIRST_PAGE + "";
    }

    @Override
    protected String getMaxId() {
        return mPage + "";
    }

    @Override
    protected String getMaxKey() {
        return "page";
    }

    public IBaseCallback<D> getCallback() {
        return getCommonCallback();
    }

    public IBaseCallback<D> getLoadMoreDataCallback() {
        return getCommonCallback();
    }

    private IBaseCallback<D> getCommonCallback() {
        return new BaseListRequestCallback<P, D>(mPresenter) {
            @Override
            public void onSuccess(D data) {
                if (data.isSucceed()) {
                    mergeData(data);
                } else {
                    onError(data);
                }
            }

            @Override
            public void onError(D data) {
                mPresenter.setData(getRequest().getUrl(), data);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish(boolean isRefresh) {
                super.onFinish(isRefresh);
            }
        };
    }

    public void mergeData(D data) {
        if (data.hasMore() || FIRST_PAGE == mPage) {
            mPresenter.setData(getRequest().getUrl(), data);
        }
        mPage++;
    }
}
