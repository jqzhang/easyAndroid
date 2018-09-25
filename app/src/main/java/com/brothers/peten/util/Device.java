package com.brothers.peten.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

public class Device {
    private static final String TAG = "device";
    private static final String KEY_INSTALL_TIME = "installTime";
    private static final long NEW_USER_TIME = 7 * 24 * 60 * 60 * 1000; // 默认安装7天以内为新用户
    /**
     * 设备宽度（像素）
     */
    public static int DISPLAY_WIDTH;
    /**
     * 设备高度（像素）
     */
    public static int DISPLAY_HEIGHT;
    /**
     * 设备的高度*设备的宽度
     */
    public static String DISPLAY_RESOLUTION;
    /**
     * 设备的像素密度dpi
     */
    public static int DISPLAY_DENSITY;
    /**
     * 用户可见的设备名，例如Nexus One
     */
    public static String MODEL;
    /**
     * 内部设备名，例如Nexus One是passion
     */
    public static String DEVICE;
    /**
     * 产品名
     */
    public static String PRODUCT;
    /**
     * 主板名
     */
    public static String BOARD;
    /**
     * 硬件名，从内核命令行获取，例如qcom
     */
    public static String HARDWARE;
    /**
     * 生产商
     */
    public static String MANUFACTURER;
    /**
     * 软件定制品牌
     */
    public static String BRAND;
    /**
     * build类型，例如user/eng
     */
    public static String BUILD_TYPE;
    /**
     * 系统的sdk版本号
     */
    public static int SDK_VERSION;
    /**
     * 系统的版本号（MIUI），例如2.7.13
     */
    public static String SYSTEM_VERSION;
    /**
     * 系统的版本号（Android），例如4.0.4
     */
    public static String RELEASE;
    /**
     * 是否MIUI系统
     */
    public static boolean IS_MIUI;
    /**
     * package name
     **/
    public static String PACKAGE;
    /**
     * version code
     */
    public static int BBS_VERSION;
    /**
     * version name
     */
    public static String BBS_VERSION_STRING;
    /**
     * 设置中的国家
     */
    public static String COUNTRY;
    /**
     * 设置中的语言
     */
    public static String LANGUAGE;
    /**
     * Sim卡的运营商
     */
    public static String CARRIER;
    /**
     * 设备号
     */
    public static String UUID;
    /**
     * IMEI号
     */
    public static String IMEI;
    /**
     * 渠道号
     */
    public static String CHANNEL_ID;
    /**
     * 标题栏高度
     **/
    public static int STATUS_BAR_HEIGHT;

    public static void init(Context context) {
        acquireScreenAttr(context);
        acquireSystemInfo(context);
        acquireBbsInfo(context);
        acquireUserInfo(context);
    }

    public static String getDevice_MODEL() {
        return MODEL;
    }

    public static String getDeviceManufacturer() {
        return MANUFACTURER;
    }

    public static String getClientInfoHash() {
        return Coder.encodeMD5(getFullInfo());
    }

    public static String getFullInfo() {
        return DISPLAY_RESOLUTION + DISPLAY_WIDTH +
                DISPLAY_HEIGHT + DISPLAY_DENSITY + MODEL + DEVICE + PRODUCT +
                BOARD + HARDWARE + MANUFACTURER + BRAND + BUILD_TYPE +
                SDK_VERSION + SYSTEM_VERSION + RELEASE +
                BBS_VERSION + BBS_VERSION_STRING +
                COUNTRY + LANGUAGE + CARRIER +
                UUID + IMEI + CHANNEL_ID;
    }

    private static void acquireScreenAttr(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        DISPLAY_HEIGHT = dm.heightPixels;
        DISPLAY_WIDTH = dm.widthPixels;
        DISPLAY_RESOLUTION = DISPLAY_HEIGHT + "*" + DISPLAY_WIDTH;
        DISPLAY_DENSITY = dm.densityDpi;

        STATUS_BAR_HEIGHT = getStatusBarHeight();
    }

    public static int getStatusBarHeight() {
        int result;
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        } else {
            result = Coder.dip2px(25);
        }
        return result;
    }

    private static void acquireSystemInfo(Context context) {
        MODEL = Build.MODEL;
        DEVICE = Build.DEVICE;
        PRODUCT = Build.PRODUCT;
        BOARD = Build.BOARD;
        HARDWARE = Build.HARDWARE;
        MANUFACTURER = Build.MANUFACTURER;
        BRAND = Build.BRAND;
        BUILD_TYPE = Build.TYPE;

        RELEASE = Build.VERSION.RELEASE;
        SYSTEM_VERSION = Build.VERSION.INCREMENTAL;
        SDK_VERSION = Build.VERSION.SDK_INT;
        IS_MIUI = isMiui(context);
    }

    /**
     * 通过判断CloudService是否存在来判断是否是miui，小米账户如果不存在的话认为不是miui
     */
    public static boolean isMiui(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo pkgInfo = packageManager.getPackageInfo("com.miui.cloudservice", 0);
            return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void acquireBbsInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo pkgInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            PACKAGE = pkgInfo.packageName;
            BBS_VERSION = pkgInfo.versionCode;
            BBS_VERSION_STRING = pkgInfo.versionName;
        } catch (NameNotFoundException e) {
            BBS_VERSION = 0;
            BBS_VERSION_STRING = "";
        }
    }

    private static void acquireUserInfo(Context context) {
        COUNTRY = Locale.getDefault().getCountry();
        LANGUAGE = Locale.getDefault().getLanguage();
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        CARRIER = telManager.getSimOperator();
    }

    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
        }
        return null;
    }

    /**
     * 没有检测到SD卡
     *
     * @return
     */
    public static boolean isSDCardUnavailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED);
    }

    /**
     * @return true 如果SD卡处于不可读写的状态
     */
    public static boolean isSDCardBusy() {
        return !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检查ＳＤ卡是否已满。如果ＳＤ卡的剩余空间小于１００ｋ，则认为ＳＤ卡已满。
     *
     * @return
     */
    public static boolean isSDCardFull() {
        return getSDCardAvailableBytes() <= (100 * 1024);
    }

    public static boolean isSDCardUseful() {
        return (!isSDCardBusy()) && (!isSDCardFull())
                && (!isSDCardUnavailable());
    }

    /**
     * 获取ＳＤ卡的剩余字节数。
     *
     * @return
     */
    public static long getSDCardAvailableBytes() {
        if (isSDCardBusy()) {
            return 0;
        }

        final File path = Environment.getExternalStorageDirectory();
        final StatFs stat = new StatFs(path.getPath());
        final long blockSize = stat.getBlockSize();
        final long availableBlocks = stat.getAvailableBlocks();
        return blockSize * (availableBlocks - 4);
    }
}
