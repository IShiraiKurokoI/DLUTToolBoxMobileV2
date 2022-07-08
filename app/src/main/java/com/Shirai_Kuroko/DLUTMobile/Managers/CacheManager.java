package com.Shirai_Kuroko.DLUTMobile.Managers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.Shirai_Kuroko.DLUTMobile.Helpers.ContextHelper;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

//缓存工具类
public class CacheManager {
    //获取缓存大小
    public static String getTotalCacheSize(Context context) {

        long cacheSize = getFolderSize(context.getCacheDir());

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        cacheSize+=getFolderSize(context.getExternalFilesDir("Share"));
        cacheSize+=getFolderSize(context.getExternalFilesDir("Crop"));
        Log.i("分享图片缓存目录：", context.getExternalFilesDir("Share").toString());
        Log.i("头像上传缓存目录：", context.getExternalFilesDir("Crop").toString());
        return getFormatSize(cacheSize);
    }

    //清除缓存
    public static void clearAllCache(Context context) {

        deleteDir(context.getCacheDir());
        deleteDir(context.getExternalFilesDir("Share"));
        deleteDir(context.getExternalFilesDir("Crop"));
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {

            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {

                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {

                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if (fileList != null) {
                for (File value : fileList) {

                    // 如果下面还有文件
                    if (value.isDirectory()) {

                        size = size + getFolderSize(value);
                    } else {

                        size = size + value.length();
                    }
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return size;
    }


    //格式化单位
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {

            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {

            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, RoundingMode.HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {

            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, RoundingMode.HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {

            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, RoundingMode.HALF_UP).toPlainString() + "GB";
        }

        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, RoundingMode.HALF_UP).toPlainString() + "TB";
    }
    /**
     * 创建内部类，清除缓存
     */
    public static class ClearCache implements Runnable {
        @Override
        public void run() {
            try {
                CacheManager.clearAllCache(ContextHelper.getContext());
                if (CacheManager.getTotalCacheSize(ContextHelper.getContext()).startsWith("0")) {
                    Toast.makeText(ContextHelper.getContext(), "清理完成", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

