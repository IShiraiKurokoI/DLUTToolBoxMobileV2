package com.Shirai_Kuroko.DLUTMobile.Helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;

public class PermissionHelper {
    //    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private static final int MY_REQUEST_CODE = 10000;

    @SuppressLint("InlinedApi")
    public static void GetAllPermission(Activity mContext) {
        String[] permissions = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.RECORD_AUDIO,Manifest.permission.MODIFY_AUDIO_SETTINGS};
        PackageManager packageManager = mContext.getPackageManager();
        PermissionInfo permissionInfo = null;
        for (String permission : permissions) {
            try {
                permissionInfo = packageManager.getPermissionInfo(permission, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            CharSequence permissionName = null;
            if (permissionInfo != null) {
                permissionName = permissionInfo.loadLabel(packageManager);
            }
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                // 未获取权限
                Log.i("权限申请", "您未获得【" + permissionName + "】的权限 ===>");
                LogToFile.i("权限申请", "您未获得【" + permissionName + "】的权限 ===>");
                if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, permission)) {
                    // 这是一个坑，某些手机弹出提示时没有永不询问的复选框，点击拒绝就默认勾上了这个复选框，而某些手机上即使勾选上了永不询问的复选框也不起作用
                    Log.i("权限申请", "您勾选了不再提示【" + permissionName + "】权限的申请");
                    LogToFile.i("权限申请", "您勾选了不再提示【" + permissionName + "】权限的申请");
                } else {
                    ActivityCompat.requestPermissions(mContext, permissions, MY_REQUEST_CODE);
                }
            } else {
                Log.d("权限申请", "您已获得了【" + permissionName + "】的权限");
                LogToFile.d("权限申请", "您已获得了【" + permissionName + "】的权限");
            }
        }
    }
}
