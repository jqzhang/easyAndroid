package com.brothers.peten.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.brothers.peten.R;
import com.brothers.peten.bussiness.AppApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PermissionUtil {

    public static final int PERMISSION_REQUEST_READ_PHONE_STATE = 0;
    public static final int PERMISSION_REQUEST_CAMERA = 1;
    public static final int PERMISSION_REQUEST_READ_STORAGE = 2;
    public static final int PERMISSION_REQUEST_WRITE_STORAGE = 3;
    public static final int PERMISSION_REQUEST_UPDATE_APK = 4;
    public static final int PERMISSION_REQUEST_GET_ACCOUNTS = 5;

    public static boolean checkAndRequestPermission(final Fragment fragment, String permission, int requestCode) {
        IPermissionAdapter adapter = new FragmentPermissionAdapter(fragment);
        return checkAndRequestPermission(adapter, permission, requestCode);
    }

    public static boolean checkAndRequestPermission(final Activity activity, String permission, int requestCode) {
        IPermissionAdapter adapter = new ActivityPermissionAdapter(activity);
        return checkAndRequestPermission(adapter, permission, requestCode);
    }

    /**
     * @param adapter
     * @param permission
     * @param requestCode
     * @return true if requesting permission, or false if permission allowed/granted
     */
    private static boolean checkAndRequestPermission(PermissionUtil.IPermissionAdapter adapter, String permission, int requestCode) {
        final Activity activity = adapter.getActivity();
        boolean hasPermission = checkPermission(activity, permission);
        if (hasPermission) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= 23) {
            adapter.requestPermissions(new String[]{permission}, requestCode);
            return true;
        } else if (Build.VERSION.SDK_INT >= 19) {
            Permission p = Permission.findByName(permission);
            if (p == null) {
                return false;
            }
            String message = activity.getString(R.string.need_permission_message, activity.getString(p.displayRes));
            Toast.makeText(AppApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * 检查用户是否赋予权限
     *
     * @param activity
     * @param permission
     * @return
     */
    public static boolean checkPermission(final Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Permission p = Permission.findByName(permission);
            if (p == null) {
                return true;
            }
            AppOpsManager appOpsManager = (AppOpsManager) activity.getSystemService(Context.APP_OPS_SERVICE);
            int result = checkOp(appOpsManager, p.opName);
            return result == AppOpsManager.MODE_ALLOWED;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static int checkOp(AppOpsManager manager, String operation) {
        try {
            Class<?> clz = AppOpsManager.class;
            Class[] params = {int.class, int.class, String.class};
            Method method = clz.getMethod("checkOp", params);
            int op = clz.getField(operation).getInt(null);
            Object[] arguments = {op, android.os.Process.myUid(), Device.PACKAGE};
            return (int) method.invoke(manager, arguments);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return AppOpsManager.MODE_IGNORED;
    }

    private enum Permission {
        // Permission for Android 5.0/5.1
        CAMERA(Manifest.permission.CAMERA, "OP_CAMERA", R.string.permission_display_camera);

        public final String name;
        public final String opName;
        public final int displayRes;

        Permission(String name, String opName, int displayRes) {
            this.name = name;
            this.opName = opName;
            this.displayRes = displayRes;
        }

        public static Permission findByName(String permission) {
            for (Permission p : Permission.values()) {
                if (p.name.equals(permission)) {
                    return p;
                }
            }
            return null;
        }
    }

    public interface IPermissionAdapter {
        void requestPermissions(String[] permissions, int requestCode);

        Activity getActivity();
    }

    public static class FragmentPermissionAdapter implements IPermissionAdapter {

        private Fragment mFragment;

        public FragmentPermissionAdapter(Fragment fragment) {
            this.mFragment = fragment;
        }

        @TargetApi(android.os.Build.VERSION_CODES.M)
        @Override
        public void requestPermissions(String[] permissions, int requestCode) {
            mFragment.requestPermissions(permissions, requestCode);
        }

        @Override
        public Activity getActivity() {
            return mFragment.getActivity();
        }
    }

    public static class ActivityPermissionAdapter implements IPermissionAdapter {

        private Activity mActivity;

        public ActivityPermissionAdapter(Activity activity) {
            this.mActivity = activity;
        }

        @TargetApi(android.os.Build.VERSION_CODES.M)
        @Override
        public void requestPermissions(String[] permissions, int requestCode) {
            mActivity.requestPermissions(permissions, requestCode);
        }

        @Override
        public Activity getActivity() {
            return mActivity;
        }
    }
}