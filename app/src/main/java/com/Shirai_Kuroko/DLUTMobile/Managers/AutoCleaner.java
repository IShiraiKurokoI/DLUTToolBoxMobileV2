package com.Shirai_Kuroko.DLUTMobile.Managers;

import android.content.Context;

import java.io.File;

public class AutoCleaner {
    public static void Clean(Context context)
    {
        deleteDir(context.getExternalFilesDir("Share"));
        deleteDir(context.getExternalFilesDir("Crop"));
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
}
