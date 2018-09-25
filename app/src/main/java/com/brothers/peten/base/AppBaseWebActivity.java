package com.brothers.peten.base;

import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.brothers.base.ui.base.BaseWebActivity;
import com.brothers.peten.common.router.Router;

/**
 * Created by zhangjiqun on 2018/1/18.
 */
@Route(path = Router.PATH.BASE_WEBVIEW_ACTIVITY)
public class AppBaseWebActivity extends BaseWebActivity {

    @Autowired(name = "url")
    String mUrl;
    private ProgressBar progressBar;

    @Override
    protected void init() {
        super.init();
        webView.loadUrl(mUrl);
    }
}
