package com.brothers.base.request;

import android.content.Context;

import com.brothers.base.Intrface.NoProguard;
import com.brothers.base.R;
import com.brothers.base.ui.dialog.LoadingDialog;
import com.brothers.base.utils.GsonUtils;
import com.brothers.base.Intrface.NoProguard;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * Created by zhangjiqun on 2017/12/10.
 */

public class BaseCallback<T extends BaseResult> extends StringCallback implements NoProguard {

    private Context mContext;

    private IBaseCallback mIBaseCallback;

    private Class<T> mClass;

    private boolean mIsShowLoadingDialog;

    private LoadingDialog mLoadingDialog;

    private boolean mIsRefresh;


    public BaseCallback(Context context, IBaseCallback callback, Class<T> clazz, boolean showLoading, boolean isRefresh) {
        this.mContext = context;
        mIBaseCallback = callback;
        mClass = clazz;
        mIsShowLoadingDialog = showLoading;
        mIsRefresh = isRefresh;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (mIBaseCallback != null) {
            mIBaseCallback.onStart();
        }
        showLoading();
    }

    @Override
    public void onSuccess(Response<String> response) {
        T data = GsonUtils.fromJson(response.body(), mClass);
        if (null == data) {
            try {
                data = mClass.newInstance();
                data.message = mContext.getString(R.string.parse_data_error);
                if (mIBaseCallback != null) {
                    mIBaseCallback.onError(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (mIBaseCallback != null) {
                mIBaseCallback.onSuccess(data);
            }
        }
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        try {
            T data = mClass.newInstance();
            data.message = response.getException().getMessage();
            if (mIBaseCallback != null) {
                mIBaseCallback.onError(data);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (mIBaseCallback != null) {
            mIBaseCallback.onFinish(mIsRefresh);
        }
        dismissLoading();
    }

    private void showLoading() {
        if (!mIsShowLoadingDialog) {
            return;
        }
        if (null == mLoadingDialog) {
            mLoadingDialog = new LoadingDialog(mContext);
        }
        mLoadingDialog.show();
    }

    private void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
