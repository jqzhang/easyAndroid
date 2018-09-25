package com.brothers.base.utils;

import android.util.Log;

import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.utils.IOUtils;
import com.lzy.okgo.utils.OkLogger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

/**
 * Created by zhangjiqun on 2018/2/28.
 */

public class LogUtils {

    public static class HOLDER {
        public final static LogUtils INSTANCE = new LogUtils();
    }

    private String TAG = "AppBase";

    public static int logLevel = Log.VERBOSE;

    public static boolean isDebug = ConfigureUtils.isDebug();

    public LogUtils() {
    }

    public LogUtils(String tag) {
        this.TAG = tag;
    }

    public void setTag(String tag) {
        this.TAG = tag;
    }

    public static LogUtils getInstance() {
        return HOLDER.INSTANCE;
    }

    public String getTAG() {
        return TAG;
    }

    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }

            return "[" + Thread.currentThread().getId() + ": " + st.getFileName() + ":" + st.getLineNumber() + "]";
        }

        return null;
    }

    public void info(Object str) {
        if (logLevel <= Log.INFO) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            Log.i(TAG, ls);
        }
    }

    public void i(Object str) {
        if (isDebug) {
            info(str);
        }
    }

    public void verbose(Object str) {
        if (logLevel <= Log.VERBOSE) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            Log.v(TAG, ls);
        }
    }

    public void v(Object str) {
        if (isDebug) {
            verbose(str);
        }
    }

    public void warn(Object str) {
        if (logLevel <= Log.WARN) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            Log.w(TAG, ls);
        }
    }

    public void w(Object str) {
        if (isDebug) {
            warn(str);
        }
    }

    public void error(Object str) {
        if (logLevel <= Log.ERROR) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            Log.e(TAG, ls);
        }
    }

    public void error(Exception ex) {
        if (logLevel <= Log.ERROR) {
            StringBuffer sb = new StringBuffer();
            String name = getFunctionName();
            StackTraceElement[] sts = ex.getStackTrace();

            if (name != null) {
                sb.append(name + " - " + ex + "\r\n");
            } else {
                sb.append(ex + "\r\n");
            }

            if (sts != null && sts.length > 0) {
                for (StackTraceElement st : sts) {
                    if (st != null) {
                        sb.append("[ " + st.getFileName() + ":" + st.getLineNumber() + " ]\r\n");
                    }
                }
            }

            Log.e(TAG, sb.toString());
        }
    }

    public void e(Object str) {
        if (isDebug) {
            error(str);
        }
    }

    public void e(Exception ex) {
        if (isDebug) {
            error(ex);
        }
    }

    public void debug(Object str) {
        if (logLevel <= Log.DEBUG) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            Log.d(TAG, ls);
        }
    }

    public void d(Object str) {
        if (isDebug) {
            debug(str);
        }
    }
}
