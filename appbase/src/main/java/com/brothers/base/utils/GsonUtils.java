package com.brothers.base.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhangjiqun on 2017/12/10.
 */

public class GsonUtils {

    public static class HOLDER {
        public final static Gson INSTANCE = new Gson();
    }

    private static Gson getInstance() {
        return HOLDER.INSTANCE;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return getInstance().fromJson(json, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Type classOfT) {
        try {
            return getInstance().fromJson(json, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(Object src) {
        try {
            return getInstance().toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
