package com.brothers.peten.bussiness;

import android.app.ActivityManager;
import android.os.Build;

import com.brothers.appcommon.CommonApplication;
import com.brothers.base.eventbus.BaseEventBus;
import com.brothers.peten.common.Urls;
import com.brothers.peten.util.Device;
import com.brothers.peten.util.MyBitmapMemoryCacheParamsSupplier;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.tencent.bugly.crashreport.CrashReport;

import okhttp3.OkHttpClient;

public class AppApplication extends CommonApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initCommonParams();
        Device.init(this);
        initFresco();
        CrashReport.initCrashReport(getApplicationContext(), "268f667bec", false);
        BaseEventBus.getDefault().initial(null);
    }

    private void initFresco() {
        DiskCacheConfig diskCacheConfig =
                DiskCacheConfig.newBuilder(this).setBaseDirectoryPath(getExternalCacheDir()).build();
        ProgressiveJpegConfig pjpegConfig = new SimpleProgressiveJpegConfig();
        OkHttpClient okHttpClient = new OkHttpClient();
        ImagePipelineConfig pipelineConfig = OkHttpImagePipelineConfigFactory.newBuilder(this, okHttpClient)
                .setProgressiveJpegConfig(pjpegConfig)
                .setDownsampleEnabled(true) //解决png 无法被resize 的问题
                .setBitmapMemoryCacheParamsSupplier(new MyBitmapMemoryCacheParamsSupplier(
                        (ActivityManager) getSystemService(ACTIVITY_SERVICE)))
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this, pipelineConfig);
    }

    private void initCommonParams() {
        HttpParams params = new HttpParams();
        params.put("key", Urls.getMobApiKey());
        params.put("deviceInfo", Build.BRAND + "_" + Build.MODEL + "_" + Build.VERSION.RELEASE);
        OkGo.getInstance().addCommonParams((HttpParams) params);
    }
}
