package com.brothers.base.utils;

/**
 * Created by zhangjiqun on 2017/12/10.
 */

public class StringUtils {
    public static boolean isEmpty(String str) {
        if (null == str) {
            return true;
        }
        if (str.trim().length() < 1) {
            return true;
        }
        if ("null".equalsIgnoreCase(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    public static String formatNum(int time) {
        return time < 10 ? "0" + time : String.valueOf(time);
    }

    public static String formatMillisecond(int millisecond) {
        String retMillisecondStr;

        if (millisecond > 99) {
            retMillisecondStr = String.valueOf(millisecond / 10);
        } else if (millisecond <= 9) {
            retMillisecondStr = "0" + millisecond;
        } else {
            retMillisecondStr = String.valueOf(millisecond);
        }

        return retMillisecondStr;
    }
}
