package com.brothers.base.ui.flowlayout;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FlowAdapter<T> {

    private List<T> mTagDatas;

    private OnDataChangedListener mOnDataChangedListener;

    public FlowAdapter(List<T> datas) {
        mTagDatas = datas;
    }

    @Deprecated
    public FlowAdapter(T[] datas) {
        mTagDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onChanged();
        }
    }

    public T getItem(int position) {
        if (null == mTagDatas || mTagDatas.isEmpty()) {
            return null;
        }
        return mTagDatas.get(position);
    }

    public int indexOf(T t) {
        if (null == mTagDatas) {
            return -1;
        }
        return mTagDatas.indexOf(t);
    }

    public abstract View getView(FlowLayout parent, int position, T t);
}
