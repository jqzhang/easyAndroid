package com.brothers.peten.base;

import com.brothers.appcommon.base.CommonListPresenter;
import com.brothers.appcommon.intrface.ICommonListView;

/**
 * Created by zhangjiqun on 2018/1/30.
 */

public abstract class AppBaseListPresenter<V extends ICommonListView> extends CommonListPresenter<V> {


    public AppBaseListPresenter(V view) {
        super(view);
    }

    @Override
    public void onLoadMore() {
        getModel().onLoadMore();
    }

}
