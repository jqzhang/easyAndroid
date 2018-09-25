package com.brothers.base.utils;

import android.support.v7.app.AppCompatActivity;

import com.brothers.base.Intrface.NoProguard;
import com.brothers.base.ui.base.BaseActivity;
import com.brothers.base.Intrface.NoProguard;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjiqun on 2018/3/1.
 */

public class ActivityStack implements NoProguard {

    private static List<BaseActivity> mActivityStack = new LinkedList<>();

    public static void push(BaseActivity activity) {
        if (null == activity) {
            return;
        }
        mActivityStack.add(activity);
    }

    public static void pop(AppCompatActivity activity) {
        if (null == activity) {
            return;
        }
        mActivityStack.remove(activity);
    }

    public static List<BaseActivity> getActivityList() {
        return mActivityStack;
    }

    public static int size() {
        return mActivityStack.size();
    }

    public static void closeAllBefore(BaseActivity activity) {
        if (mActivityStack.size() == 0) {
            return;
        }

        for (int i = 0 ; i < mActivityStack.size() ; i++) {
            BaseActivity hasActivity = mActivityStack.get(i);
            if (hasActivity == null || hasActivity.isDestroyed() || hasActivity.isFinishing()) {
                continue;
            }

            if (activity.getComponentName().getClassName().equals(hasActivity.getComponentName().getClassName())) {
                return;
            }

            hasActivity.closeActivity();
            LogUtils.getInstance().e("close " + hasActivity.getComponentName().getClassName());
        }
    }

    public static void printfAll() {
        if (mActivityStack.size() == 0) {
            return;
        }

        for (int i = 0 ; i < mActivityStack.size() ; i++) {
            BaseActivity hasActivity = mActivityStack.get(i);
            LogUtils.getInstance().e("contain " + hasActivity.getComponentName().getClassName());
        }
    }
}
