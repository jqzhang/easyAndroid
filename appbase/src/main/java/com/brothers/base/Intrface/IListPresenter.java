package com.brothers.base.Intrface;

import com.brothers.base.request.BaseResult;

/**
 * Created by zhangjiqun on 2018/1/3.
 */

public interface IListPresenter extends IBasePresenter {
    void refreshFinish();

    void loadMoreFinish();
}
