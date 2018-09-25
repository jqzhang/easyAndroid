package com.brothers.peten.base;

import com.brothers.appcommon.base.CommonData;
import com.brothers.appcommon.base.CommonResult;

public class AppResult extends CommonResult {

    public Pager pager;

    @Override
    public boolean isSucceed() {
        return "0".equals(code);
    }

    public boolean isNoLogin() {
        return "104".equals(code);
    }

    public static class Pager extends CommonData {
        public int pageNo;
        public int pageSize;
        public int rowCount;
        public int skip;
        public int totalPages;
    }
}
