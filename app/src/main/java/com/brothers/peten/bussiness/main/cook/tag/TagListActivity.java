package com.brothers.peten.bussiness.main.cook.tag;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.brothers.peten.R;
import com.brothers.peten.base.AppBaseListActivity;
import com.brothers.peten.common.router.Router;

@Route(path = Router.PATH.TAG_LIST_ACTIVITY)
public class TagListActivity extends AppBaseListActivity implements TagListContract.View {

    @Autowired(name = "cid")
    String mCid;
    private TagListPresenter mPresenter;
    private TagListAdapter mAdapter;

    @Override
    protected void initData() {
        getPresenter().setCid(mCid);
        getPresenter().onRefresh();
    }

    @Override
    protected void initView() {
        setAppTitle(R.string.cookbook);
        showBackButton(true);
    }

    @Override
    public TagListAdapter getAdapter() {
        if (null == mAdapter) {
            mAdapter = new TagListAdapter(this);
        }
        return mAdapter;
    }

    @Override
    public TagListPresenter getPresenter() {
        if (null == mPresenter) {
            mPresenter = new TagListPresenter(this);
        }
        return mPresenter;
    }

    @Override
    public void setData(TagListResult.Result data) {
        getAdapter().setData(data.list);
        showContentView();
    }
}
