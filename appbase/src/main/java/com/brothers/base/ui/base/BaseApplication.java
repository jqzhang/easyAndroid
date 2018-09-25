package com.brothers.base.ui.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.brothers.base.R;
import com.brothers.base.eventbus.BaseEventBus;
import com.brothers.base.utils.ActivityStack;
import com.brothers.base.utils.ConfigureUtils;
import com.brothers.base.utils.LanguageUtils;
import com.brothers.base.utils.LogUtils;
import com.brothers.base.utils.SPUtils;
import com.brothers.base.utils.ActivityStack;
import com.brothers.base.utils.ConfigureUtils;
import com.brothers.base.utils.LanguageUtils;
import com.brothers.base.utils.LogUtils;
import com.brothers.base.utils.SPUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by zhangjiqun on 2017/12/10.
 */
public abstract class BaseApplication extends MultiDexApplication {

    protected Handler handler = new Handler(Looper.getMainLooper());

    public static BaseApplication getInstance() {
        return mInstance;
    }

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        SPUtils.init(this);
        if (ConfigureUtils.isDebug()) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        Fresco.initialize(this);
        BaseEventBus.getDefault().initial(null);
        initOkGo();
    }

    @Override
    protected void attachBaseContext(Context base) {
        if (!SPUtils.hasInited()) {
            SPUtils.init(base);
        }
        super.attachBaseContext(LanguageUtils.attachBaseContext(base, getAppLanguage()));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onLanguageChange();
    }

    private void onLanguageChange() {
        LanguageUtils.changeAppLanguage(this, LanguageUtils.getSupportLanguage(""));
    }

    public String getAppLanguage() {
        return Locale.getDefault().getLanguage();
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(LogUtils.getInstance().getTAG());
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(ConfigureUtils.isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(ConfigureUtils.HTTP_TIME_OUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(ConfigureUtils.HTTP_TIME_OUT, TimeUnit.MILLISECONDS);
        builder.connectTimeout(ConfigureUtils.HTTP_TIME_OUT, TimeUnit.MILLISECONDS);
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));

        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setRetryCount(2);
    }

    public void exitApp() {
        if (ActivityStack.size() < 1) {
            return;
        }
        for (BaseActivity activity : ActivityStack.getActivityList()) {
            activity.closeActivity();
        }
    }

    public void restartApp() {
        exitApp();
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void postTaskInUIThread(Runnable runable) {
        this.handler.post(runable);
    }

    public void postTaskInUIThread(Runnable runable, int delayMillis) {
        this.handler.postDelayed(runable, delayMillis);
    }

    public void postTaskInUIThread(Runnable runable, long delayMillis) {
        this.handler.postDelayed(runable, delayMillis);
    }

    public void postTaskInUIThreadAtFront(Runnable runable) {
        this.handler.postAtFrontOfQueue(runable);
    }

    public Handler getMainLoopHandler() {
        return this.handler;
    }

    public Typeface getFontTypeface() {
        return null;
    }

    public IHeaderView getRefreshHeaderView() {
        return new ProgressLayout(this);
    }

    public static void showToast(String msg) {
        if (getInstance() == null) {
            return;
        }

        BaseToast.show(getInstance(), -1, msg);
    }
}
