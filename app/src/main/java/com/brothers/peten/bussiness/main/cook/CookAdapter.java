package com.brothers.peten.bussiness.main.cook;

import android.view.View;
import android.view.ViewGroup;

import com.brothers.base.ui.list.BaseAdapter;
import com.brothers.base.ui.list.BaseViewHolder;
import com.brothers.base.ui.widget.AppTextView;
import com.brothers.base.utils.GsonUtils;
import com.brothers.base.utils.InflaterService;
import com.brothers.peten.R;
import com.brothers.peten.common.router.Router;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class CookAdapter extends BaseAdapter<CookResult.Childs> {

    private CookContract.View mView;

    public CookAdapter(CookContract.View view) {
        mView = view;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(InflaterService.getInstance().inflate(parent.getContext(), R.layout.fragment_cook, null));
    }

    public class ContentViewHolder extends BaseViewHolder<CookResult.Childs> {

        @BindView(R.id.tvContent)
        AppTextView tvContent;

        public ContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void updateView(BaseViewHolder vh, CookResult.Childs data, int position) {
            tvContent.setText(data.categoryInfo.name);
        }

        @Override
        public void onClick(View view, CookResult.Childs data) {
            super.onClick(view, data);

            Map<String, String> map = new HashMap<>();
            map.put("childs", GsonUtils.toJson(data));
            Router.getInstance().start(Router.PATH.CATEGORY_LIST_ACTIVITY, map);
        }

        @Override
        protected boolean enableClickItemAnimal() {
            return true;
        }
    }
}
