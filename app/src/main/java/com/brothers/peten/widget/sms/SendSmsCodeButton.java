package com.brothers.peten.widget.sms;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.brothers.appcommon.intrface.ICommonCallback;
import com.brothers.base.Intrface.NoProguard;
import com.brothers.base.request.BaseRequest;
import com.brothers.base.ui.widget.AppTextView;
import com.brothers.base.utils.StringUtils;
import com.brothers.peten.R;
import com.brothers.peten.bussiness.AppApplication;
import com.brothers.peten.common.Urls;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjiqun on 2017/12/13.
 */

public class SendSmsCodeButton extends AppTextView implements View.OnClickListener {

    /**
     * 发送短信成功后倒计时分钟数
     */
    private final int MINUTE = 3;
    private Context mContext;
    private String mPhoneNum;
    private String mType;
    private ISendSmsCodeClickListenner mISendSmsCodeClickListenner;
    private CountDownTimer timer = new CountDownTimer(MINUTE * 60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            setEnabled(false);
            setText((millisUntilFinished / 1000) + mContext.getString(R.string.seconds));
        }

        @Override
        public void onFinish() {
            setEnabled(true);
            setText(mContext.getString(R.string.send_sms_code));
        }
    };

    public SendSmsCodeButton(Context context) {
        super(context);
        init(context);
    }

    public SendSmsCodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SendSmsCodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setGravity(Gravity.CENTER);
        setEnabled(true);
        setText(mContext.getString(R.string.send_sms_code));
        setOnClickListener(this);
    }

    public void setClickListenner(ISendSmsCodeClickListenner listenner) {
        mISendSmsCodeClickListenner = listenner;
    }

    public void setPhoneNum(String phoneNum) {
        mPhoneNum = phoneNum;
    }

    public void setType(String type) {
        mType = type;
    }

    private void sendSmscodeRequest() {
        if (StringUtils.isEmpty(mPhoneNum)) {
            AppApplication.showToast(mContext.getString(R.string.input_phone_number));
            return;
        }
        if (!isEnabled()) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", mPhoneNum);
        map.put("type", mType);
        new BaseRequest<SmsResult>(mContext, SmsResult.class)
                .showLoading(true)
                .setUrl(Urls.getSendSmsCodeUrl())
                .setParams(map)
                .setCallback(new ICommonCallback<SmsResult>() {
                    @Override
                    public void onSuccess(SmsResult data) {
                        AppApplication.getInstance().showToast(data.getMessage());
                        if (data.isSucceed()) {
                            timer.start();
                        } else {
                            setEnabled(true);
                        }
                    }

                    @Override
                    public void onError(SmsResult data) {
                        AppApplication.getInstance().showToast(data.getMessage());
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish(boolean isRefresh) {

                    }
                }).postBody();
    }

    @Override
    public void onClick(View v) {
        if (mISendSmsCodeClickListenner != null) {
            mISendSmsCodeClickListenner.onClick();
        }
        sendSmscodeRequest();
    }

    public interface ISendSmsCodeClickListenner {
        void onClick();
    }

    public static class TYPE implements NoProguard {
        public final static String SIGNUP = "0";
        public final static String FORGET_PWD = "1";
    }
}
