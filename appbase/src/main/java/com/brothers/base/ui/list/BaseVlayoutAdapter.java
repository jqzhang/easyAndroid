package com.brothers.base.ui.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muuky on 2017/8/9.
 */

public abstract class BaseVlayoutAdapter<T> extends DelegateAdapter.Adapter<BaseViewHolder> {

    public abstract LayoutHelper onCreateLayoutHelper();

    private List<T> mData = new ArrayList<>();

    protected List<T> getList() {
        return mData;
    }

    protected T getItemData(int position) {
        if (getList() != null && getList().size() > position) {
            return getList().get(position);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.onClick(v, getItemData(position));
            }
        });
        holder.updateView(holder, getItemData(position), position);
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

    protected class DefaultViewHolder extends BaseViewHolder {

        public DefaultViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void updateView(BaseViewHolder vh, Object data, int position) {

        }
    }
}
