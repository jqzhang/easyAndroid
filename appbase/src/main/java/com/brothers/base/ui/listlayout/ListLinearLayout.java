package com.brothers.base.ui.listlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

public class ListLinearLayout extends LinearLayout {

    protected LinkedList<View> mViewLinkedList = new LinkedList<>();

    protected HashSet<Integer> mSelectedPositionList = new HashSet<Integer>();

    protected int mMaxSelectedCount = 0;

    public interface OnTagClickListener<D> {
        void onTagClick(int position, D data, boolean isChecked);
    }

    protected OnTagClickListener mOnTagClickListener;

    public ListLinearLayout(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public ListLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public ListLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    public void setMaxSelectedCount(int count) {
        mMaxSelectedCount = count;
    }

    private boolean isSingleChecked() {
        return (1 == mMaxSelectedCount);
    }

    public void setOnTagClickListener(OnTagClickListener listener) {
        mOnTagClickListener = listener;
    }

    public void setAdapter(final ListLinearLayoutAdapter adapter) {
        if (null == adapter || adapter.getCount() < 1) {
            return;
        }

        for (int i = 0; i < adapter.getCount(); i++) {
            final int position = i;
            final View view = adapter.getView(this, position, adapter.getItem(position));

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((mMaxSelectedCount > 0 && mSelectedPositionList.size() < mMaxSelectedCount) || view.isSelected() || isSingleChecked()) {
                        view.setSelected(!view.isSelected());
                        if (view.isSelected()) {
                            // 单选
                            if (isSingleChecked()) {
                                mSelectedPositionList.clear();
                                for (View itemView : mViewLinkedList) {
                                    if (view != itemView) {
                                        itemView.setSelected(false);
                                    }
                                }
                            }
                            mSelectedPositionList.add(position);
                        } else if (!mSelectedPositionList.isEmpty() && mSelectedPositionList.contains(position)) {
                            mSelectedPositionList.remove(position);
                        }
                    }
                    if (mOnTagClickListener != null) {
                        mOnTagClickListener.onTagClick(position, adapter.getItem(position), view.isSelected());
                    }
                }
            });
            addView(view);
        }
    }
}
