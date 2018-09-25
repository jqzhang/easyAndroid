package com.brothers.base.ui.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.brothers.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FlowLayout extends LinearLayout {

    public interface OnTagClickListener<D> {
        void onTagClick(int position, D data, boolean isChecked);
    }

    private List<View> mViewList = new ArrayList<>();

    private OnTagClickListener mOnTagClickListener;

    private int mLeft, mRight, mTop, mBottom;

    private LinkedHashMap<View, Position> mViewMap = new LinkedHashMap<View, Position>();

    private HashSet<Integer> mSelectedPositionList = new HashSet<Integer>();

    private int mMaxSelectedCount = 0;

    /**
     * 每个view上下的间距
     */
    private final int dividerLine = 15;
    /**
     * 每个view左右的间距
     */
    private final int dividerCol = 15;

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context);
    }

    public FlowLayout(Context context, int horizontalSpacing, int verticalSpacing) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mCount = getChildCount();

        // int mX = 0;
        // int mY = 0;
        mLeft = getPaddingLeft();
        mRight = 0;
        mTop = getPaddingTop();
        mBottom = getPaddingBottom();

        int j = 0;

        for (int i = 0; i < mCount; i++) {
            final View child = getChildAt(i);

            child.measure(500, 100);
//            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            // 此处增加onlayout中的换行判断，用于计算所需的高度
            int childw = child.getMeasuredWidth();
            int childh = child.getMeasuredHeight();

            mRight += childw; // 将每次子控件宽度进行统计叠加，如果大于设定的宽度则需要换行，高度即Top坐标也需重新设置

            Position position = new Position();
            mLeft = getPosition(i - j, i);
            mRight = mLeft + child.getMeasuredWidth();
            if (mRight >= (mWidth - getPaddingRight())) {
                j = i;
                mLeft = getPaddingLeft();
                mRight = mLeft + child.getMeasuredWidth();
                mTop += childh + dividerLine;
                // PS：如果发现高度还是有问题就得自己再细调了
            }
            mBottom = mTop + child.getMeasuredHeight();
            // mY = mTop; //每次的高度必须记录 否则控件会叠加到一起
            // mX = mRight;
            position.left = mLeft;
            position.top = mTop;
            position.right = mRight;
            position.bottom = mBottom;
            mViewMap.put(child, position);
        }
        setMeasuredDimension(mWidth, mBottom + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Position pos = mViewMap.get(child);
            if (pos != null) {
                child.layout(pos.left, pos.top, pos.right, pos.bottom);
            } else {
                LogUtils.getInstance().i("Position is null !!!");
            }
        }
    }

    private class Position {
        int left, top, right, bottom;
    }

    public void setSelectView(int position) {
        if (position > mViewList.size()) {
            return;
        }

        mViewList.get(position).performClick();
    }

    private FlowAdapter mFlowAdapter;

    public int getViewPosition(Object obj) {
        if (null == mFlowAdapter) {
            return -1;
        }

        return mFlowAdapter.indexOf(obj);
    }

    private int getPosition(int IndexInRow, int childIndex) {
        if (IndexInRow > 0) {
            return getPosition(IndexInRow - 1, childIndex - 1) + getChildAt(childIndex - 1).getMeasuredWidth() + dividerCol;
        }
        return getPaddingLeft();
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

    public void setAdapter(final FlowAdapter adapter) {
        removeAllViews();
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
                                for (Map.Entry<View, Position> entry : mViewMap.entrySet()) {
                                    if (view != entry.getKey()) {
                                        entry.getKey().setSelected(false);
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
            mViewList.add(view);
        }
    }
}
