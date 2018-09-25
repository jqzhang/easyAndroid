package com.brothers.peten.bussiness.splash;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.brothers.base.common.BasePresenter;
import com.brothers.peten.R;
import com.brothers.peten.base.AppBaseActivity;
import com.brothers.peten.common.router.Router;

@Route(path = Router.PATH.SPLASH_ACTIVITY)
public class SplashActivity extends AppBaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        showContentView();
        Router.getInstance().start(Router.PATH.MAIN_ACTIVITY);
        closeActivity();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
