package com.brothers.peten.widget.statusbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

// Encapsulate MIUI status bar's function
public class StatusBar {
    private final String miuiVersion;
    private String TAG = getClass().getSimpleName();
    private Activity mActivity;
    private boolean mDarkMode = false;
    private int mDarkModeValue = 0;
    private Method mDarkModeMethod = null;
    private boolean mHasSet = false;

    public StatusBar(Activity activity) {
        mActivity = activity;
        // MIUI版本
        miuiVersion = getSystemProperties("ro.miui.ui.version.name");
        if (mActivity != null) {
            Class<? extends Window> clazz = activity.getWindow().getClass();
            try {
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                mDarkModeValue = field.getInt(layoutParams);
                mDarkModeMethod = clazz.getMethod("setExtraFlags", int.class, int.class);
            } catch (Exception e) {
                mDarkModeValue = 0;
                mDarkModeMethod = null;
            }
        }
    }

    public void setStatusBarDarkMode(boolean darkmode) {
        if (mDarkModeMethod == null) {
            return;
        }
        if (darkmode == mDarkMode && mHasSet) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !"V8".equalsIgnoreCase(miuiVersion)) {
            Window window = mActivity.getWindow();
            if (darkmode) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                int flag = window.getDecorView().getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                window.getDecorView().setSystemUiVisibility(flag);
            }
        } else {
            try {
                mDarkModeMethod.invoke(mActivity.getWindow(), darkmode ? mDarkModeValue : 0,
                        mDarkModeValue);
                mDarkMode = darkmode;
                mHasSet = true;
            } catch (Exception e) {
                //Ignore exception
            }
        }
    }

    private String getSystemProperties(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }

    public boolean darkMode() {
        return mDarkMode;
    }
}
