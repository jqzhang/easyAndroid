package com.brothers.base.utils;

import java.text.SimpleDateFormat;

/**
 * Created by zhangjiqun on 2018/2/11.
 */

public class DateFormatUtils {
    public interface TYPE {
        public String TYPE1 = "yyyy-MM-dd HH:mm:ss";
    }

    public static String getFormatDate(long time) {
        return getFormatDate(time, TYPE.TYPE1);
    }

    public static String getFormatDate(long time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat (format);
        return simpleDateFormat.format(time * 1000);
    }
}
