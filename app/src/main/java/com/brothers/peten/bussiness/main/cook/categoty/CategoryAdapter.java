package com.brothers.peten.bussiness.main.cook.categoty;

import android.view.View;
import android.view.ViewGroup;

import com.brothers.base.ui.list.BaseAdapter;
import com.brothers.base.ui.list.BaseViewHolder;
import com.brothers.base.ui.widget.AppTextView;
import com.brothers.base.utils.InflaterService;
import com.brothers.peten.R;
import com.brothers.peten.bussiness.main.cook.CookResult;
import com.brothers.peten.common.router.Router;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class CategoryAdapter extends BaseAdapter<CookResult.Child> {

    private CategoryContract.View mView;

    public CategoryAdapter(CategoryContract.View view) {
        mView = view;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(InflaterService.getInstance().inflate(parent.getContext(), R.layout.fragment_category, null));
    }

    public class ContentViewHolder extends BaseViewHolder<CookResult.Child> {

        @BindView(R.id.tvContent)
        AppTextView tvContent;

        public ContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void updateView(BaseViewHolder vh, CookResult.Child data, int position) {
            tvContent.setText(data.categoryInfo.name);
        }

        @Override
        public void onClick(View view, CookResult.Child data) {
            super.onClick(view, data);

            Map<String, String> map = new HashMap<>();
            map.put("cid", data.categoryInfo.ctgId);
            Router.getInstance().start(Router.PATH.TAG_LIST_ACTIVITY, map);
        }

        @Override
        protected boolean enableClickItemAnimal() {
            return true;
        }
    }
}
