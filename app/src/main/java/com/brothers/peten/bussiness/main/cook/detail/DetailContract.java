package com.brothers.peten.bussiness.main.cook.detail;

import com.brothers.appcommon.intrface.ICommonListView;
import com.brothers.peten.base.AppBaseContract;
import com.brothers.peten.base.AppBaseListPresenter;

public class DetailContract extends AppBaseContract {

    interface View extends ICommonListView {
        void setData(DetailResult.Result data);
    }

    abstract static class Presenter extends AppBaseListPresenter<View> {

        public Presenter(View view) {
            super(view);
        }
    }
}
