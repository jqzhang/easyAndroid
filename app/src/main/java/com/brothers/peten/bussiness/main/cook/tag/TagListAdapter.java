package com.brothers.peten.bussiness.main.cook.tag;

import android.view.View;
import android.view.ViewGroup;

import com.brothers.base.ui.list.BaseAdapter;
import com.brothers.base.ui.list.BaseViewHolder;
import com.brothers.base.ui.widget.AppTextView;
import com.brothers.base.utils.InflaterService;
import com.brothers.base.utils.UriUtils;
import com.brothers.peten.R;
import com.brothers.peten.common.router.Router;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class TagListAdapter extends BaseAdapter<TagListResult.Data> {

    private TagListContract.View mView;

    public TagListAdapter(TagListContract.View view) {
        mView = view;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(InflaterService.getInstance().inflate(parent.getContext(), R.layout.fragment_cook_tag, null));
    }

    public class ContentViewHolder extends BaseViewHolder<TagListResult.Data> {

        @BindView(R.id.sdvImg)
        SimpleDraweeView sdvImg;
        @BindView(R.id.tvName)
        AppTextView tvName;
        @BindView(R.id.tvSumary)
        AppTextView tvSumary;

        public ContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void updateView(BaseViewHolder vh, TagListResult.Data data, int position) {
            sdvImg.setImageURI(UriUtils.parse(data.thumbnail));
            tvName.setText(data.name);
            tvSumary.setText(data.recipe.sumary);
        }

        @Override
        public void onClick(View view, TagListResult.Data data) {
            super.onClick(view, data);
            Map<String, String> map = new HashMap<>();
            map.put("id", data.menuId);
            Router.getInstance().start(Router.PATH.DETAIL_ACTIVITY, map);
        }

        @Override
        protected boolean enableClickItemAnimal() {
            return true;
        }
    }
}
