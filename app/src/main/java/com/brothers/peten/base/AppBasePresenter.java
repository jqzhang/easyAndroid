package com.brothers.peten.base;

import com.brothers.appcommon.base.CommonPresenter;
import com.brothers.appcommon.intrface.ICommonView;
import com.brothers.base.utils.StringUtils;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public abstract class AppBasePresenter<V extends ICommonView> extends CommonPresenter<V> {

    public AppBasePresenter(V view) {
        super(view);
    }

    @Override
    public void onRefresh() {
        getModel().onRefresh();
    }

    public void uploadFile(File file) {
        if (null == file || false == file.exists()) {
            return;
        }
        ((AppBaseModel) getModel()).uploadFile(file);
    }

    public void compressFile(String path, OnCompressFileListener listener) {
        Luban.with(getContext())
                .load(path)
                .ignoreBy(100)
                .setTargetDir(getContext().getExternalCacheDir().getAbsolutePath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(StringUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        if (listener != null) {
                            listener.onStrat();
                        }
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (listener != null) {
                            listener.onSuccess(file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onError(e);
                        }
                    }
                }).launch();
    }

    public void uploadFile(String path) {
        Luban.with(getContext())
                .load(path)
                .ignoreBy(100)
                .setTargetDir(getContext().getExternalCacheDir().getAbsolutePath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(StringUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        uploadFile(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    public interface OnCompressFileListener {
        void onSuccess(File file);

        void onStrat();

        void onError(Throwable e);
    }
}
