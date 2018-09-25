package com.brothers.peten.bussiness.main.cook.tag;

import com.brothers.peten.base.AppData;
import com.brothers.peten.bussiness.main.cook.CookBaseResult;

import java.util.List;

public class TagListResult extends CookBaseResult {

    public Result result;

    @Override
    public boolean hasMore() {
        return (result.list != null) && (false == result.list.isEmpty());
    }

    public class Result extends AppData {
        public int curPage;
        public List<Data> list;
    }

    public class Data extends AppData {
        public List<String> ctgIds;
        public String ctgTitles;
        public String menuId;
        public String name;
        public Recipe recipe;
        public String thumbnail;
    }

    public class Recipe extends AppData {
        public String img;
        public String ingredients;
        public String method;
        public String sumary;
        public String title;
    }
}
