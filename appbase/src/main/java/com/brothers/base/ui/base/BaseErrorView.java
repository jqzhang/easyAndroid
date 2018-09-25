package com.brothers.base.ui.base;

import android.support.v7.widget.AppCompatButton;
import com.brothers.base.ui.widget.AppTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.brothers.base.Intrface.IBaseErrorView;
import com.brothers.base.Intrface.IBaseView;
import com.brothers.base.R;
import com.brothers.base.utils.InflaterService;
import com.brothers.base.utils.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class BaseErrorView implements IBaseErrorView {

    private IBaseView mIBaseView;

    private View mRootView;

    private LinearLayout layoutError;

    private AppTextView tvErrorTitle;

    private AppTextView tvErrorDesc;

    private SimpleDraweeView sdvNoData;

    private SimpleDraweeView sdvNetError;

    private AppCompatButton btnRetry;

    public BaseErrorView(IBaseView intreface) {
        mIBaseView = intreface;
        init();
    }

    protected void init() {
        mRootView = InflaterService.getInstance().inflate(mIBaseView.getAppActivity(), R.layout.base_error_view, null);
        layoutError = mRootView.findViewById(R.id.layoutError);
        tvErrorDesc = mRootView.findViewById(R.id.tvErrorDesc);
        tvErrorTitle = mRootView.findViewById(R.id.tvErrorTitle);
        sdvNetError = mRootView.findViewById(R.id.sdvNetError);
        sdvNoData = mRootView.findViewById(R.id.sdvNoData);
        btnRetry = mRootView.findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry();
            }
        });
    }

    @Override
    public View getView() {
        return mRootView;
    }

    @Override
    public void setRetryText(String str) {
        btnRetry.setText(str);
    }

    @Override
    public void showError(String title, String content) {
        layoutError.setVisibility(View.VISIBLE);
        sdvNoData.setVisibility(View.GONE);
        sdvNetError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText(StringUtils.isEmpty(title) ? mIBaseView.getAppActivity().getString(R.string.net_error) : title);
        tvErrorDesc.setVisibility(StringUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        tvErrorDesc.setText(content);
    }

    @Override
    public void showNoData(String title, String content) {
        layoutError.setVisibility(View.VISIBLE);
        sdvNoData.setVisibility(View.VISIBLE);
        sdvNetError.setVisibility(View.GONE);
        tvErrorTitle.setText(StringUtils.isEmpty(title) ? mIBaseView.getAppActivity().getString(R.string.no_data) : title);
        tvErrorDesc.setVisibility(StringUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        tvErrorDesc.setText(content);
    }

    @Override
    public void hide() {
        layoutError.setVisibility(View.GONE);
    }

    @Override
    public void retry() {
        mIBaseView.retry();
    }
}
