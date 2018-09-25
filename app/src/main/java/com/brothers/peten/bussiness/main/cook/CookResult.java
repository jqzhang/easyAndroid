package com.brothers.peten.bussiness.main.cook;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.brothers.peten.base.AppData;
import com.brothers.peten.common.router.Router;

import java.util.List;

public class CookResult extends CookBaseResult {

    public Result result;

    public class Result extends AppData {
        public CategoryInfo categoryInfo;
        public List<Childs> childs;
    }

    public class CategoryInfo extends AppData {
        public String ctgId;
        public String parentId;
        public String name;
    }

    @Route(path = Router.OBJ.COOK_CHILDS)
    public class Childs extends AppData {
        public CategoryInfo categoryInfo;
        public List<Child> childs;
    }

    public class Child extends AppData {
        public CategoryInfo categoryInfo;
    }
}
