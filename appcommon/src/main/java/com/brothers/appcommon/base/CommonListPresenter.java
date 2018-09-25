package com.brothers.appcommon.base;

import com.brothers.appcommon.intrface.ICommonListPresenter;
import com.brothers.base.common.BaseListPresenter;
import com.brothers.appcommon.intrface.ICommonListView;

/**
 * Created by zhangjiqun on 2018/1/30.
 */

public abstract class CommonListPresenter<V extends ICommonListView> extends BaseListPresenter<V> implements ICommonListPresenter {


    public CommonListPresenter(V view) {
        super(view);
    }

    @Override
    public void onLoadMore() {
        getModel().onLoadMore();
    }
}
