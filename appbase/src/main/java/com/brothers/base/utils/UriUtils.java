package com.brothers.base.utils;

import android.net.Uri;

import com.brothers.base.Intrface.NoProguard;
import com.brothers.base.Intrface.NoProguard;

import java.util.Map;

/**
 * Created by zhangjiqun on 2018/2/28.
 */

public class UriUtils implements NoProguard {

    public static Uri parse(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        return Uri.parse(url);
    }

    /**
     * 返回 key=value&key1=value1
     *
     * @param params
     * @return
     */
    public static String map2String(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();

        if (params != null && false == params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
        }

        String result = sb.toString();

        if (StringUtils.notEmpty(result)) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}
