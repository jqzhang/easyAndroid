package com.brothers.base.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import com.brothers.base.ui.widget.AppTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.brothers.base.R;
import com.brothers.base.utils.StringUtils;
import com.brothers.base.utils.StringUtils;

/**
 * Created by zhangjiqun on 2018/3/1.
 */

public class BaseDialog extends Dialog {

    public interface CommonDialogOnClickListener {
        void onConfirm(int code);

        void onCancel(int code);

        void onIKnow(int code);
    }

    private class SHOW_TYPE {
        public final static int IKONW = 0;
        public final static int TOW_BTN = 1;
    }

    AppTextView tvTitle;
    AppTextView tvContent;
    AppCompatButton btnIKnow;
    AppCompatButton btnCancel;
    AppCompatButton btnConfirm;
    LinearLayout llTowBtn;

    private String mTitle;
    private String mContent;
    private String mConfirmBtnText;
    private String mIKnowBtnText;
    private String mCancelBtnText;
    private int mBtnType;
    private int mCode;

    private CommonDialogOnClickListener mCommonDialogOnClickListener;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.CommonDialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);

        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        btnIKnow = findViewById(R.id.btnIKnow);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);
        llTowBtn = findViewById(R.id.llTowBtn);

        tvTitle.setText(StringUtils.isEmpty(mTitle) ? getContext().getString(R.string.tips) : mTitle);
        tvContent.setText(mContent);

        if (SHOW_TYPE.IKONW == mBtnType) {
            llTowBtn.setVisibility(View.GONE);
            btnIKnow.setVisibility(View.VISIBLE);
        } else {
            llTowBtn.setVisibility(View.VISIBLE);
            btnIKnow.setVisibility(View.GONE);
        }

        btnIKnow.setText(StringUtils.isEmpty(mIKnowBtnText) ? getContext().getString(R.string.i_know) : mIKnowBtnText);
        btnConfirm.setText(StringUtils.isEmpty(mConfirmBtnText) ? getContext().getString(R.string.confirm) : mConfirmBtnText);
        btnCancel.setText(StringUtils.isEmpty(mCancelBtnText) ? getContext().getString(R.string.cancel) : mCancelBtnText);

        btnIKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCommonDialogOnClickListener != null) {
                    mCommonDialogOnClickListener.onIKnow(mCode);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCommonDialogOnClickListener != null) {
                    mCommonDialogOnClickListener.onCancel(mCode);
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCommonDialogOnClickListener != null) {
                    mCommonDialogOnClickListener.onConfirm(mCode);
                }
            }
        });
    }

    public BaseDialog setListener(CommonDialogOnClickListener listener) {
        mCommonDialogOnClickListener = listener;
        return this;
    }

    public BaseDialog setTitle(String title) {
        mTitle = title;
        if (tvTitle != null) {
            tvTitle.setText(StringUtils.isEmpty(mTitle) ? getContext().getString(R.string.tips) : mTitle);
        }
        return this;
    }

    public BaseDialog setContent(String content) {
        mContent = content;
        if (tvContent != null) {
            tvContent.setText(mContent);
        }
        return this;
    }

    public void showIKnow() {
        showIKnow("");
    }

    public void showIKnow(String btnText) {
        if (llTowBtn != null && btnIKnow != null) {
            llTowBtn.setVisibility(View.GONE);
            btnIKnow.setVisibility(View.VISIBLE);
            btnIKnow.setText(StringUtils.isEmpty(btnText) ? getContext().getString(R.string.i_know) : btnText);
        }
        mIKnowBtnText = btnText;
        mBtnType = SHOW_TYPE.IKONW;
        show();
    }

    public void showTowButton(String confirm, String cancel) {
        mBtnType = SHOW_TYPE.TOW_BTN;
        mConfirmBtnText = confirm;
        mCancelBtnText = cancel;
        if (llTowBtn != null && btnIKnow != null) {
            llTowBtn.setVisibility(View.VISIBLE);
            btnIKnow.setVisibility(View.GONE);
            btnConfirm.setText(StringUtils.isEmpty(mConfirmBtnText) ? getContext().getString(R.string.confirm) : mConfirmBtnText);
            btnCancel.setText(StringUtils.isEmpty(mCancelBtnText) ? getContext().getString(R.string.cancel) : mCancelBtnText);
        }

        show();
    }

    public void showTowButton(String confirm, String cancel, int code) {
        mBtnType = SHOW_TYPE.TOW_BTN;
        mConfirmBtnText = confirm;
        mCancelBtnText = cancel;
        mCode = code;
        if (llTowBtn != null && btnIKnow != null) {
            llTowBtn.setVisibility(View.VISIBLE);
            btnIKnow.setVisibility(View.GONE);
            btnConfirm.setText(StringUtils.isEmpty(mConfirmBtnText) ? getContext().getString(R.string.confirm) : mConfirmBtnText);
            btnCancel.setText(StringUtils.isEmpty(mCancelBtnText) ? getContext().getString(R.string.cancel) : mCancelBtnText);
        }

        show();
    }

    public void showTowButton() {
        showTowButton(getContext().getString(R.string.confirm), getContext().getString(R.string.cancel));
    }

    public void showTowButton(int code) {
        showTowButton(getContext().getString(R.string.confirm), getContext().getString(R.string.cancel), code);
    }
}
