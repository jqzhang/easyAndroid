package com.brothers.base.ui.base;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.brothers.base.Intrface.IBaseErrorView;
import com.brothers.base.Intrface.IBaseLoadingView;
import com.brothers.base.Intrface.IBaseNavigationBar;
import com.brothers.base.Intrface.IBaseView;
import com.brothers.base.Intrface.NoProguard;
import com.brothers.base.R;
import com.brothers.base.ui.dialog.LoadingDialog;
import com.brothers.base.ui.widget.BaseDialog;
import com.brothers.base.utils.InflaterService;

public class BaseViewHolder implements NoProguard {

    private RelativeLayout mRootView;

    private IBaseView mIBaseView;

    private LinearLayout layoutContent;

    private BaseLoadingView mBaseLoadingView;

    private BaseErrorView mBaseErrorView;

    private BaseNavigationBar mBaseNavigationBar;

    private BaseDialog mDialog;

    private LoadingDialog mLoadingDialog;

    // 内容是否充满全屏，放在 actionBar 后面
    private boolean isSuspendNavigationBar;

    public BaseViewHolder(IBaseView intreface, boolean suspendNavigationBar) {
        mIBaseView = intreface;
        isSuspendNavigationBar = suspendNavigationBar;
        init();
    }

    private void initView() {
        mRootView = new RelativeLayout(mIBaseView.getContext());
        mRootView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Navigation Bar
        RelativeLayout.LayoutParams paramsBar = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsBar.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        getNavigationBar().getView().setLayoutParams(paramsBar);

        // Content
        layoutContent = new LinearLayout(mIBaseView.getContext());
        layoutContent.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams paramsContent = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (false == isSuspendNavigationBar()) {
            paramsContent.addRule(RelativeLayout.BELOW, getNavigationBar().getView().getId());
        } else {
            getNavigationBar().setBackgroundColor(R.color.transparent);
        }
        layoutContent.setLayoutParams(paramsContent);

        // Error View
        RelativeLayout.LayoutParams paramsError = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsError.addRule(RelativeLayout.BELOW, getNavigationBar().getView().getId());
        getErrorView().getView().setLayoutParams(paramsError);

        // Loading view
        RelativeLayout.LayoutParams paramsLoading = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsLoading.addRule(RelativeLayout.BELOW, getNavigationBar().getView().getId());
        getLoadingView().getView().setLayoutParams(paramsLoading);

        mRootView.addView(layoutContent, paramsContent);
        mRootView.addView(getNavigationBar().getView(), paramsBar);
        mRootView.addView(getErrorView().getView(), paramsError);
        mRootView.addView(getLoadingView().getView(), paramsLoading);
    }

    private void init() {
        initView();

        //以下代码用于去除阴影
        if (Build.VERSION.SDK_INT >= 21) {
            mIBaseView.getAppActivity().getSupportActionBar().setElevation(0);
        }
        showTitleBar(isSuspendNavigationBar());
    }

    public View getRootView() {
        return mRootView;
    }

    public ViewGroup getContentRootView() {
        return layoutContent;
    }

    public boolean isSuspendNavigationBar() {
        return isSuspendNavigationBar;
    }

    public void addContentView(int viewId) {
        View view = InflaterService.getInstance().inflate(mIBaseView.getAppActivity(), viewId, getContentRootView(), false);
        getContentRootView().addView(view);
    }

    public void setRetryText(String str) {
        getErrorView().setRetryText(str);
    }

    public void showError(String title, String content) {
        layoutContent.setVisibility(View.GONE);
        getLoadingView().hide();
        getErrorView().showError(title, content);
    }

    public void showNoData(String title, String content) {
        layoutContent.setVisibility(View.GONE);
        getLoadingView().hide();
        getErrorView().showNoData(title, content);
    }

    public void showLoading() {
        layoutContent.setVisibility(View.GONE);
        getErrorView().hide();
        getLoadingView().show();
    }

    public void showContentView() {
        getContentRootView().setVisibility(View.VISIBLE);
        getLoadingView().hide();
        getErrorView().hide();
    }

    public void dismissLoading() {
        getLoadingView().hide();
    }

    public BaseDialog getDialog() {
        if (null == mDialog) {
            mDialog = new BaseDialog(mIBaseView.getAppActivity());
        }
        return mDialog;
    }

    public void dialogDismiss() {
        if (null == getDialog() || false == getDialog().isShowing()) {
            return;
        }
        getDialog().dismiss();
    }

    public void showDialog(String content, final int code) {
        getDialog().setListener(new BaseDialog.CommonDialogOnClickListener() {
            @Override
            public void onConfirm(int code) {
                mIBaseView.onClickDialogConfirm(code);
            }

            @Override
            public void onCancel(int code) {
                mIBaseView.onClickDialogCancel(code);
            }

            @Override
            public void onIKnow(int code) {
                mIBaseView.onClickDialogIKnow(code);
            }
        });
        getDialog().setContent(content);
        getDialog().showTowButton(code);
    }

    public void showLoadingDialog() {
        if (null == mLoadingDialog) {
            mLoadingDialog = new LoadingDialog(mIBaseView.getAppActivity());
        }
        if (mLoadingDialog.isShowing()) {
            return;
        }
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (null == mLoadingDialog) {
            return;
        }
        mLoadingDialog.dismiss();
    }

    protected IBaseLoadingView getLoadingView() {
        if (null == mBaseLoadingView) {
            mBaseLoadingView = new BaseLoadingView(mIBaseView);
        }
        return mBaseLoadingView;
    }


    protected IBaseErrorView getErrorView() {
        if (null == mBaseErrorView) {
            mBaseErrorView = new BaseErrorView(mIBaseView);
        }

        return mBaseErrorView;
    }


    protected IBaseNavigationBar getNavigationBar() {
        if (null == mBaseNavigationBar) {
            mBaseNavigationBar = new BaseNavigationBar(mIBaseView);
        }

        return mBaseNavigationBar;
    }

    public void setBackgroundColor(int colorId) {
        mRootView.setBackgroundColor(mIBaseView.getContext().getResources().getColor(colorId));
    }

    public void setBackgroundResource(int resId) {
        mRootView.setBackgroundResource(resId);
    }

    public void setTitleBackground(int colorId) {
        getNavigationBar().setBackgroundColor(colorId);
    }

    public void showTitleBar(boolean show) {
        getNavigationBar().showTitleBar(show);
    }

    public void setAppTitle(String title) {
        getNavigationBar().setAppTitle(title);
    }

    public void setRightTitle(String title) {
        getNavigationBar().setRightTitle(title);
    }

    public void addBarRightView(View view) {
        getNavigationBar().addBarRightView(view);
    }
}
