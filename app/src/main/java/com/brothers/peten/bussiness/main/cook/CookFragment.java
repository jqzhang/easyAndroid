package com.brothers.peten.bussiness.main.cook;

import com.brothers.base.common.BasePresenter;
import com.brothers.peten.R;
import com.brothers.peten.base.AppBaseListFragment;

public class CookFragment extends AppBaseListFragment implements CookContract.View {

    private CookPresenter mPresenter;

    private CookAdapter mAdapter;

    @Override
    protected void initData() {
        getPresenter().onRefresh();
    }

    @Override
    protected void initView() {
        setAppTitle(R.string.cookbook);
        setEnableLoadMore(false);
    }

    @Override
    public CookAdapter getAdapter() {
        if (null == mAdapter) {
            mAdapter = new CookAdapter(this);
        }
        return mAdapter;
    }

    @Override
    public BasePresenter getPresenter() {
        if (null == mPresenter) {
            mPresenter = new CookPresenter(this);
        }
        return mPresenter;
    }

    @Override
    public void setData(CookResult.Result data) {
        getAdapter().setData(data.childs);
        showContentView();
    }
}
