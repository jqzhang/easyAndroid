package com.brothers.peten.base;

import com.brothers.appcommon.base.CommonActivity;
import com.brothers.peten.common.SoftKeyBoardListener;

public abstract class AppBaseActivity extends CommonActivity {

    protected void setOnSoftKeyBoardListener() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                onKeyBoardShow(height);
            }

            @Override
            public void keyBoardHide(int height) {
                onKeyBoardHide(height);
            }
        });
    }

    protected void onKeyBoardShow(int height) {

    }

    protected void onKeyBoardHide(int height) {

    }
}
