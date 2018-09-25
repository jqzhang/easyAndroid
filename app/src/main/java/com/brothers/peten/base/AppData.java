package com.brothers.peten.base;

import android.content.Context;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.brothers.appcommon.base.CommonData;
import com.brothers.base.utils.GsonUtils;

import java.lang.reflect.Type;

public class AppData extends CommonData implements SerializationService {

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return GsonUtils.fromJson(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return GsonUtils.toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return GsonUtils.fromJson(input, clazz);
    }

    @Override
    public void init(Context context) {

    }
}
