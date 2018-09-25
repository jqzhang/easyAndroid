package com.brothers.peten.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;

import com.brothers.peten.bussiness.AppApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Coder {

    public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String AES_PHP = "AES/ECB/PKCS5Padding";

    private final static String[] hexDigits = {
            "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
    };

    public static String getStringMD5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
            BigInteger bigInt = new BigInteger(1, digest.digest());
            String output = bigInt.toString(16);
            // Fill to 32 chars
            output = String.format("%32s", output).replace(' ', '0');
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getFileMD5(String path) {
        if (TextUtils.isEmpty(path)) return null;
        try {
            File file = new File(path);
            return encodeMD5(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String encodeMD5(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        MessageDigest digester = null;
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        digester.update(string.getBytes());
        byte[] digest = digester.digest();
        return byteArrayToString(digest);
    }

    public static final String encodeMD5(File file) {
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        try {
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return byteArrayToString(md5.digest());
    }

    private static String byteArrayToString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static final String encodeSHA(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        MessageDigest digester = null;
        try {
            digester = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        digester.update(string.getBytes());
        byte[] digest = digester.digest();
        return byteArrayToString(digest);
    }

    public static final byte[] encodeSHABytes(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        MessageDigest digester = null;
        try {
            digester = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        digester.update(string.getBytes());
        return digester.digest();
    }

    public static final String encodeBase64(String string) {
        return Base64.encodeToString(string.getBytes(), Base64.DEFAULT
                | Base64.NO_WRAP);
    }

    public static final String encodeBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT | Base64.NO_WRAP);
    }

    public static final byte[] encodeBase64Bytes(String string) {
        return Base64
                .encode(string.getBytes(), Base64.DEFAULT | Base64.NO_WRAP);
    }

    public static final String decodeBase64(String string) {
        return new String(Base64.decode(string, Base64.DEFAULT));
    }

    public static final byte[] decodeBase64Bytes(String string) {
        return Base64.decode(string, Base64.DEFAULT);
    }

    /**
     * 返回经过AES加密和base64编码后的数据
     */
    public static final String encodeAES(String data, String key) {
        if (TextUtils.isEmpty(data) || TextUtils.isEmpty(key)) {
            return null;
        }
        byte[] raw = decodeBase64Bytes(key);
        if (raw == null || raw.length != 16) {
            return null;
        }
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");

        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(
                    "0102030405060708".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            return encodeBase64(cipher.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (NoSuchPaddingException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            return null;
        } catch (IllegalBlockSizeException e) {
            return null;
        } catch (BadPaddingException e) {
            return null;
        }
    }

    /**
     * 返回经过base64解码和AES解密后的数据
     */
    public static final String decodeAES(String data, String key) {
        if (TextUtils.isEmpty(data) || TextUtils.isEmpty(key)) {
            return null;
        }
        byte[] raw = decodeBase64Bytes(key);
        if (raw == null || raw.length != 16) {
            return null;
        }
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");

        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(
                    "0102030405060708".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] encryptedByte = decodeBase64Bytes(data);
            if (null == encryptedByte) {
                return null;
            }
            byte[] decryptedByte = cipher.doFinal(encryptedByte);
            return new String(decryptedByte);
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (NoSuchPaddingException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            return null;
        } catch (IllegalBlockSizeException e) {
            return null;
        } catch (BadPaddingException e) {
            return null;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(final Activity activity, final float dpValue) {
        final DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final float scale = metrics.density;
        return (int) ((dpValue * scale) + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(final Context context, final float dpValue) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final float scale = metrics.density;
        return (int) ((dpValue * scale) + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(final float dpValue) {
        return dip2px(AppApplication.getInstance(), dpValue);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(final Activity activity, final float pxValue) {
        final DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final float scale = metrics.density;
        return (int) ((pxValue / scale) + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(final Context context, final float pxValue) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final float scale = metrics.density;
        return (int) ((pxValue / scale) + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(final float pxValue) {
        return px2dip(AppApplication.getInstance(), pxValue);
    }
}
