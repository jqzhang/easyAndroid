/**
 *
 */
package com.brothers.base.utils;

import java.math.BigDecimal;

/**
 * 安全的类型转换
 *
 * @author liukaixuan
 */
public abstract class ValueOfUtil {

    public static int toInt(String val, int defaultValue) {
        if (val == null || (val.length() == 0) || val.equals("null")) {
            return defaultValue;
        }

        try {
            val = val.trim();
            val = val.replace(",", "");
            return Integer.parseInt(val);
        } catch (Exception e) {
        }

        return defaultValue;
    }

    public static int toInt(String val, int radix, int defaultValue) {
        if (val == null || (val.length() == 0) || val.equals("null")) {
            return defaultValue;
        }

        try {
            val = val.trim();
            val = val.replace(",", "");
            return Integer.parseInt(val, radix);
        } catch (Exception e) {
        }

        return defaultValue;
    }

    public static long toLong(String val, long defaultValue) {
        if (val == null || (val.length() == 0) || val.equals("null")) {
            return defaultValue;
        }

        try {
            val = val.trim();
            val = val.replace(",", "");
            return Long.parseLong(val);
        } catch (Exception e) {
        }

        return defaultValue;
    }

    public static float toFloat(String val, float defaultValue) {
        if (val == null || (val.length() == 0) || val.equals("null")) {
            return defaultValue;
        }

        try {
            val = val.trim();
            val = val.replace(",", "");
            return Float.parseFloat(val);
        } catch (Exception e) {
        }

        return defaultValue;
    }

    public static double toDouble(String val, double defaultValue) {
        if (val == null || (val.length() == 0) || val.equals("null")) {
            return defaultValue;
        }

        try {
            val = val.trim();
            val = val.replace(",", "");
            return Double.parseDouble(val);
        } catch (Exception e) {
        }

        return defaultValue;
    }



    public static boolean toBoolean(String val, boolean defaultValue) {
        if (val == null) {
            return defaultValue;
        }

        try {
            return Boolean.parseBoolean(val);
        } catch (Exception e) {
        }

        return defaultValue;
    }

    public static float toFloat(String f) {
        return toFloat(f, 0.0f);
    }

    public static long toLong(String l) {
        return toLong(l, 0L);
    }

    public static int toInt(String num) {
        int ret = 0;
        if (num != null && num.contains(".")) {
            ret = (int) toFloat(num);
        } else {
            return toInt(num, 0);
        }

        return ret;
    }

    public static int toRadixInt(String i, int d) {
        return toInt(i, d, 0);
    }

    /**
     * double 比较
     *
     * @param d1
     * @param d2
     * @return 1 if d1 > d2; -1 if d1 < d2; 0 if d1 == d2;
     */
    public static int compareDouble(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.compareTo(bd2);
    }


    /**
     * float 比较
     *
     * @param d1
     * @param d2
     * @return 1 if d1 > d2; -1 if d1 < d2; 0 if d1 == d2;
     */
    public static int compareFloat(float d1, float d2) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.compareTo(bd2);
    }
}
