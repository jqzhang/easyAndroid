package com.brothers.base.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 对LayoutInflater加载XML布局资源做保护
 */
public class InflaterService {

    private static int RESOURCE_LOAD_MAX_TRY_COUNT = 3;

    private static InflaterService instance = new InflaterService();

    private InflaterService() {

    }

    public static InflaterService getInstance() {
        return instance;
    }

    /**
     * 获取LayoutInflater实例
     *
     * @param context
     * @return LayoutInflater
     */
    private LayoutInflater getLayoutInflater(Context context) {
        if (null == context) {
            return null;
        }
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 从特定的xml布局资源文件加载视图
     *
     * @param context
     * @param resId   ID for an XML layout resource to load
     * @param root    Optional view to be the parent of the generated hierarchy
     * @return view The root View of the inflated hierarchy. If root was
     * supplied, this is the root View; otherwise it is the root of the
     * inflated XML file
     */
    public View inflate(Context context, int resId, ViewGroup root) {

        if (null == context) {
            return null;
        }
        View view = null;
        for (int i = 0; i < RESOURCE_LOAD_MAX_TRY_COUNT; i++) {
            try {
                LayoutInflater layoutInflater = getLayoutInflater(context);
                if (null != layoutInflater) {
                    view = getLayoutInflater(context).inflate(resId, root);
                }
                break;
            } catch (RuntimeException t) {
                // 如果inflate失败，重新尝试一次
                if (i == RESOURCE_LOAD_MAX_TRY_COUNT - 1) {
                    throw t;
                }

            } catch (OutOfMemoryError t) {
                if (i == RESOURCE_LOAD_MAX_TRY_COUNT - 1) {
                    throw t;
                }
            }
        }
        return view;
    }

    /**
     * 从特定的xml布局资源文件加载视图
     *
     * @param context
     * @param resId        ID for an XML layout resource to load
     * @param root         Optional view to be the parent of the generated hierarchy
     * @param attachToRoot Whether the inflated hierarchy should be attached to the root
     *                     parameter? If false, root is only used to create the correct
     *                     subclass of LayoutParams for the root view in the XML
     * @return view The root View of the inflated hierarchy. If root was
     * supplied and attachToRoot is true, this is root; otherwise it is
     * the root of the inflated XML file
     */
    public View inflate(Context context, int resId, ViewGroup root, boolean attachToRoot) {
        if (null == context) {
            return null;
        }
        View view = null;
        for (int i = 0; i < RESOURCE_LOAD_MAX_TRY_COUNT; i++) {
            try {
                view = getLayoutInflater(context).inflate(resId, root, attachToRoot);
                break;
            } catch (RuntimeException t) {
                // 如果inflate失败，重新尝试一次
                if (i == RESOURCE_LOAD_MAX_TRY_COUNT - 1) {
                    throw t;
                }
//                AppBaseApplication.getInst().onAppMemoryLow();
            } catch (OutOfMemoryError t) {
                if (i == RESOURCE_LOAD_MAX_TRY_COUNT - 1) {
                    throw t;
                }
//                AppBaseApplication.getInst().onAppMemoryLow();
            }
        }
        return view;
    }
}
