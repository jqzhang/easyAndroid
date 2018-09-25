package com.brothers.peten.bussiness.main.cook.detail;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.brothers.base.ui.list.BaseViewHolder;
import com.brothers.base.ui.list.BaseVlayoutAdapter;
import com.brothers.base.ui.widget.AppTextView;
import com.brothers.base.utils.InflaterService;
import com.brothers.base.utils.UriUtils;
import com.brothers.peten.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

public class MethodAdapter extends BaseVlayoutAdapter<DetailResult.Method> {

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContentViewHolder(InflaterService.getInstance().inflate(parent.getContext(), R.layout.activity_detail_method, parent, false));
    }

    class ContentViewHolder extends BaseViewHolder<DetailResult.Method> {

        @BindView(R.id.sdvImg)
        SimpleDraweeView sdvImg;
        @BindView(R.id.tvContent)
        AppTextView tvContent;

        public ContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void updateView(BaseViewHolder vh, DetailResult.Method data, int position) {
            sdvImg.setImageURI(UriUtils.parse(data.img));
            tvContent.setText(data.step);
        }
    }
}
