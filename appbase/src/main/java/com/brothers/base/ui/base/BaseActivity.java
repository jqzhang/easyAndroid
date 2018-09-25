package com.brothers.base.ui.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.brothers.base.Intrface.IBaseView;
import com.brothers.base.ui.widget.BaseDialog;
import com.brothers.base.utils.ActivityStack;
import com.brothers.base.utils.GsonUtils;
import com.brothers.base.utils.LanguageUtils;
import com.brothers.base.utils.StringUtils;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangjiqun on 2017/12/10.
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    protected abstract int getContentView();

    protected abstract void init();

    private Unbinder mUnbinder;

    protected Map<String, String> mParams;

    private BaseViewHolder mBaseViewHolder;

    private OnClickHomeListener mOnClickHomeListener;

    /** 登陆成功 */
    protected void onLoginSuccess() {

    }

    /** 退出登陆 */
    protected void onLoginOut() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.push(this);
        ARouter.getInstance().inject(this);
        setContentView(getBaseViewHolder().getRootView());

        getBaseViewHolder().addContentView(getContentView());
        mUnbinder = ButterKnife.bind(this);

        String params = getIntent().getStringExtra("params");
        if (StringUtils.notEmpty(params)) {
            mParams = GsonUtils.fromJson(params, HashMap.class);
        }

        if (enableEventBus()) {
            EventBus.getDefault().register(this);
        }
        init();
    }

    protected boolean enableEventBus() {
        return false;
    }

    protected String getParamsValue(String key) {
        if (null == mParams) {
            return "";
        }
        return mParams.get(key);
    }

    protected void setBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Android 5.0 以上 全透明
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 状态栏（以上几行代码必须，参考setStatusBarColor|setNavigationBarColor方法源码）
            window.setStatusBarColor(Color.TRANSPARENT);
            // 虚拟导航键
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Android 4.4 以上 半透明
            Window window = getWindow();
            // 状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 虚拟导航键
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.pop(this);
        OkGo.getInstance().cancelTag(this);
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (enableEventBus() ) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mOnClickHomeListener != null) {
                    mOnClickHomeListener.onClickHome();
                    break;
                }
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public boolean isSuspendNavigationBar() {
        return false;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageUtils.attachBaseContext(newBase, BaseApplication.getInstance().getAppLanguage()));
    }

    public View getRootView() {
        return getBaseViewHolder().getContentRootView();
    }

    protected BaseViewHolder getBaseViewHolder() {
        if(null == mBaseViewHolder) {
            mBaseViewHolder = new BaseViewHolder(this, isSuspendNavigationBar());
        }
        return mBaseViewHolder;
    }

    protected void setOnClickHomeListener(OnClickHomeListener onClickHomeListener) {
        mOnClickHomeListener = onClickHomeListener;
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    protected void setRetryText(String str) {
        getBaseViewHolder().setRetryText(str);
    }

    protected void showTitleBar(boolean show) {
        getBaseViewHolder().showTitleBar(show);
    }

    protected void setAppTitle(int resId) {
        setAppTitle(getString(resId));
    }

    protected void setAppTitle(String title) {
        getBaseViewHolder().setAppTitle(title);
    }

    protected void setRightTitle(int resId) {
        setRightTitle(getString(resId));
    }

    protected void setRightTitle(String title) {
        getBaseViewHolder().setRightTitle(title);
    }

    protected void addBarRightView(View view) {
        getBaseViewHolder().addBarRightView(view);
    }

    protected void showBackButton(boolean show) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    public void setTitleBackgrount(int colorId) {
        getBaseViewHolder().setTitleBackground(colorId);
    }

    protected void showDialog(String content, final int code) {
        getBaseViewHolder().showDialog(content, code);
    }

    protected BaseDialog getDialog() {
        return getBaseViewHolder().getDialog();
    }

    protected void showDialog(String content) {
        showDialog(content, 0);
    }


    @Override
    public void showToast(int resId) {
        BaseToast.show(this, -1, resId);
    }

    @Override
    public void showToast(String content) {
        BaseToast.show(this, -1, content);
    }

    public void showError(String title) {
        showError(title, "");
    }

    public void showError() {
        showError("", "");
    }

    @Override
    public void showError(String title, String content) {
        getBaseViewHolder().showError(title, content);
    }

    public void showNoData() {
        showNoData("", "");
    }

    @Override
    public void showNoData(String title, String content) {
        getBaseViewHolder().showNoData(title, content);
    }

    @Override
    public void showLoading() {
        getBaseViewHolder().showLoading();
    }

    @Override
    public void showContentView() {
        getBaseViewHolder().showContentView();
    }

    @Override
    public void dismissLoading() {
        getBaseViewHolder().dismissLoading();
    }

    @Override
    public void showLoadingDialog() {
        getBaseViewHolder().showLoadingDialog();
    }

    @Override
    public void dismissLoadingDialog() {
        getBaseViewHolder().dismissLoadingDialog();
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public AppCompatActivity getAppActivity() {
        return this;
    }

    @Override
    public void onClickDialogConfirm(int code) {
        if (getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }

    @Override
    public void onClickDialogCancel(int code) {
        if (getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }

    @Override
    public void onClickDialogIKnow(int code) {
        if (getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }

    @Override
    public boolean initSupportActionBar() {
        return true;
    }

    @Override
    public void onRightViewCliked(View v) {

    }

    @Override
    public boolean isDestroy() {
        return isFinishing();
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed();
    }


    public void retry() {
        getPresenter().onRefresh();
    }

    @Override
    public void clearData() {
    }

    @Override
    public void closeActivity() {
        if (isFinishing()) {
            return;
        }
        finish();
    }

    public void setBackgroundColor(int colorId) {
        getBaseViewHolder().setBackgroundColor(colorId);
    }

    public void setBackgroundResource(int resId) {
        getBaseViewHolder().setBackgroundResource(resId);
    }
}
