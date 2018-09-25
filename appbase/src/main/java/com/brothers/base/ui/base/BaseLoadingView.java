package com.brothers.base.ui.base;

import android.view.View;

import com.brothers.base.Intrface.IBaseLoadingView;
import com.brothers.base.Intrface.IBaseView;
import com.brothers.base.R;
import com.brothers.base.utils.InflaterService;
import com.brothers.base.utils.UriUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class BaseLoadingView implements IBaseLoadingView {

    private IBaseView mIBaseView;

    private View mRootView;

    private SimpleDraweeView sdvLoading;

    private DraweeController mDraweeController;

    public BaseLoadingView(IBaseView intreface) {
        mIBaseView = intreface;
        mRootView = inflaterView();
        init();
    }

    protected View inflaterView() {
        return InflaterService.getInstance().inflate(mIBaseView.getAppActivity(), R.layout.base_loading_view, null);
    }

    private void init() {
        sdvLoading = mRootView.findViewById(R.id.sdvLoading);
    }

    @Override
    public void show() {
        if (null == mDraweeController) {
            mDraweeController = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    //加载drawable里的一张gif图
                    .setUri(UriUtils.parse("res://" + mIBaseView.getAppActivity().getPackageName() + "/" + R.drawable.loadding))//设置uri
                    .build();
            //设置Controller
            sdvLoading.setController(mDraweeController);
        }

        mRootView.setVisibility(View.VISIBLE);
        sdvLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        mRootView.setVisibility(View.GONE);
    }

    @Override
    public View getView() {
        return mRootView;
    }
}
