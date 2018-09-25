package com.brothers.peten.bussiness.main.cook.detail;

import com.brothers.peten.base.AppData;
import com.brothers.peten.bussiness.main.cook.CookBaseResult;

import java.util.List;

public class DetailResult extends CookBaseResult {

    public Result result;

    public class Result extends AppData {
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

    public class Method extends AppData {
        public String img;
        public String step;
    }
}
