package com.brothers.base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.brothers.base.BuildConfig;
import com.brothers.base.ui.base.BaseApplication;

/**
 * Created by zhangjiqun on 2017/12/10.
 */

public class ConfigureUtils {

    public final static long HTTP_TIME_OUT = 30 * 1000;

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
