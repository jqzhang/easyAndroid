package com.brothers.base.ui.widget.message;


import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import com.brothers.base.ui.widget.message.MessageView;

/**
 * 未读消息提示View,显示小红点或者带有数字的红点:
 * 数字一位,圆
 * 数字两位,圆角矩形,圆角是高度的一半
 * 数字超过两位,显示99+
 */
public class MessageViewUtils {
    public static void show(MessageView messageView, int num) {
        if (messageView == null) {
            return;
        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) messageView.getLayoutParams();
        DisplayMetrics dm = messageView.getResources().getDisplayMetrics();
        messageView.setVisibility(View.VISIBLE);
        if (num <= 0) {//圆点,设置默认宽高
            messageView.setStrokeWidth(0);
            messageView.setText("");

            lp.width = (int) (5 * dm.density);
            lp.height = (int) (5 * dm.density);
            messageView.setLayoutParams(lp);
        } else {
            lp.height = (int) (18 * dm.density);
            if (num > 0 && num < 10) {//圆
                lp.width = (int) (18 * dm.density);
                messageView.setText(num + "");
            } else if (num > 9 && num < 100) {//圆角矩形,圆角是高度的一半,设置默认padding
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                messageView.setPadding((int) (6 * dm.density), 0, (int) (6 * dm.density), 0);
                messageView.setText(num + "");
            } else {//数字超过两位,显示99+
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                messageView.setPadding((int) (6 * dm.density), 0, (int) (6 * dm.density), 0);
                messageView.setText("99+");
            }
            messageView.setLayoutParams(lp);
        }
    }

    public static void setSize(MessageView rtv, int size) {
        if (rtv == null) {
            return;
        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rtv.getLayoutParams();
        lp.width = size;
        lp.height = size;
        rtv.setLayoutParams(lp);
    }
}
