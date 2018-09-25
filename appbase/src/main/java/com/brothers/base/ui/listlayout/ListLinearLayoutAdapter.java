package com.brothers.base.ui.listlayout;

import android.view.View;

import com.brothers.base.ui.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ListLinearLayoutAdapter<T> {

    private List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangedListener;

    public ListLinearLayoutAdapter(List<T> datas) {
        mTagDatas = datas;
    }

    @Deprecated
    public ListLinearLayoutAdapter(T[] datas) {
        mTagDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    @Deprecated
    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses) {
            set.add(pos);
        }
    }


    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null)
            mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        if (null == mTagDatas || mTagDatas.isEmpty()) {
            return null;
        }
        return mTagDatas.get(position);
    }

    public abstract View getView(ListLinearLayout parent, int position, T t);
}
