package com.brothers.base.ui.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.brothers.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muuky on 2017/8/9.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> mData = new ArrayList<>();

    protected OnItemClickListener mItemClickListener;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    protected List<T> getList() {
        return mData;
    }

    protected T getItemData(int position) {
        if (null == getList()) {
            LogUtils.getInstance().e("List is empty!!!");
            return null;
        }

        if (position >= getItemCount()) {
            LogUtils.getInstance().e(String.format("IndexOutOfBoundsException position is %d, Size is %d", position, getItemCount()) );
            return null;
        }
        return getList().get(position);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        T data = null;
        if (getList()!=null && getList().size() > position) {
            data = getList().get(position);
        }
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.onClick(v, getItemData(position));
            }
        });
        holder.updateView(holder, data, position);
    }

    @Override
    public int getItemCount() {
        if (null == getList()) {
            return 0;
        }
        return getList().size();
    }

    public void setData(List<T> list) {
        if (null == list || list.isEmpty()) {
            return;
        }
        getList().addAll(list);
        notifyDataSetChanged();
    }

    public void setData(T data) {
        if (null == data) {
            return;
        }
        getList().add(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        getList().clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    protected void onItemClick(T data, int position) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(data, position);
        }
    }

    protected class DefaultViewHolder extends BaseViewHolder {

        public DefaultViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void updateView(BaseViewHolder vh, Object data, int position) {

        }
    }
}
