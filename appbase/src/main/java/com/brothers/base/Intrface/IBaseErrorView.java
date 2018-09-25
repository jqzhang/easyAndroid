package com.brothers.base.Intrface;

import android.view.View;

public interface IBaseErrorView {
    View getView();

    void setRetryText(String str);

    void showError(String title, String content);

    void showNoData(String title, String content);

    void hide();

    void retry();
}
