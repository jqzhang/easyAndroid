package com.brothers.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.brothers.base.ui.base.BaseApplication;

/**
 * Created by zhangjiqun on 2017/12/26.
 */

public class SPUtils {

    public static String TAG = "APP";

    private static SharedPreferences mSharedPreferences;

    public static class HOLDER {
        private static final SPUtils INSTANCE = new SPUtils();
    }

    public static final SPUtils init(Context context) {
        mSharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return HOLDER.INSTANCE;
    }

    public static boolean hasInited() {
        return mSharedPreferences != null;
    }

    private static SharedPreferences getSharedPreferences() {
        if (null == mSharedPreferences) {
            mSharedPreferences = BaseApplication.getInstance().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public void setTag(String tag) {
        TAG = tag;
    }

    public static void saveString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).commit();
    }

    public static void saveInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static void saveBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).commit();
    }

    public static void clear() {
        getSharedPreferences().edit().clear().commit();
    }

    public static String getStringValue(String key) {
        return getSharedPreferences().getString(key, null);
    }

    public static boolean getBooleaValue(String key) {
        return getBooleaValue(key, false);
    }

    public static boolean getBooleaValue(String key, boolean value) {
        return getSharedPreferences().getBoolean(key, value);
    }

    public static void save(Context context, String key, String value) {
        getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getValue(String key) {
        return getSharedPreferences().getString(key, null);
    }
}
