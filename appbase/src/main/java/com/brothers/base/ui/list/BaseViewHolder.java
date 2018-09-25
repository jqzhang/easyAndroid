package com.brothers.base.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.brothers.base.R;

import java.util.Date;

import butterknife.ButterKnife;

/**
 * Created by Muuky on 2017/8/9.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected abstract void updateView(BaseViewHolder vh, T data, int position);

    private Context mContext;

    private View mRootView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mRootView = itemView;
        mContext = itemView.getContext();

        if (enableClickItemAnimal()) {
            TypedValue typedValue = new TypedValue();
            itemView.getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
            mRootView.setBackgroundResource(typedValue.resourceId);
        }
    }

    public void onClick(View view, T data) {
    }

    public View getRootView() {
        return mRootView;
    }

    protected Context getContext() {
        return mContext;
    }

    /**
     * 是否打开ItemView 的点击动画（水波纹效果）
     * @return
     */
    protected boolean enableClickItemAnimal() {
        return false;
    }
}
