package com.brothers.base.utils;

import android.content.Context;

/**
 * DeviceUtils
 * Created by iWgang on 16/6/19.
 * https://github.com/iwgang/CountdownView
 */
public final class DeviceUtils {

    public static int dp2px(Context context, float dpValue) {
        if (dpValue <= 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static float sp2px(Context context, float spValue) {
        if (spValue <= 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }
}
