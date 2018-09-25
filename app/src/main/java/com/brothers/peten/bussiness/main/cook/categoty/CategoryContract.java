package com.brothers.peten.bussiness.main.cook.categoty;

import com.brothers.appcommon.intrface.ICommonListView;
import com.brothers.peten.base.AppBaseContract;
import com.brothers.peten.base.AppBaseListPresenter;

public class CategoryContract extends AppBaseContract {

    interface View extends ICommonListView {
    }

    abstract static class Presenter extends AppBaseListPresenter<View> {

        public Presenter(View view) {
            super(view);
        }
    }
}
