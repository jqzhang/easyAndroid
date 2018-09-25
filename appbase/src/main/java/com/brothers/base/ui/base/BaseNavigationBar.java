package com.brothers.base.ui.base;

import android.support.design.widget.AppBarLayout;
import com.brothers.base.ui.widget.AppTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.brothers.base.Intrface.IBaseNavigationBar;
import com.brothers.base.Intrface.IBaseView;
import com.brothers.base.R;
import com.brothers.base.utils.InflaterService;
import com.brothers.base.utils.StringUtils;

public class BaseNavigationBar implements IBaseNavigationBar {

    private View mRootView;

    private IBaseView mIBaseView;

    protected Toolbar toolbar;

    protected AppBarLayout appBarLayout;

    private AppTextView tvBaseTitle;

    private LinearLayout layoutBarRight;

    private AppTextView tvRightTitle;

    public BaseNavigationBar(IBaseView intreface) {
        mIBaseView = intreface;
        mRootView = inflaterView();
        init();
    }

    protected View inflaterView() {
        return InflaterService.getInstance().inflate(mIBaseView.getAppActivity(), R.layout.base_navigation_bar, null);
    }

    protected void init() {
        toolbar = mRootView.findViewById(R.id.toolbar);
        appBarLayout = mRootView.findViewById(R.id.appBarLayout);
        tvBaseTitle = mRootView.findViewById(R.id.tvBaseTitle);
        layoutBarRight = mRootView.findViewById(R.id.layoutBarRight);
        tvRightTitle = mRootView.findViewById(R.id.tvRightTitle);

        if (mIBaseView.initSupportActionBar()) {
            mIBaseView.getAppActivity().setSupportActionBar(toolbar);
        }

        mIBaseView.getAppActivity().setTitle("");
        tvRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIBaseView.onRightViewCliked(v);
            }
        });

        layoutBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIBaseView.onRightViewCliked(v);
            }
        });
    }

    @Override
    public View getView() {
        return mRootView;
    }

    @Override
    public void showTitleBar(boolean show) {
        if (show) {
            mIBaseView.getAppActivity().setSupportActionBar(toolbar);
            mIBaseView.getAppActivity().setTitle("");
        }
        appBarLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setAppTitle(String title) {
        tvBaseTitle.setText(StringUtils.isEmpty(title) ? mIBaseView.getAppActivity().getString(R.string.app_name) : title);
        showTitleBar(true);
    }

    @Override
    public void setRightTitle(String title) {
        tvRightTitle.setText(title);
    }

    @Override
    public void addBarRightView(View view) {
        layoutBarRight.addView(view);
        layoutBarRight.setVisibility(View.VISIBLE);
    }

    @Override
    public void setBackgroundColor(int colorId) {
        appBarLayout.setBackgroundColor(mIBaseView.getAppActivity().getResources().getColor(colorId));
        toolbar.setBackgroundColor(mIBaseView.getAppActivity().getResources().getColor(colorId));
    }
}
