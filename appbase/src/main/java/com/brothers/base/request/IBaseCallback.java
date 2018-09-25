package com.brothers.base.request;

/**
 * Created by zhangjiqun on 2017/12/10.
 */

public interface IBaseCallback<T extends BaseResult> {
    void onSuccess(T data);
    void onError(T data);
    void onStart();
    void onFinish(boolean isRefresh);
}
