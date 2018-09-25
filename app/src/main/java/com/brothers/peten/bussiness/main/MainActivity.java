package com.brothers.peten.bussiness.main;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.brothers.base.common.BasePresenter;
import com.brothers.base.ui.tablayout.CommonTabLayout;
import com.brothers.base.ui.tablayout.listener.CustomTabEntity;
import com.brothers.base.utils.ActivityStack;
import com.brothers.base.utils.ResourcesUtils;
import com.brothers.peten.R;
import com.brothers.peten.base.AppBaseActivity;
import com.brothers.peten.bussiness.main.cook.CookFragment;
import com.brothers.peten.common.router.Router;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import butterknife.BindView;

@Route(path = Router.PATH.MAIN_ACTIVITY)
public class MainActivity extends AppBaseActivity {

    @BindView(R.id.tabLayout)
    CommonTabLayout tabLayout;
    @BindView(R.id.tabFragment)
    FrameLayout tabFragment;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void init() {
        showTitleBar(false);
        initTab();
        showContentView();
        ActivityStack.closeAllBefore(this);
    }

    @Override
    public void onBackPressed() {
        showDialog(getString(R.string.exit_app_tip));
    }

    @Override
    public void onClickDialogConfirm(int code) {
        super.onClickDialogConfirm(code);
        closeActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        enablePermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appExit();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initTab() {
        String tabs[] = getResources().getStringArray(R.array.TAB_TITLE);
        String tabIconNormalNames[] = getResources().getStringArray(R.array.TAB_ICON_NORMAL);
        String tabIconSelectedNames[] = getResources().getStringArray(R.array.TAB_ICON_SELECTED);

        for (int i = 0; i < tabs.length; i++) {
            mTabEntities.add(new TabEntity(tabs[i],
                    ResourcesUtils.getDrawableId(this, tabIconSelectedNames[i]),
                    ResourcesUtils.getDrawableId(this, tabIconNormalNames[i])));
        }

        mFragments.add(new CookFragment());
        mFragments.add(new CookFragment());
        mFragments.add(new CookFragment());
        mFragments.add(new CookFragment());

        tabLayout.setTabData(mTabEntities, this, R.id.tabFragment, mFragments);
    }

    private void enablePermission() {
        RxPermissions rxPermissions = new RxPermissions(this);

        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                    } else {
                        showToast(R.string.read_write_storage_permission);
                    }
                });
    }

    public interface TAB_POSINTION {
        int DISCOVERY = 0;
        int CLASSROOM = 1;
        int FAVORITES = 2;
        int ME = 3;
    }
}
