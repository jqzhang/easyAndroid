package com.brothers.peten.bussiness.main.cook.tag;

import com.brothers.appcommon.intrface.ICommonListView;
import com.brothers.peten.base.AppBaseContract;
import com.brothers.peten.base.AppBaseListPresenter;

public class TagListContract extends AppBaseContract {

    interface View extends ICommonListView {
        void setData(TagListResult.Result data);
    }

    abstract static class Presenter extends AppBaseListPresenter<View> {

        public Presenter(View view) {
            super(view);
        }
    }
}
