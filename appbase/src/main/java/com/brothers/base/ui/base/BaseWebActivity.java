package com.brothers.base.ui.base;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.brothers.base.R;
import com.brothers.base.common.BasePresenter;
import com.brothers.base.utils.StringUtils;
import com.brothers.base.common.BasePresenter;
import com.brothers.base.utils.StringUtils;

/**
 * Created by zhangjiqun on 2018/1/18.
 */

public class BaseWebActivity extends BaseActivity {

    protected WebView webView;

    private ProgressBar progressBar;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init() {
        showBackButton(true);
        showTitleBar(true);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebChromeClient(new BaseWebChromeClient());
        webView.setWebViewClient(new BaseWebViewClient());
        showContentView();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            closeActivity();
        }
    }

    @Override
    protected void setAppTitle(String title) {
        if (StringUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        super.setAppTitle(title);
    }

    public class BaseWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (100 == newProgress) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
        }
    }

    public class BaseWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            setAppTitle(view.getTitle());
        }
    }
}
