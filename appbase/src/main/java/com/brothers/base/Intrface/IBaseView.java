package com.brothers.base.Intrface;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.brothers.base.common.BasePresenter;
import com.brothers.base.request.BaseData;

/**
 * Created by zhangjiqun on 2018/1/3.
 */

public interface IBaseView {
    void showToast(int resId);
    void showToast(String content);
    void showError(String title, String content);
    void showNoData(String title, String content);
    void showContentView();
    void showLoading();
    void dismissLoading();
    void showLoadingDialog();
    void dismissLoadingDialog();
    boolean isDestroy();
    void clearData();
    void closeActivity();
    BasePresenter getPresenter();
    Activity getContext();
    AppCompatActivity getAppActivity();
    void onClickDialogConfirm(int code);
    void onClickDialogCancel(int code);
    void onClickDialogIKnow(int code);
    void onRightViewCliked(View v);
    View getRootView();
    void retry();
    boolean isSuspendNavigationBar();

    /**
     * 是否初始化ActionBar，如果Activity 嵌套Fragment会导致左上角返回按钮无效，在Fragment 中重写此方法，返回 false 可解决此问题
     * @return
     */
    boolean initSupportActionBar();

}
