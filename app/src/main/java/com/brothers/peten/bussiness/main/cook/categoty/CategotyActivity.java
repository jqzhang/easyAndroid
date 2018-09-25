package com.brothers.peten.bussiness.main.cook.categoty;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.brothers.base.common.BasePresenter;
import com.brothers.base.utils.GsonUtils;
import com.brothers.peten.base.AppBaseListActivity;
import com.brothers.peten.bussiness.main.cook.CookResult;
import com.brothers.peten.common.router.Router;

@Route(path = Router.PATH.CATEGORY_LIST_ACTIVITY)
public class CategotyActivity extends AppBaseListActivity implements CategoryContract.View {

    @Autowired(name = "childs")
    String mChilds;
    private CategoryAdapter mAdapter;

    @Override
    protected void initData() {
        CookResult.Childs childs = GsonUtils.fromJson(mChilds, CookResult.Childs.class);

        getAdapter().setData(childs.childs);
        setAppTitle(childs.categoryInfo.name);
    }

    @Override
    protected void initView() {
        showBackButton(true);
        setEnableLoadMore(false);
        setEnableRefresh(false);
        showContentView();
    }

    @Override
    public CategoryAdapter getAdapter() {
        if (null == mAdapter) {
            mAdapter = new CategoryAdapter(this);
        }
        return mAdapter;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
