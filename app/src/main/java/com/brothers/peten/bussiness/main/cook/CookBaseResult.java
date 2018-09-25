package com.brothers.peten.bussiness.main.cook;

import com.brothers.peten.base.AppResult;

public class CookBaseResult extends AppResult {

    public int retCode;

    @Override
    public boolean isSucceed() {
        return 200 == retCode;
    }


}
