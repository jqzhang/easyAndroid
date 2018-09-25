package com.brothers.peten.bussiness.main.cook.detail;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.brothers.base.utils.GsonUtils;
import com.brothers.peten.R;
import com.brothers.peten.base.AppBaseVlayoutActivity;
import com.brothers.peten.common.router.Router;
import com.google.gson.reflect.TypeToken;

import java.util.List;

@Route(path = Router.PATH.DETAIL_ACTIVITY)
public class DetailActivity extends AppBaseVlayoutActivity implements DetailContract.View {


    @Autowired(name = "id")
    String mId;
    private DetailPresenter mPresenter;

    @Override
    protected void initData() {
        getPresenter().setId(mId);
        getPresenter().onRefresh();
    }

    @Override
    protected void initView() {
        showBackButton(true);
        setAppTitle(null);
        setEnableRefresh(false);
        setEnableLoadMore(false);
        setTitleBackgrount(R.color.transparent);
    }

    @Override
    public void setData(DetailResult.Result data) {
        setAppTitle(data.name);

        SumaryAdapter nameAdapter = new SumaryAdapter();
        nameAdapter.setData(data.recipe);
        addAdapter(nameAdapter);

        MethodAdapter methodAdapter = new MethodAdapter();
        List<DetailResult.Method> methods = GsonUtils.fromJson(data.recipe.method, new TypeToken<List<DetailResult.Method>>() {
        }.getType());
        methodAdapter.setData(methods);
        addAdapter(methodAdapter);
        showContentView();
    }

    @Override
    public DetailPresenter getPresenter() {
        if (null == mPresenter) {
            mPresenter = new DetailPresenter(this);
        }
        return mPresenter;
    }

    @Override
    public boolean isSuspendNavigationBar() {
        return true;
    }
}
