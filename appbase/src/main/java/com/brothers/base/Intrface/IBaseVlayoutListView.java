package com.brothers.base.Intrface;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.brothers.base.request.BaseData;
import com.brothers.base.request.BaseResult;
import com.brothers.base.ui.list.BaseAdapter;

/**
 * Created by zhangjiqun on 2018/1/3.
 */

public interface IBaseVlayoutListView extends IListView {

    DelegateAdapter getAdapter();

    VirtualLayoutManager getLayoutManager();
}
