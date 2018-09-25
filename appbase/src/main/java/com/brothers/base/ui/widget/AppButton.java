package com.brothers.base.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.brothers.base.ui.base.BaseApplication;

/**
 * Created by zhangjiqun on 2018/3/15.
 */

public class AppButton extends AppCompatButton {
    public AppButton(Context context) {
        this(context, null);
    }

    public AppButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Typeface tf = BaseApplication.getInstance().getFontTypeface();
        if (tf != null) {
            if (null == getTypeface()) {
                setTypeface(Typeface.create(tf, defStyleAttr));
            } else {
                setTypeface(Typeface.create(tf, getTypeface().getStyle()));
            }
        }
    }
}
