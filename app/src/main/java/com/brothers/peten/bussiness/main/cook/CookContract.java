package com.brothers.peten.bussiness.main.cook;

import com.brothers.appcommon.base.CommonContract;
import com.brothers.appcommon.intrface.ICommonListView;
import com.brothers.peten.base.AppBaseListPresenter;

public class CookContract extends CommonContract {

    interface View extends ICommonListView {
        void setData(CookResult.Result data);
    }

    abstract static class Presenter extends AppBaseListPresenter<View> {

        public Presenter(View view) {
            super(view);
        }
    }
}
