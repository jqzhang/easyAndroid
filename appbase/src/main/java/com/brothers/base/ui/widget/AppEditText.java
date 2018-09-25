package com.brothers.base.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.brothers.base.ui.base.BaseApplication;

/**
 * Created by zhangjiqun on 2018/3/15.
 */

public class AppEditText extends AppCompatEditText {
    public AppEditText(Context context) {
        this(context, null);
    }

    public AppEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
