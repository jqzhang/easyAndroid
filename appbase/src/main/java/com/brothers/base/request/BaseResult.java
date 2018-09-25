package com.brothers.base.request;

import com.brothers.base.Intrface.NoProguard;
import com.brothers.base.utils.StringUtils;

import java.util.Map;

/**
 * Created by zhangjiqun on 2017/12/10.
 */

public abstract class BaseResult extends BaseData {
    public int status;
    public String code;
    public String message;

    public abstract boolean isSucceed();

    public boolean hasMore() {
        return false;
    }

    public String getMessage() {
        return message;
    }

}
