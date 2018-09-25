package com.brothers.peten.bussiness.main.cook.detail;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.brothers.base.ui.list.BaseViewHolder;
import com.brothers.base.ui.list.BaseVlayoutAdapter;
import com.brothers.base.ui.widget.AppTextView;
import com.brothers.base.utils.InflaterService;
import com.brothers.peten.R;

import butterknife.BindView;

public class SumaryAdapter extends BaseVlayoutAdapter<DetailResult.Recipe> {

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContentViewHolder(InflaterService.getInstance().inflate(parent.getContext(), R.layout.activity_detail_name, null));
    }

    class ContentViewHolder extends BaseViewHolder<DetailResult.Recipe> {

        @BindView(R.id.tvContent)
        AppTextView tvContent;

        public ContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void updateView(BaseViewHolder vh, DetailResult.Recipe data, int position) {
            tvContent.setText(data.sumary);
        }
    }
}
