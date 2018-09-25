package com.brothers.peten.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class AttachmentUtil {

    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory() + "/shudong/images/";
    private static final String FILENAME_FORMAT = "%s_%d.%s";

    private static final String TAG = "AttachmentUtil";

    public static File makeImageDirsIfNeeded() {
        if (isSDCardBusy())
            return null;

        File bbsRoot = new File(IMAGE_PATH);
        if (bbsRoot.isDirectory() || bbsRoot.mkdirs()) {
            hideFromMediaScanner(bbsRoot);
        }

        File imageRoot = new File(bbsRoot, "images");
        if (imageRoot.isDirectory() || imageRoot.mkdirs()) {
            hideFromMediaScanner(imageRoot);
        }
        return imageRoot;
    }

    /**
     * @return true 如果SD卡处于不可读写的状态
     */
    public static boolean isSDCardBusy() {
        return !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void hideFromMediaScanner(final File root) {
        final File file = new File(root, ".nomedia");
        if (!file.exists() || !file.isFile()) {
            try {
                file.createNewFile();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getUniqueFileName(final File root, final String filename) {
        final File file = new File(root, filename);
        if (!file.exists()) {
            return file.getAbsolutePath();
        }
        final int dotPos = filename.lastIndexOf('.');
        String part1 = filename;
        String part2 = "";
        if (dotPos > 0) {
            part1 = filename.substring(0, dotPos);
            part2 = filename.substring(dotPos + 1);
        }
        for (int i = 1; true; i++) {
            final String res = String.format(FILENAME_FORMAT, part1, i, part2);
            final File t = new File(root, res);
            if (!t.exists()) {
                return t.getAbsolutePath();
            }
        }
    }
}

