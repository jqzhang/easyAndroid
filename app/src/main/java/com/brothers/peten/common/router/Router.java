package com.brothers.peten.common.router;

import com.alibaba.android.arouter.launcher.ARouter;
import com.brothers.base.utils.LogUtils;
import com.brothers.base.utils.StringUtils;
import com.brothers.base.utils.UriUtils;

import java.util.Map;

/**
 * Created by zhangjiqun on 2018/1/4.
 */

public class Router {

    public static Router getInstance() {
        return HOLDER.INSTANCE;
    }

    public void start(final String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();

        sb.append(url);
        String param = UriUtils.map2String(params);
        if (StringUtils.notEmpty(param)) {
            sb.append("?");
            sb.append(param);
        }
        String router = sb.toString();

        LogUtils.getInstance().e(router);
        ARouter.getInstance().build(UriUtils.parse(PATH.HEADER + router)).navigation();
    }

    /**
     * 带参数启动
     *
     * @param baseUrl
     */
    public void start(final String baseUrl) {
        start(baseUrl, null);
    }

    public static class HOLDER {
        public final static Router INSTANCE = new Router();
    }

    public static class PATH {
        public final static String FLAG = "easyAndroid";
        public final static String HEADER = "http://" + FLAG;
        public final static String TAG_LIST_ACTIVITY = "/tag/list";
        public final static String CATEGORY_LIST_ACTIVITY = "/category/list";
        public final static String DETAIL_ACTIVITY = "/detail/list";
        public final static String MAIN_ACTIVITY = "/main/activity";
        public final static String SPLASH_ACTIVITY = "/splash/activity";
        public final static String BASE_WEBVIEW_ACTIVITY = "/base/webview/activity";
    }

    public static class OBJ {
        public final static String COOK_CHILDS = "/cook/childs";
    }
}
