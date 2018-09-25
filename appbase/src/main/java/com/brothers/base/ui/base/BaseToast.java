package com.brothers.base.ui.base;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brothers.base.R;
import com.brothers.base.utils.InflaterService;
import com.brothers.base.utils.StringUtils;
import com.brothers.base.utils.InflaterService;
import com.brothers.base.utils.StringUtils;

public class BaseToast {
    // private final static Object mLock = new Object();
    // private static final String TAG = "BaseToast";

    public static final int TOAST_SUCCESS = 0;
    public static final int TOAST_WARNING = 1;

    public static Toast toast = null;
    private static int icon[] = {R.drawable.toast_ready, R.drawable.toast_warning};

    protected static int getDurationTimeInMills(String string) {
        if (string == null) {
            return 0;
        }

        // 提示时间有点短，比iOS增加10%时长
        return (int) (Math.min(string.length() * 0.09 * 1.1 + 0.5, 5.0) * 1000);
    }

    public static void show(Context context, int iconId, String string) {
        show(context, iconId, string, getDurationTimeInMills(string));
    }

    public static void show(Context context, int iconId, String string, int duration) {
        if (context == null) {
            return;
        }

        if (StringUtils.isEmpty(string)) {
            return;
        }

        if (toast == null) {
            if (context instanceof Activity) {
                toast = new Toast(context.getApplicationContext());
            } else {
                toast = new Toast(context);
            }
        }

        View v = InflaterService.getInstance().inflate(context.getApplicationContext(), R.layout.app_toast_layout, null);
        ImageView ico = (ImageView) v.findViewById(R.id.img_toast);
        if (iconId >= 0 && iconId < icon.length) {
            ico.setVisibility(View.VISIBLE);
            ico.setBackgroundResource(icon[iconId]);
        } else {
            ico.setVisibility(View.GONE);
        }

        TextView txt = (TextView) v.findViewById(R.id.text_toast);

        int marginToTop = context.getResources().getDimensionPixelSize(R.dimen.ds120);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, marginToTop);
        txt.setText(string);
        toast.setView(v);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showOnTitle(Context context, int iconId, String string) {
        if (context == null) {
            return;
        }
        if (toast == null) {
            if (context instanceof Activity) {
                toast = new Toast(context.getApplicationContext());
            } else {
                toast = new Toast(context);
            }
        }

        View v = InflaterService.getInstance().inflate(context.getApplicationContext(), R.layout.app_toast_layout, null);
        ImageView ico = (ImageView) v.findViewById(R.id.img_toast);
        if (iconId >= 0 && iconId < icon.length) {
            ico.setVisibility(View.VISIBLE);
            ico.setBackgroundResource(icon[iconId]);
        } else {
            ico.setVisibility(View.GONE);
        }

        TextView txt = (TextView) v.findViewById(R.id.text_toast);
        int topbarHeight = (int) context.getResources().getDimension(R.dimen.topbar_height);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, topbarHeight);
        txt.setText(string);
        toast.setView(v);
        toast.setDuration(getDurationTimeInMills(string));
        toast.show();
    }

    public static void show(Context context, int iconId, int strId) {
        if (context != null) {
            show(context, iconId, context.getResources().getString(strId));
        }
    }

    public static void showOnTitle(Context context, int iconId, int strId) {
        if (context != null) {
            showOnTitle(context, iconId, context.getResources().getString(strId));
        }
    }

    public static void dismiss() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
