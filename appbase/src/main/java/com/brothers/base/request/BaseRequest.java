package com.brothers.base.request;


import android.content.Context;
import android.util.Log;

import com.brothers.base.Intrface.NoProguard;
import com.brothers.base.utils.GsonUtils;
import com.brothers.base.utils.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangjiqun on 2017/12/10.
 */

public class BaseRequest<T extends BaseResult> implements NoProguard {

    private Class<T> mClass;

    private Context mContext;

    private String mUrl;

    private boolean mShowLoading;

    private HttpParams mHttpParams;

    private IBaseCallback mIBaseCallback;

    private boolean mIsJson;

    private boolean mIsRefresh = true;

    public BaseRequest(Context context, Class<T> t) {
        mContext = context;
        mClass = t;
    }

    public void delete() {
        OkGo.<String>delete(mUrl)
                .tag(mContext)
                .params(getHttpParams())
                .execute(new BaseCallback<T>(mContext, mIBaseCallback, mClass, mShowLoading, mIsRefresh));
    }

    public void post() {
        OkGo.<String>post(mUrl)
                .tag(mContext)
                .params(getHttpParams())
                .execute(new BaseCallback<T>(mContext, mIBaseCallback, mClass, mShowLoading, mIsRefresh));
    }

    public void get() {
        OkGo.<String>get(mUrl)
                .tag(mContext)
                .params(getHttpParams())
                .execute(new BaseCallback<T>(mContext, mIBaseCallback, mClass, mShowLoading, mIsRefresh));
    }

    public void uploadFile(Map<String, File> fileMap) {

        PostRequest<String> request = OkGo.<String>post(mUrl)
                .isMultipart(true)
                .tag(mContext);

        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            request.params(entry.getKey(), entry.getValue());
        }
        request.params(getHttpParams());
        request.execute(new BaseCallback<T>(mContext, mIBaseCallback, mClass, mShowLoading, mIsRefresh));
    }


    public void uploadFile(File file) {
        Map<String, File> map = new HashMap<>();
        map.put("file", file);
        uploadFile(map);
    }

    public void postBody() {
        Map<String, Object> params = new HashMap<>();

        HttpParams commonParams = OkGo.getInstance().getCommonParams();
        if (commonParams != null && commonParams.urlParamsMap != null && commonParams.urlParamsMap.size() > 0) {
            for (ConcurrentHashMap.Entry<String, List<String>> entry : commonParams.urlParamsMap.entrySet()) {
                if (entry.getValue().size() < 2) {
                    params.put(entry.getKey(), entry.getValue().get(0));
                } else {
                    params.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if (getHttpParams().urlParamsMap != null && getHttpParams().urlParamsMap.size() > 0) {
            for (ConcurrentHashMap.Entry<String, List<String>> entry : getHttpParams().urlParamsMap.entrySet()) {
                if (entry.getValue().size() < 2) {
                    params.put(entry.getKey(), entry.getValue().get(0));
                } else {
                    params.put(entry.getKey(), entry.getValue());
                }
            }
        }

        LogUtils.getInstance().d("upload body : " + GsonUtils.toJson(params));
        OkGo.<String>post(mUrl)
                .tag(mContext)
                .upJson(GsonUtils.toJson(params))
                .execute(new BaseCallback<T>(mContext, mIBaseCallback, mClass, mShowLoading, mIsRefresh));
    }

    public void postBody(File file) {
        OkGo.<String>post(mUrl)
                .tag(mContext)
                .upFile(file)
                .execute(new BaseCallback<T>(mContext, mIBaseCallback, mClass, mShowLoading, mIsRefresh));
    }

    /**
     * @param url
     * @return
     */
    public BaseRequest setUrl(String url) {
        this.mUrl = url;
        return this;
    }

    public BaseRequest setParams(Map<String, String> params) {
        getHttpParams().put(params);
        return this;
    }

    public BaseRequest appendParams(Map<String, String> params) {
        getHttpParams().put(params, false);
        return this;
    }

    public BaseRequest showLoading(boolean show) {
        mShowLoading = show;
        return this;
    }

    public BaseRequest setCallback(IBaseCallback callback) {
        this.mIBaseCallback = callback;
        return this;
    }

    public BaseRequest isMore() {
        this.mIsRefresh = false;
        return this;
    }

    public BaseRequest addUrlParams(String key, List<String> values) {
        getHttpParams().putUrlParams(key, values);
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    private HttpParams getHttpParams() {
        if (null == mHttpParams) {
            mHttpParams = new HttpParams();
        }
        return mHttpParams;
    }

    public BaseRequest(Builder builder) {
    }

    public static class Builder implements NoProguard {
        public static <O> PostRequest<O> post(String url) {
            return OkGo.<O>post(url);
        }
    }
}
