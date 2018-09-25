package com.brothers.base.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import com.brothers.base.ui.widget.AppTextView;

import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.brothers.base.ui.base.BaseApplication;
import com.brothers.base.utils.ValueOfUtil;

import java.text.DecimalFormat;

/**
 * Created by zhangjiqun on 2018/3/15.
 */

public class AppTextView extends AppCompatTextView {

    public AppTextView(Context context) {
        this(context, null);
    }

    public AppTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Typeface tf = BaseApplication.getInstance().getFontTypeface();
        if (tf != null) {
            if (null == getTypeface()) {
                setTypeface(Typeface.create(tf, defStyleAttr));
            } else {
                setTypeface(Typeface.create(tf, getTypeface().getStyle()));
            }
        }
        setLineSpacing(getLineSpacingExtra(), 1.2f);
    }

    public void setMoney(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        String str = decimalFormat.format(ValueOfUtil.toDouble(value, 0d));

        setText(str);
    }
}
