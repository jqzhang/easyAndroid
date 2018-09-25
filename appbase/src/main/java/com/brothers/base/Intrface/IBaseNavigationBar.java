package com.brothers.base.Intrface;

import android.view.View;

public interface IBaseNavigationBar {

    View getView();

    void showTitleBar(boolean show);

    void setAppTitle(String title);

    void setRightTitle(String title);

    void addBarRightView(View view);

    void setBackgroundColor(int colorId);
}
