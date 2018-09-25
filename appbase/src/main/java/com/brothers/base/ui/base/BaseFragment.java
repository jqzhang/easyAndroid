package com.brothers.base.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.brothers.base.Intrface.IBaseView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangjiqun on 2017/12/22.
 */

public abstract class BaseFragment extends Fragment implements IBaseView {

    protected abstract int getContentView();

    protected abstract void init();

    private Unbinder mUnbinder;

    private BaseViewHolder mBaseViewHolder;

    private OnClickHomeListener mOnClickHomeListener;

    /** 登陆成功 */
    protected void onLoginSuccess() {

    }

    /** 退出登陆 */
    protected void onLoginOut() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getBaseViewHolder().addContentView(getContentView());
        mUnbinder = ButterKnife.bind(this, getBaseViewHolder().getRootView());
        return getBaseViewHolder().getRootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (enableEventBus()) {
            EventBus.getDefault().register(this);
        }
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (enableEventBus() ) {
            EventBus.getDefault().unregister(this);
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public boolean initSupportActionBar() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mOnClickHomeListener != null) {
                    mOnClickHomeListener.onClickHome();
                    break;
                }
                closeActivity();
                break;
        }
        return true;
    }

    protected void setOnClickHomeListener(OnClickHomeListener onClickHomeListener) {
        mOnClickHomeListener = onClickHomeListener;
    }

    protected boolean enableEventBus() {
        return false;
    }

    @Override
    public void closeActivity() {
        ((BaseActivity) getActivity()).closeActivity();
    }

    public View getRootView() {
        return getBaseViewHolder().getRootView();
    }

    protected BaseViewHolder getBaseViewHolder() {
        if (null == mBaseViewHolder) {
            mBaseViewHolder = new BaseViewHolder(this, isSuspendNavigationBar());
        }
        return mBaseViewHolder;
    }

    protected void setRetryText(String str) {
        getBaseViewHolder().setRetryText(str);
    }

    protected void showTitleBar(int resId) {
        showTitleBar(true);
        setAppTitle(resId);
    }

    protected void showTitleBar(boolean show) {
        getBaseViewHolder().showTitleBar(show);
    }

    protected void setAppTitle(int resId) {
        getBaseViewHolder().setAppTitle(getString(resId));
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
        ((BaseActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    protected void showDialog(String content, final int code) {
        getBaseViewHolder().showDialog(content, code);
    }

    protected void showDialog(String content) {
        showDialog(content, 0);
    }

    @Override
    public void showToast(int resId) {
        BaseToast.show(getActivity(), -1, resId);
    }

    @Override
    public void showToast(String content) {
        BaseToast.show(getActivity(), -1, content);
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
    public boolean isSuspendNavigationBar() {
        return false;
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
        return getActivity();
    }

    @Override
    public void onClickDialogConfirm(int code) {
        getBaseViewHolder().dialogDismiss();
    }

    @Override
    public void onClickDialogCancel(int code) {
        getBaseViewHolder().dialogDismiss();
    }

    @Override
    public void onClickDialogIKnow(int code) {
        getBaseViewHolder().dialogDismiss();
    }

    @Override
    public void onRightViewCliked(View v) {
        getBaseViewHolder().dialogDismiss();
    }

    @Override
    public AppCompatActivity getAppActivity() {
        return (AppCompatActivity) getActivity();
    }

    @Override
    public boolean isDestroy() {
        return getActivity() == null || getActivity().isFinishing();
    }

    @Override
    public void retry() {
        if (null == getPresenter()) {
            return;
        }
        getPresenter().onRefresh();
    }

    @Override
    public void clearData() {

    }

    public void setTitleBackgrount(int colorId) {
        getBaseViewHolder().setTitleBackground(colorId);
    }

    public void setBackgroundColor(int colorId) {
        getBaseViewHolder().setBackgroundColor(colorId);
    }

    public void setBackgroundResource(int resId) {
        getBaseViewHolder().setBackgroundResource(resId);
    }
}
