package com.brothers.peten.common;


import com.brothers.peten.BuildConfig;

public class Urls {

    private static String HOST = BuildConfig.HOST + "v1/cook/";

    public static String getMobApiKey() {
        return BuildConfig.MOB_API_KEY;
    }

    public static String getCookCategoryUrl() {
        return HOST + "category/query";
    }

    public static String getCookTagUrl() {
        return HOST + "menu/search";
    }

    public static String getMenuQueryUrl() {
//        return "http://apicloud.mob.com/v1/cook/" + "menu/query";
        return HOST + "menu/query";
    }

    public static String getUploadFileUrl() {
        return "";
    }

    public static String getSendSmsCodeUrl() {
        return "";
    }
}
