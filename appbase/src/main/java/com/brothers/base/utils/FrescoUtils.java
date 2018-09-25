package com.brothers.base.utils;

import android.net.Uri;

import com.brothers.base.ui.base.BaseApplication;

/**
 * Created by zhangjiqun on 2018/1/4.
 */

public class FrescoUtils {

    public static Uri getLocalUri(String path) {
        return UriUtils.parse("file://" + path);
    }

    public static Uri getDeawableUri(int resId) {
        return UriUtils.parse("res://" + BaseApplication.getInstance().getPackageName() + "/" + resId);
    }

    public static String getDeawablePath(int resId) {
        return ("res://" + BaseApplication.getInstance().getPackageName() + "/" + resId);
    }
}
